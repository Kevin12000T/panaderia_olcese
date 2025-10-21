package com.olcese.panaderia.service.Impl;

import com.olcese.panaderia.model.Producto;
import com.olcese.panaderia.model.Sucursal;
import com.olcese.panaderia.model.dto.CatalogoProductoResponse;
import com.olcese.panaderia.model.dto.SucursalResponse;
import com.olcese.panaderia.repository.ProductoRepository;
import com.olcese.panaderia.repository.SucursalRepository;
import com.olcese.panaderia.service.CatalogoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de catálogo
 * Maneja la lógica de negocio para consultar productos públicos
 */
@Service
@Transactional(readOnly = true) // Solo lectura, optimiza rendimiento
public class CatalogoServiceImpl implements CatalogoService {
    
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    
    public CatalogoServiceImpl(ProductoRepository productoRepository,
                               SucursalRepository sucursalRepository) {
        this.productoRepository = productoRepository;
        this.sucursalRepository = sucursalRepository;
    }
    
    @Override
    public List<CatalogoProductoResponse> listarProductosActivos() {
        // Buscar solo productos activos en la BD
        return productoRepository.findAll().stream()
                .filter(Producto::getActivo) // Filtrar solo activos
                .map(this::convertirACatalogoResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CatalogoProductoResponse> listarProductosPorSucursal(Long sucursalId) {
        // Filtrar productos activos de una sucursal específica
        return productoRepository.findAll().stream()
                .filter(p -> p.getActivo() && p.getSucursal().getId().equals(sucursalId))
                .map(this::convertirACatalogoResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SucursalResponse> listarSucursales() {
        return sucursalRepository.findAll().stream()
                .map(this::convertirASucursalResponse)
                .collect(Collectors.toList());
    }
    
    // Método auxiliar para convertir Producto a CatalogoProductoResponse
    private CatalogoProductoResponse convertirACatalogoResponse(Producto p) {
        return new CatalogoProductoResponse(
                p.getId(),
                p.getNombre(),
                p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría",
                p.getSucursal() != null ? p.getSucursal().getNombre() : "Sin sucursal",
                p.getSucursal() != null ? p.getSucursal().getDistrito() : null,
                p.getPrecio().doubleValue(),
                p.getActivo()
        );
    }
    
    // Método auxiliar para convertir Sucursal a SucursalResponse
    private SucursalResponse convertirASucursalResponse(Sucursal s) {
        return new SucursalResponse(
                s.getId(),
                s.getNombre(),
                s.getDistrito(),
                s.getDireccion(),
                s.getTelefono()
        );
    }
}