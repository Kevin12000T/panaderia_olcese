package com.olcese.panaderia.service;

import com.olcese.panaderia.model.dto.LoginRequest;
import com.olcese.panaderia.model.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    String refrescarToken(String refreshToken);


}
