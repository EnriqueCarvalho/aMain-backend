package br.com.amain.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amain.backend.model.Usuario;
import br.com.amain.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Usuário", description = "Autenticação de usuários")
@RestController
@RequestMapping("/v1/api/usuario")
public class UsuarioController {
    
    @Autowired 
    private UsuarioService usuarioService;

    
    @PostMapping
    public void cadastrarUsuario(@RequestBody Usuario usuario){
        usuarioService.cadastrarUsuario(usuario);
    }
}
