package br.com.amain.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.amain.backend.dto.ConsultaDto;
import br.com.amain.backend.model.Consultas;
import br.com.amain.backend.repository.ConsultasRepository;

@Service
public class ConsultaService {
    
    @Autowired
    private ConsultasRepository consultasRepository;
    @Autowired
    private PsicologoService psicologoService;
    @Autowired
    private PacienteService pacienteService; 

    private final String LINK_MEET = "https://meet.google.com/byc-asaa-kim";

    
    
    @Transactional
    public Consultas criarConsulta(ConsultaDto consultaDto){
        
        Consultas consulta = new Consultas();
        consulta.setData(consultaDto.getData());
        consulta.setHora(consultaDto.getHora());
        // Paciente paciente = pacienteService.findById(idPaciente);
        // Psicologo psicologo = psicologoService.findById(idPsicologo);
        // consulta.setPsicologo(psicologo);
        // consulta.setPaciente(paciente);

        return consultasRepository.save(consulta);
    }

}
