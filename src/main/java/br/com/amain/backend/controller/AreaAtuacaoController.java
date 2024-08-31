package br.com.amain.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amain.backend.model.AreaAtuacao;
import br.com.amain.backend.service.AreaAtuacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Area", description = "Área de atuação")
@RestController
@RequestMapping("/api/area")
public class AreaAtuacaoController {
    
    @Autowired
    private AreaAtuacaoService areaAtuacaoService;

    @GetMapping
    public List<AreaAtuacao> findAll() {
        return areaAtuacaoService.getAreas();
    }
    
}
