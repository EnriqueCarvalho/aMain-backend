package br.com.amain.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.amain.backend.model.Psicologo;

public interface PsicologoRepository extends  JpaRepository<Psicologo, Long> {
    
}
