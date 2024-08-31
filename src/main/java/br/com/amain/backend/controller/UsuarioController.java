package br.com.amain.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amain.backend.dto.LoginDto;
import br.com.amain.backend.dto.PsicologoDto;
import br.com.amain.backend.model.AuthTokenResponse;
import br.com.amain.backend.model.Usuario;
import br.com.amain.backend.service.AuthService;
import br.com.amain.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Usuário", description = "Autenticação de usuários")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    @Autowired 
    private UsuarioService usuarioService;
    @Autowired
    private AuthService authService;

    
    @PostMapping("/cadastro/psicologo")
    public void cadastrarUsuario(@RequestBody PsicologoDto psicologoDto){                
        usuarioService.cadastrarUsuarioPsicologo(psicologoDto);      
    }


       
    @PostMapping("/login")
    public AuthTokenResponse login(@RequestBody LoginDto loginDto ){    
        return authService.gerarToken(loginDto);
    }
}
