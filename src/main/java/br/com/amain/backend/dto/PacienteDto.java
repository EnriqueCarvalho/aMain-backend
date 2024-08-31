package br.com.amain.backend.dto;

import br.com.amain.backend.model.PublicoAlvo;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDto {
    private Long idPaciente;    
    @ManyToOne
    private PublicoAlvo publicoAlvo;
    private String observacoes;
    private Boolean reposavel;
    @ManyToOne
    private UsuarioDto usuario;
}
