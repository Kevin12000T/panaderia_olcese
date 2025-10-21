package com.olcese.panaderia.repository;

import com.olcese.panaderia.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // ========== MÉTODOS PARA CATÁLOGO ==========

    // Buscar solo productos activos
    List<Producto> findByActivoTrue();

    // Buscar productos activos de una sucursal
    List<Producto> findBySucursalIdAndActivoTrue(Long sucursalId);

    // Buscar productos activos de una categoría
    List<Producto> findByCategoriaIdAndActivoTrue(Long categoriaId);
}