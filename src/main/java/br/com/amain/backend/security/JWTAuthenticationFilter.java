package br.com.amain.backend.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


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
    }
    

    //método criado para somente entrar no filtro se for uma dessas urls, caso contrário, retornará um erro instantâneamente pro usuário
    private boolean hasUrlPermida(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("/sie/") ||
                uri.contains("swagger") ||
                uri.contains("api-docs") ||
                uri.contains("asten-api.html") ||
                uri.contains("v3");
    }
    
    private void validarToken(String token, HttpServletRequest request) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        LOGGER.log(Level.INFO, "--> Acessando url:  " + request.getRequestURL() + ", Usuário: " + username);
        UserDetails userDetails = usuarioService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    
    private void logRequestSemToken(HttpServletRequest request) {
        LOGGER.log(Level.INFO, "doFilterInternal" + 
            ", IP : " + request.getRemoteAddr() +
            ", PORT: " + request.getRemotePort() + 
            ", Host: " + request.getRemoteHost() + 
            ", Ip Nginx: " + request.getHeader("X-Forwarded-For") + 
            ", Url: " + request.getRequestURL());
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
