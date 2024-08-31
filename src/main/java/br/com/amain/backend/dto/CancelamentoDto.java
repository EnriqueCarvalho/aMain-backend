package br.com.amain.backend.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelamentoDto {
    private Long idConsulta;
    private Date dtCancelamento;
    private Time hrCancelamento;
    private String motivoCancelamento;
}
