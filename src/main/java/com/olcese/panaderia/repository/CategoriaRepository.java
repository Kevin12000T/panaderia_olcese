package com.olcese.panaderia.repository;

import com.olcese.panaderia.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
