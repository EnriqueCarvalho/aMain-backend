package br.com.amain.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.amain.backend.model.AreaAtuacao;
import br.com.amain.backend.repository.AreaAtuacaoRepository;

@Service
public class AreaAtuacaoService {
    @Autowired
    private AreaAtuacaoRepository areaAtuacaoRepository;

    public List<AreaAtuacao> getAreas(){
        return areaAtuacaoRepository.findAll();
    }
}
