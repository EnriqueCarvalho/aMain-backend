package br.com.amain.backend.dto;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDto {
    private Long idPaciente;    
    @ManyToOne
    private Long idPublicoAlvo;
    private String observacoes;
    private Boolean reposavel;
    @ManyToOne
    private UsuarioDto usuario;
}
