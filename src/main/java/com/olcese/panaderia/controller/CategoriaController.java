package com.olcese.panaderia.controller;

import com.olcese.panaderia.model.dto.CategoriaResponse;
import com.olcese.panaderia.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaResponse> listarCategorias() {
        return categoriaService.listarTodas()
                .stream()
                .map(c -> new CategoriaResponse(
                        c.getId(),
                        c.getNombre(),
                        c.getDescripcion()
                ))
                .toList();
    }
}
