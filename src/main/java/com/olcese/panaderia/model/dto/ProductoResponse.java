package com.olcese.panaderia.model.dto;

public record ProductoResponse(
        Long id,
        String nombre,
        String categoria,
        String sucursal,
        Double precio,
        Boolean activo
) {}
