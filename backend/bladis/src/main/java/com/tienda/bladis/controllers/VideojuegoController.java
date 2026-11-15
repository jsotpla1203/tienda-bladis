package com.tienda.bladis.controllers;

import com.tienda.bladis.dtos.VideojuegoCrearDto;
import com.tienda.bladis.dtos.VideojuegoDetalleDto;
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
    public ResponseEntity<?> mostrarProducto(@PathVariable Integer id) {

        VideojuegoDetalleDto videojuegoDto = videojuegoService.devolverVideojuegoActivo(id);

        if(videojuegoDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(videojuegoDto);
        }
    }

    @GetMapping("/videojuegos/destacados")
    public ResponseEntity<?> mostrarDestacados() {
        return ResponseEntity.ok(videojuegoService.devolverVideojuegosActivos());
    }

    @GetMapping("/admin/videojuegos/")
    public ResponseEntity<?> listarProductos() {
        return ResponseEntity.ok(videojuegoService.devolverVideojuegos());
    }

    @PostMapping("/admin/videojuegos/")
    public ResponseEntity<?> crearProducto(@RequestBody VideojuegoCrearDto videojuego) {

        System.out.println(videojuego.getCategoria());

        return ResponseEntity.ok().build();
    }
}