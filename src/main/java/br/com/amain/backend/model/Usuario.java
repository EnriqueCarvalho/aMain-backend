package br.com.amain.backend.model;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Usuario implements UserDetails{
    
    @Id
    private Long idUsuario;
    private String nome;
    private String email;
    private String password;
}
