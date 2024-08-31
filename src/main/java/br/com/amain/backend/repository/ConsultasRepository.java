package br.com.amain.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.amain.backend.model.Consultas;

public interface  ConsultasRepository extends JpaRepository<Consultas, Long>{
    
}
