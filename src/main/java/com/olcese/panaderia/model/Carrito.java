package com.olcese.panaderia.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "carritos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Carrito {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CarritoItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    private LocalDateTime creadoEn = LocalDateTime.now();
    private LocalDateTime actualizadoEn = LocalDateTime.now();

    @PreUpdate
    public void preUpdate(){ actualizadoEn = LocalDateTime.now(); }
}
