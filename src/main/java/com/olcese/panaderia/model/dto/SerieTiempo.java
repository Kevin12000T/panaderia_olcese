package com.olcese.panaderia.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SerieTiempo(
    LocalDate fecha,
    BigDecimal total
) {}