package br.com.amain.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.amain.backend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    
}
