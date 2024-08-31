package br.com.amain.backend.model;


import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Embedded;
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
public class Consultas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsulta;
    private Date data;
    private Time hora;
    private String anotacoesPsicologo;

    @ManyToOne
    private Psicologo psicologo;

    @ManyToOne
    private Paciente paciente;

    private Date dtCancelamento;
    private Time hrCancelamento;
    private String motivoCancelamento;
    private String tipo; //"P-presencial" "M- meet"
    @Embedded
    private Endereco endereco;
    private String linkMeet;

}
