package com.olcese.panaderia.service;

import com.olcese.panaderia.model.dto.CatalogoProductoResponse;
import com.olcese.panaderia.model.dto.SucursalResponse;

import java.util.List;

/**
 * Interfaz del servicio de catálogo
 * Proporciona métodos para consultar productos y sucursales disponibles
 */
public interface CatalogoService {
    
    // Listar todos los productos activos del catálogo
    List<CatalogoProductoResponse> listarProductosActivos();
    
    // Listar productos por sucursal
    List<CatalogoProductoResponse> listarProductosPorSucursal(Long sucursalId);
    
    // Listar todas las sucursales
    List<SucursalResponse> listarSucursales();
}