package br.com.amain.backend.service;


import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.amain.backend.dto.PsicologoDto;
import br.com.amain.backend.exception.AcessoInvalidoException;
import br.com.amain.backend.exception.ObjectNotFoundException;
import br.com.amain.backend.model.Psicologo;
import br.com.amain.backend.model.Usuario;
import br.com.amain.backend.repository.PsicologoRepository;
import br.com.amain.backend.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PsicologoRepository psicologoRepository; 

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

    public void cadastrarUsuarioPsicologo(PsicologoDto psicologoDto){  
        try { 
        Usuario usuario = new Usuario();
        usuario.setEmail(psicologoDto.getUsuario().getEmail());
        usuario.setPassword(psicologoDto.getUsuario().getPassword());
        usuario.setNome(psicologoDto.getUsuario().getNome());
        usuario.setDtNascimento(psicologoDto.getUsuario().getDtNascimento());
        usuarioRepository.save(usuario);

        Psicologo psicologo = new Psicologo();
        psicologo.setBiografia(psicologoDto.getBiografia());
        psicologo.setCRP(psicologoDto.getCRP());
        psicologo.setUsuario(usuario);
        psicologoRepository.save(psicologo);
    } catch (Exception e) {
        e.printStackTrace();
        // TODO: handle exception
    }
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findByEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
            () -> new ObjectNotFoundException("Nenhum usuário encontrado!")
        );
    }
}
