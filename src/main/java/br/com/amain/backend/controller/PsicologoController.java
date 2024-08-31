package br.com.amain.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amain.backend.model.Psicologo;
import br.com.amain.backend.service.PsicologoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Psicólogo", description = "Psicólogos")
@RestController
@RequestMapping("api/psicologo")
@SecurityRequirement(name = "bearer-key")
public class PsicologoController {
    @Autowired
    private PsicologoService psicologoService;

    
    @GetMapping
    public List<Psicologo> findAll() {
        return psicologoService.findAll();
    }
}
