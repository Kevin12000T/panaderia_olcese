package com.olcese.panaderia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.olcese.panaderia.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
