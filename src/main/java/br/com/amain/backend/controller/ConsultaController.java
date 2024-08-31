package br.com.amain.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amain.backend.dto.CancelamentoDto;
import br.com.amain.backend.dto.ConsultaDto;
import br.com.amain.backend.model.Consultas;
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
    public Consultas criarConsulta(HttpServletRequest request, @RequestBody ConsultaDto consultaDto){
        Long idUsuario = jwtTokenUtil.getIdIdUsuarioFromRequest(request);
        return consultaService.criarConsulta(consultaDto, idUsuario);
    }

    @GetMapping
    public List<Consultas> findbyIdUsuario(HttpServletRequest request){
        Long idUsuario = jwtTokenUtil.getIdIdUsuarioFromRequest(request);
        return consultaService.findByIdUsuario(idUsuario);
    }

    @PutMapping("/cancelar")
    public void cancelarConsulta(HttpServletRequest request, @RequestBody CancelamentoDto cancelamentoDto) {
        Long idUsuario = jwtTokenUtil.getIdIdUsuarioFromRequest(request);
        consultaService.cancelarConsulta(cancelamentoDto, idUsuario);
    }
    
}
