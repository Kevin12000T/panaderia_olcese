package com.olcese.panaderia.model.dto;

import java.math.BigDecimal;

public record ReporteResumen(
    BigDecimal totalVentas,
    long pedidos,
    BigDecimal ticketPromedio
) {}
