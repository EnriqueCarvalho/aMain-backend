package br.com.amain.backend.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaDto {
    private Date data;
    private Time hora;
    private Long idPsicologo;
    private String tipo;
}
