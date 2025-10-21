package com.olcese.panaderia.model.dto;

public record CarritoUpdateQtyRequest(
        Long itemId,
        Integer cantidad
) {}