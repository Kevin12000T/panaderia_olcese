package com.olcese.panaderia.model.dto;

public record CarritoAddRequest(
        Long productoId,
        Integer cantidad
) {}
