package br.com.amain.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.amain.backend.model.PublicoAlvo;

public interface PublicAlvoRepository extends JpaRepository<PublicoAlvo, Long>{

    
}
