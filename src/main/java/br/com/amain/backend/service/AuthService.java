package br.com.amain.backend.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import br.com.amain.backend.dto.LoginDto;
import br.com.amain.backend.exception.DadosInvalidosException;
import br.com.amain.backend.model.AuthTokenResponse;
import br.com.amain.backend.model.Paciente;
import br.com.amain.backend.model.Psicologo;
import br.com.amain.backend.model.Usuario;
import br.com.amain.backend.security.JwtTokenUtil;



@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PsicologoService psicologoService;
    @Autowired
    private PacienteService pacienteService;

    public AuthTokenResponse gerarToken(LoginDto loginDto){
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        Set<GrantedAuthority> roles = new HashSet<>(); 
        Usuario usuario = usuarioService.findByEmail(email);

        Psicologo psicologo = psicologoService.getPsicologoByIdUsuario(usuario.getIdUsuario());     
        Paciente paciente = pacienteService.findByIdUsuario(usuario.getIdUsuario());

        if(psicologo != null && paciente != null){
            throw  new DadosInvalidosException("Paciente e Psicologos inconsistentes");
        }

        roles.add(new SimpleGrantedAuthority("A"));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        Authentication authentication  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password, grantedAuthorities));
        

        String token = jwtTokenUtil.generateToken(authentication, usuario.getIdUsuario());
        Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);
        return new AuthTokenResponse(token, expirationDate.getTime(), psicologo, paciente);        
    } 


    


}
