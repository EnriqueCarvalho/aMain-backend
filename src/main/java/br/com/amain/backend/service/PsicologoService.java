package br.com.amain.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.amain.backend.model.Psicologo;
import br.com.amain.backend.repository.PsicologoRepository;

@Service
public class PsicologoService {
    
    @Autowired
    private PsicologoRepository psicologoRepository;
    
    public Psicologo getPsicologoByIdUsuario(Long idUsuario){
        return psicologoRepository.findByIdUsuario(idUsuario);
    }
}
