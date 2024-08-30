package br.com.amain.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.amain.backend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndPassword(String email, String password);
}
