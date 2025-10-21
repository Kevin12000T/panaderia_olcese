package com.olcese.panaderia.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaResponse(
        Long id,
        Long usuarioId,
        String nombreUsuario,
        Long sucursalId,
        String nombreSucursal,
        String direccionSucursal,
        LocalDate fechaReserva,
        LocalTime horaInicio,
        LocalTime horaFin,
        Integer numeroPersonas,
        String estado,
        BigDecimal total
) {}
