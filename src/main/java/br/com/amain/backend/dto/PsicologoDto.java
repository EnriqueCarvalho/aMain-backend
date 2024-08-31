package br.com.amain.backend.dto;

import br.com.amain.backend.model.AreaAtuacao;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PsicologoDto {
    private Integer CRP;
    private Float valorConsulta;
    private String biografia;
    
    @ManyToOne
    private UsuarioDto usuario;

    @ManyToOne
    private AreaAtuacao areaAtuacao;
}
