package br.com.amain.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.amain.backend.model.PublicoAlvo;
import br.com.amain.backend.repository.PublicoAlvoRepository;

@Service
public class PublicoAlvoService {
    @Autowired
    private PublicoAlvoRepository publicoAlvoRepository;

    public List<PublicoAlvo> findAll(){
        return publicoAlvoRepository.findAll();
    }
}
