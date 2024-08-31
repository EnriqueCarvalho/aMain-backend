package br.com.amain.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.amain.backend.exception.ObjectNotFoundException;
import br.com.amain.backend.model.Psicologo;
import br.com.amain.backend.repository.PsicologoRepository;

@Service
public class PsicologoService {
    
    @Autowired
    private PsicologoRepository psicologoRepository;
    
    public Psicologo getPsicologoByIdUsuario(Long idUsuario){
        return psicologoRepository.findByIdUsuario(idUsuario);
    }

    public List<Psicologo> findAll(){
        return psicologoRepository.findAll();
    }

    public Psicologo findById(Long idPsicologo){
        return psicologoRepository.findById(idPsicologo).orElseThrow(
            () -> new ObjectNotFoundException("Nenhum psicologo encontrado")
        );
    }
}
