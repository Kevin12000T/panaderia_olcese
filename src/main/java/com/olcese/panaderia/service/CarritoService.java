package com.olcese.panaderia.service;

import com.olcese.panaderia.model.dto.*;
public interface CarritoService {
    CarritoResponse obtenerCarritoActual(Long usuarioId);
    CarritoResponse agregarProducto(Long usuarioId, CarritoAddRequest req);
    CarritoResponse actualizarCantidad(Long usuarioId, CarritoUpdateQtyRequest req);
    CarritoResponse quitarItem(Long usuarioId, Long itemId);
    void vaciar(Long usuarioId);
    Long checkout(Long usuarioId); 
}
