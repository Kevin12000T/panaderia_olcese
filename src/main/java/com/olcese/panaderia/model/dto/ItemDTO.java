package com.olcese.panaderia.model.dto;

import java.math.BigDecimal;

public record ItemDTO(
        Long id,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal,
        ProductoDTO producto
) {}