package br.com.amain.backend.security;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.avmb.diploma_digital.exception.DadosInvalidosException;
import com.avmb.diploma_digital.service.UsuarioService;


/**
 * <p>A custom Authentication provider example. To create custom AuthenticationProvider, we need to implement the
 * AuthenticationProvider provide the implementation for the authenticate and support method.</p>
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @jakarta.annotation.Resource
    UserDetailsService userDetailsService;

    @Autowired
    UsuarioService usuarioService;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * <p> The authenticate method to authenticate the request. We will get the username from the Authentication object and will
     * use the custom @userDetailsService service to load the given user.</p>
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {        
        final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        final String password = (authentication.getCredentials() == null) ? "NONE_PROVIDED" : authentication.getCredentials().toString();

      

        if (username.equals("NONE_PROVIDED") || password.equals("NONE_PROVIDED")) {
            throw new BadCredentialsException("Nenhum login ou senha foi informado.");
        } 
        UserDetails user = null;    
        LOGGER.log(Level.INFO, "Autenticando usuário: " + username.trim());
        
        for ( GrantedAuthority auth : authentication.getAuthorities()) {
            LOGGER.log(Level.INFO, "auth -> "+auth.getAuthority());
            boolean userIsAdmin = auth.getAuthority().equals("ADMIN");
            user = userIsAdmin ? this.usuarioService.loginViaSie(username,password) : this.usuarioService.loginViaSieSemCrypto(username,password);         
        break;
        }  

        return createSuccessfulAuthentication(authentication, user);
    }

    private Authentication createSuccessfulAuthentication(final Authentication authentication, final UserDetails user) {
        if(user == null){
            throw new DadosInvalidosException("User is null ");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername().trim(), authentication.getCredentials(), user.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}