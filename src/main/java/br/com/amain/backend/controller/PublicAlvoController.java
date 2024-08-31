package br.com.amain.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amain.backend.model.PublicoAlvo;
import br.com.amain.backend.service.PublicoAlvoService;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Público", description = "Público Alvo")
@RestController
@RequestMapping("/api/publicoAlvo")
public class PublicAlvoController {

    @Autowired
    private PublicoAlvoService publicoAlvoService;
    
    @GetMapping
    public List<PublicoAlvo> findAll() {
        return publicoAlvoService.findAll();
    }
}
