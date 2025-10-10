package com.olcese.panaderia.service.Impl;

import com.olcese.panaderia.model.Rol;
import com.olcese.panaderia.model.Usuario;
import com.olcese.panaderia.repository.RolRepository;
import com.olcese.panaderia.repository.UsuarioRepository;
import com.olcese.panaderia.service.UsuarioService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              RolRepository rolRepository,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // Encriptar la contraseña
        usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));

        // Asignar rol por defecto si no tiene
        if (usuario.getRol() == null) {
            Rol rolUser = rolRepository.findByNombre("CLIENTE")
                    .orElseThrow(() -> new RuntimeException("El rol CLIENTE no existe en la base de datos"));
            usuario.setRol(rolUser);
        }

        return usuarioRepository.save(usuario);
    }
}
