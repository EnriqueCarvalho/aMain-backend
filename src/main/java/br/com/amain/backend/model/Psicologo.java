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
public class Psicologo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPsicologo;
    private Integer CRP;
    private Float valorConsulta;
    private String biografia;
    
    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private AreaAtuacao areaAtuacao;
}
