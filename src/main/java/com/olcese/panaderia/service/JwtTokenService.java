package com.olcese.panaderia.service;

import com.olcese.panaderia.model.Usuario;

import java.util.List;

public interface JwtTokenService {
    String generarTokenAcceso(Usuario usuario);
    String generarTokenRefresco(Usuario usuario);
    boolean esTokenValido(String token);
    String extraerUsuario(String token);
    String generarTokenDesdeRefreshToken(String refreshToken);

}
