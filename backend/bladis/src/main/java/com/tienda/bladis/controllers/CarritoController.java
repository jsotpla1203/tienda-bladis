package com.tienda.bladis.controllers;

import com.tienda.bladis.dtos.CarritoDto;
import com.tienda.bladis.dtos.LineaCarritoActualizarDto;
import com.tienda.bladis.dtos.LineaCarritoCrearDto;
import com.tienda.bladis.dtos.LineaCarritoDto;
import com.tienda.bladis.services.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    public ResponseEntity<CarritoDto> verCarrito() {
        CarritoDto carrito = carritoService.verCarrito();
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/lineas")
    public ResponseEntity<LineaCarritoDto> anadirLinea(@RequestBody LineaCarritoCrearDto dto) {
        LineaCarritoDto lineaActualizada = carritoService.anadirVideojuego(dto);
        return ResponseEntity.ok(lineaActualizada);
    }

    @PutMapping("/lineas/{productoId}")
    public ResponseEntity<LineaCarritoDto> actualizarCantidad(@PathVariable Integer productoId,
                                                         @RequestBody LineaCarritoActualizarDto dto) {
        LineaCarritoDto carritoActualizado = carritoService.modificarVideojuego(productoId, dto);
        return ResponseEntity.ok(carritoActualizado);
    }

    @DeleteMapping("/lineas/{productoId}")
    public ResponseEntity<Void> eliminarLinea(@PathVariable Integer productoId) {
        carritoService.quitarLinea(productoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> vaciarCarrito() {
        carritoService.vaciarCarrito();
        return ResponseEntity.noContent().build();
    }
}