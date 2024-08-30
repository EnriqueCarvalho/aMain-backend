package br.com.amain.backend.security;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.amain.backend.model.ColorsEnum;
import br.com.amain.backend.model.ObjectHolder;
import br.com.amain.backend.model.Usuario;
import br.com.amain.backend.service.UsuarioService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsuarioService usuarioService;


    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    //Método principal para o filtro
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {   
            String token = getJWTFromRequest(request);
            if (hasUrlPermida(request)) {
                if (token != null && jwtTokenUtil.validateToken(token)) {
                    validarToken(token, request);
                } else {
                    logRequestSemToken(request);
                }
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (ExpiredJwtException e) {
            LOGGER.warning("Token expirado...");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }
    

    //método criado para somente entrar no filtro se for uma dessas urls, caso contrário, retornará um erro instantâneamente pro usuário
    private boolean hasUrlPermida(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("/sac/") ||
                uri.contains("swagger") ||
                uri.contains("api-docs") ||
                uri.contains("asten-api.html") ||
                uri.contains("v3");
    }
    
    private void validarToken(String token, HttpServletRequest request) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        String siglaCliente = jwtTokenUtil.getClaimStringFromToken(token, "siglaCliente");

        String userInfoLog =  ColorsEnum.BLUE.cor + siglaCliente + " (" +username+")"; 
        String urlInfoLog = ColorsEnum.WHITE.cor + "--> url:  " + request.getRequestURL();
        LOGGER.log(Level.INFO, "{0}{1}", new Object[]{userInfoLog, urlInfoLog});

        UserDetails userDetails = usuarioService.loadUserByUsername(username);            
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        setObjectHolder(token, request);
    }

    
    private void logRequestSemToken(HttpServletRequest request) {
        LOGGER.log(Level.INFO, "doFilterInternal, Ip Nginx: {0}, Url: {1}", new Object[]{request.getHeader("X-Forwarded-For"), request.getRequestURL()});
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    //Adiciona alguns dados na thread do usuário para que as classes de auditoria possam utilizar
    private void setObjectHolder(String token, HttpServletRequest request){
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        String ipRequest = xForwardedForHeader != null ? xForwardedForHeader : request.getRemoteAddr();        
        
      
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(jwtTokenUtil.getIdPessoaCliFromToken(token));
        ObjectHolder.setCurrentIp(ipRequest);
        ObjectHolder.setCurrentUser(usuario);
    }
}
