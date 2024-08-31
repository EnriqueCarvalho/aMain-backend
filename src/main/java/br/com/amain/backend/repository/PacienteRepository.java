package br.com.amain.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.amain.backend.model.Paciente;

public interface  PacienteRepository extends JpaRepository<Paciente, Long>{
    
    @Query("select p from Paciente p where p.usuario.idUsuario = :idUsuario")
    public Paciente findByIdUsuario(Long idUsuario);
}
