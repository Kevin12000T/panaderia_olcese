package com.olcese.panaderia.service;

import com.olcese.panaderia.model.dto.ProductoRequest;
import com.olcese.panaderia.model.Categoria;
import com.olcese.panaderia.model.Producto;
import com.olcese.panaderia.model.Sucursal;
import com.olcese.panaderia.repository.CategoriaRepository;
import com.olcese.panaderia.repository.ProductoRepository;
import com.olcese.panaderia.repository.SucursalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final SucursalRepository sucursalRepository;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository,
                           SucursalRepository sucursalRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.sucursalRepository = sucursalRepository;
    }

    // Listar todos
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    // Crear nuevo producto
    public Producto crear(ProductoRequest request) {
        if (request.categoriaId() == null || request.sucursalId() == null) {
            throw new IllegalArgumentException("Debe especificar IDs válidos de categoría y sucursal");
        }

        if (productoRepository.existsBySku(request.sku())) {
            throw new RuntimeException("Ya existe un producto con el SKU: " + request.sku());
        }

        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + request.categoriaId()));

        Sucursal sucursal = sucursalRepository.findById(request.sucursalId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con ID: " + request.sucursalId()));

        Producto producto = Producto.builder()
                .sku(request.sku().trim())
                .nombre(request.nombre().trim())
                .categoria(categoria)
                .sucursal(sucursal)
                .precio(BigDecimal.valueOf(request.precio()))
                .activo(request.activo())
                .build();

        return productoRepository.save(producto);
    }
}
