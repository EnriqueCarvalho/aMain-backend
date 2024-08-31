package br.com.amain.backend.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
    private String nome;
    private String email;
    private String password;
    private String tipo;
    private Date dtNascimento;  
}
