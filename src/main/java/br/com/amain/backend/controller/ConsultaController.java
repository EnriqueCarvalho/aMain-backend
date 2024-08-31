package br.com.amain.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amain.backend.dto.ConsultaDto;
import br.com.amain.backend.security.JwtTokenUtil;
import br.com.amain.backend.service.ConsultaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@SecurityRequirement(name = "bearer-key")
@Tag(name = "Consulta", description = "Consultas")
@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ConsultaService consultaService;

    @PostMapping("/nova")
    private void criarConsulta(HttpServletRequest request, ConsultaDto consultaDto){
        Long idUsuario = jwtTokenUtil.getIdIdUsuarioFromRequest(request);
        
    }
    
}
