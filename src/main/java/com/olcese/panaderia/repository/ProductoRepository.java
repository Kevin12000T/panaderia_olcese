package com.olcese.panaderia.repository;

import com.olcese.panaderia.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findBySku(String sku);
    boolean existsBySku(String sku);
}
