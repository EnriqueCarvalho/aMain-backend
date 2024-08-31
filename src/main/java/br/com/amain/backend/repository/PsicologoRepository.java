package br.com.amain.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.amain.backend.model.Psicologo;

public interface PsicologoRepository extends  JpaRepository<Psicologo, Long> {
    
    @Query("select p from Psicologo p where p.usuario.idUsuario = :idUsuario")
    public Psicologo findByIdUsuario(Long idUsuario);
}
