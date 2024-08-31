package br.com.amain.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.amain.backend.repository.ConsultasRepository;

@Service
public class ConsultaService {
    
    @Autowired
    private ConsultasRepository consultasRepository;

    

}
