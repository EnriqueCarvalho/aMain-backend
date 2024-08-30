package br.com.amain.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import br.com.amain.backend.service.UsuarioService;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    UserDetailsService userDetailsService;
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {              
        final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        final String password = (authentication.getCredentials() == null) ? "NONE_PROVIDED" : authentication.getCredentials().toString();      

        if (username.equals("NONE_PROVIDED") || password.equals("NONE_PROVIDED")) {
            throw new BadCredentialsException("Nenhum login ou senha foi informado.");
        }   
            
        UserDetails user = usuarioService.validarUsuario(username,password, authentication.getAuthorities());    
        return createSuccessfulAuthentication(authentication, user);
    }

    private Authentication createSuccessfulAuthentication(final Authentication authentication, final UserDetails user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername().trim(), authentication.getCredentials(), user.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}