package br.com.amain.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Consulta", description = "Consultas")
@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {
    
}
