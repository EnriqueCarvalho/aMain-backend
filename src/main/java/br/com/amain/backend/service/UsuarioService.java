package br.com.amain.backend.service;


import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.amain.backend.exception.AcessoInvalidoException;
import br.com.amain.backend.exception.ObjectNotFoundException;
import br.com.amain.backend.model.Usuario;
import br.com.amain.backend.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public UserDetails login(String email, String password){
        return null;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Usuario usuario =  this.usuarioRepository.findByEmail(email).orElseThrow(
        () -> new ObjectNotFoundException("Usuário não encontrado")
      );
      return User.withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities("a")
                .build();     
    }


    public UserDetails validarUsuario(String email, String password, Collection<? extends GrantedAuthority> authorities){  
        LOGGER.log(Level.INFO, "Validando usuario: {0}", email);     
        Usuario usuario = usuarioRepository.findByEmailAndPassword(email, password).orElseThrow(
            () -> new AcessoInvalidoException("Email e/ou senha incorretos")
        );
         
  
        return User.withUsername(usuario.getEmail())
            .password("{noop}"+usuario.getPassword())
            .authorities(authorities)
            .build();   
    }

    public void cadastrarUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }
}
