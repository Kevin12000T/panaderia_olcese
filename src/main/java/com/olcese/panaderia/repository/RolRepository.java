package com.olcese.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.olcese.panaderia.model.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
