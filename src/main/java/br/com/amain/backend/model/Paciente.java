package br.com.amain.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;    

    @ManyToOne
    private PublicoAlvo publicoAlvo;
    private String observacoes;
    private Boolean reposavel;
    @ManyToOne
    private Usuario usuario;
}
