package com.olcese.panaderia.controller;

import com.olcese.panaderia.model.dto.ProductoRequest;
import com.olcese.panaderia.model.dto.ProductoResponse;
import com.olcese.panaderia.model.Producto;
import com.olcese.panaderia.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service){
        this.service = service;
    }

    // Vista principal
    @GetMapping({"/","/inicio","/productos"})
    public String inicio(Model model){
        var lista = service.listar().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        model.addAttribute("productos", lista);
        return "productos"; // plantilla Thymeleaf
    }

    // API REST - Crear producto
    @PostMapping("/api/productos")
    @ResponseBody
    public ResponseEntity<?> crear(@RequestBody @Validated ProductoRequest req){
        try {
            var p = service.crear(req);
            return ResponseEntity.ok(toResponse(p));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Conversión a DTO de respuesta
    private ProductoResponse toResponse(Producto p){
        return new ProductoResponse(
                p.getId(),
                p.getSku(),
                p.getNombre(),
                p.getCategoria() != null ? p.getCategoria().getNombre() : null,
                p.getSucursal() != null ? p.getSucursal().getNombre() : null,
                p.getPrecio().doubleValue(),
                p.getActivo()
        );
    }
}
