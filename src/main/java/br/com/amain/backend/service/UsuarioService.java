package br.com.amain.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.amain.backend.exception.ObjectNotFoundException;
import br.com.amain.backend.model.Usuario;
import br.com.amain.backend.repository.UsuarioRepository;

public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

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
}
