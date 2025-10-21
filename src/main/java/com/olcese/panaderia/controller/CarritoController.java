package com.olcese.panaderia.controller;

import com.olcese.panaderia.model.dto.*;
import com.olcese.panaderia.repository.UsuarioRepository;
import com.olcese.panaderia.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService service;
    private final UsuarioRepository usuarioRepository;

    public CarritoController(CarritoService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    private Long userId(Authentication auth){
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .getId();
    }

    @GetMapping
    public ResponseEntity<CarritoResponse> obtener(Authentication auth){
        return ResponseEntity.ok(service.obtenerCarritoActual(userId(auth)));
    }

    @PostMapping("/add")
    public ResponseEntity<CarritoResponse> add(@RequestBody CarritoAddRequest req, Authentication auth){
        return ResponseEntity.ok(service.agregarProducto(userId(auth), req));
    }

    @PatchMapping("/qty")
    public ResponseEntity<CarritoResponse> updateQty(@RequestBody CarritoUpdateQtyRequest req, Authentication auth){
        return ResponseEntity.ok(service.actualizarCantidad(userId(auth), req));
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<CarritoResponse> remove(@PathVariable Long itemId, Authentication auth){
        return ResponseEntity.ok(service.quitarItem(userId(auth), itemId));
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity<Void> vaciar(Authentication auth){
        service.vaciar(userId(auth));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, Long>> checkout(Authentication auth){
        Long pedidoId = service.checkout(userId(auth));
        return ResponseEntity
                .ok()
                .location(URI.create("/boleta/" + pedidoId))
                .body(Map.of("pedidoId", pedidoId));
    }
}
