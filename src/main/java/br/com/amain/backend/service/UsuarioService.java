package br.com.amain.backend.service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.amain.backend.model.Usuario;
import br.com.amain.backend.repository.UsuarioRepository;

public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UserDetails login(String email, String password){
        
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Usuario usuario =  this.usuarioRepository.findByEmail(email).orElseThrow(
        () -> new ObjectNotFoundException("Usuário não encontrado")
      );
      return User.withUsername(usuario.getLogin().trim())
                .password("{noop}"+usuario.getSenha())
                .authorities(usuario.getSituacaoUsuario())
                .build();     
    }
}
