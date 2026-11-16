package com.tienda.bladis.controllers;

import com.tienda.bladis.dtos.StockDto;
import com.tienda.bladis.dtos.VideojuegoCrearDto;
import com.tienda.bladis.dtos.VideojuegoDetalleDto;
import com.tienda.bladis.models.Videojuego;
import com.tienda.bladis.dtos.VideojuegoFiltroDto;
import com.tienda.bladis.services.CategoriaService;
import com.tienda.bladis.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VideojuegoController {

    @Autowired
    private VideojuegoService videojuegoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/videojuegos/{id}")
    public ResponseEntity<?> mostrarVideojuego(@PathVariable Integer id) {

        VideojuegoDetalleDto videojuegoDto = videojuegoService.devolverVideojuegoActivo(id);

        return ResponseEntity.ok(videojuegoDto);
    }

    @GetMapping("/videojuegos/destacados")
    public ResponseEntity<?> mostrarDestacados() {
        return ResponseEntity.ok(videojuegoService.devolverVideojuegosActivos());
    }

    @GetMapping("/admin/videojuegos")
    public ResponseEntity<?> listarVideojuegos() {
        return ResponseEntity.ok(videojuegoService.devolverVideojuegos());
    }

    @PostMapping("/admin/videojuegos")
    public ResponseEntity<?> crearVideojuego(@RequestBody VideojuegoCrearDto videojuego) {

        VideojuegoDetalleDto videojuegoDetalle = videojuegoService.crearVideojuego(videojuego);

        return ResponseEntity.status(HttpStatus.CREATED).body(videojuegoDetalle);
    }

    @PutMapping("/admin/videojuegos/{id}")
    public ResponseEntity<?> actualizarVideojuego(
            @PathVariable Integer id,
            @RequestBody VideojuegoCrearDto videojuego) {

        VideojuegoDetalleDto videojuegoDetalle = videojuegoService.actualizarVideojuego(id, videojuego);

        return ResponseEntity.ok(videojuegoDetalle);
    }

    @DeleteMapping("/admin/videojuegos/{id}")
    public ResponseEntity<?> borrarVideojuego(@PathVariable Integer id) {

        videojuegoService.borrarVideojuego(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("/admin/videojuegos/{id}/stock")
    public ResponseEntity<?> actualizarStock(
            @PathVariable Integer id,
            @RequestBody StockDto stockDto) {

        VideojuegoDetalleDto videojuegoDetalle = videojuegoService.actualizarStock(id, stockDto);

        return ResponseEntity.ok(videojuegoDetalle);
    }

    @PutMapping("/admin/videojuegos/{id}/reactivar")
    public ResponseEntity<?> reactivarVideojuego(@PathVariable Integer id) {

        VideojuegoDetalleDto videojuegoDetalle = videojuegoService.reactivarVideojuego(id);

        return ResponseEntity.ok(videojuegoDetalle);
    }
}