package com.olcese.panaderia.model.dto;


public record UsuarioDTO(
        Long id,
        String nombre,
        String apellido,
        String email
) {}