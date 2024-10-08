package br.com.amain.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.amain.backend.dto.CancelamentoDto;
import br.com.amain.backend.dto.ConsultaDto;
import br.com.amain.backend.exception.DadosInvalidosException;
import br.com.amain.backend.exception.ObjectNotFoundException;
import br.com.amain.backend.model.Consultas;
import br.com.amain.backend.model.Paciente;
import br.com.amain.backend.model.Psicologo;
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
    public Consultas criarConsulta(ConsultaDto consultaDto, Long idUsuario){
        Consultas consulta = new Consultas();
        consulta.setData(consultaDto.getData());
        consulta.setHora(consultaDto.getHora());
        Paciente paciente = pacienteService.findByIdUsuarioThrow(idUsuario);
        if(paciente == null){
            throw new ObjectNotFoundException("Paciente não encontrado");
        }
        Psicologo psicologo = psicologoService.findById(consultaDto.getIdPsicologo());
        validaAgendaPsicologo(consultaDto, psicologo);
        consulta.setPsicologo(psicologo);
        consulta.setPaciente(paciente);
        consulta.setLinkMeet(LINK_MEET);

        return consultasRepository.save(consulta);
    }


    public void validaAgendaPsicologo(ConsultaDto consultaDto, Psicologo psicologo){
        if(consultasRepository.findByDataAndHoraAndPsicologo(consultaDto.getData(), consultaDto.getHora(), psicologo) != null){
            throw new DadosInvalidosException("O psicologo já possui uma agenda neste horário");
        }
    } 

    public List<Consultas> findByIdUsuario(Long idUsuario){
        Psicologo psicologo = psicologoService.getPsicologoByIdUsuario(idUsuario);     
        Paciente paciente = pacienteService.findByIdUsuario(idUsuario);
        if(psicologo != null){
            return findByPsicologo(psicologo);
        }else{
            return findByPaciente(paciente);
        }
    }


    private List<Consultas> findByPsicologo(Psicologo psicologo){
        return consultasRepository.findByPsicologo(psicologo);
    }

    private List<Consultas> findByPaciente(Paciente paciente){
        return consultasRepository.findByPaciente(paciente);
    }


    public void cancelarConsulta(CancelamentoDto cancelamentoDto, Long idUsuario){
        Consultas consultas = consultasRepository.findById(cancelamentoDto.getIdConsulta()).orElseThrow(
            () -> new ObjectNotFoundException("Consulta não encontrada")
        );

        consultas.setDtCancelamento(cancelamentoDto.getDtCancelamento());
        consultas.setHrCancelamento(cancelamentoDto.getHrCancelamento());
        consultas.setMotivoCancelamento(cancelamentoDto.getMotivoCancelamento());
        consultasRepository.save(consultas);

    }

}
