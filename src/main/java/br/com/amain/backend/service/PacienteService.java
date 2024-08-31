package br.com.amain.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.amain.backend.model.Paciente;
import br.com.amain.backend.repository.PacienteRepository;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente findByIdUsuario(Long idUsuario){
        return pacienteRepository.findByIdUsuario(idUsuario);
    }
 
    

}