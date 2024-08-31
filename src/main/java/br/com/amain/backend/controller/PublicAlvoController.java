package br.com.amain.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Público", description = "Público Alvo")
@RestController
@RequestMapping("/api/publicoAlvo")
public class PublicAlvoController {

    // @Autowired
    // private
    
    // @GetMapping
    // public List<PublicoAlvo> findAll() {
    //     return areaAtuacaoService.getAreas();
    // }
}
