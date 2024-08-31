package br.com.amain.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Usuario{
    
    @Id
    private Long idUsuario;
    private String nome;
    private String email;
    private String password;
    private String tipo;
    private LocalDateTime dtNascimento;  

}
