package com.olcese.panaderia.model.dto;

public record ProductoDTO(
        Long id,
        String nombre,
        SucursalDTO sucursal
) {}