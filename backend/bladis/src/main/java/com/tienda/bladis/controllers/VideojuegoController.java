package com.tienda.bladis.controllers;

import com.tienda.bladis.dtos.StockDto;
import com.tienda.bladis.dtos.VideojuegoCrearDto;
import com.tienda.bladis.dtos.VideojuegoDetalleDto;
import com.tienda.bladis.enums.Direccion;
import com.tienda.bladis.enums.Orden;
import com.tienda.bladis.enums.Plataforma;
import com.tienda.bladis.models.Videojuego;
import com.tienda.bladis.dtos.VideojuegoFiltroDto;
import com.tienda.bladis.services.CategoriaService;
import com.tienda.bladis.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VideojuegoController {

    @Autowired
    private VideojuegoService videojuegoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/videojuegos/{id}")
    public ResponseEntity<?> mostrarVideojuego(@PathVariable(required = false) Integer id) {

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
            @PathVariable(required = false) Integer id,
            @RequestBody VideojuegoCrearDto videojuego) {

        VideojuegoDetalleDto videojuegoDetalle = videojuegoService.actualizarVideojuego(id, videojuego);

        return ResponseEntity.ok(videojuegoDetalle);
    }

    @DeleteMapping("/admin/videojuegos/{id}")
    public ResponseEntity<?> borrarVideojuego(@PathVariable(required = false) Integer id) {

        videojuegoService.borrarVideojuego(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("/admin/videojuegos/{id}/stock")
    public ResponseEntity<?> actualizarStock(
            @PathVariable(required = false) Integer id,
            @RequestBody StockDto stockDto) {

        VideojuegoDetalleDto videojuegoDetalle = videojuegoService.actualizarStock(id, stockDto);

        return ResponseEntity.ok(videojuegoDetalle);
    }

    @PutMapping("/admin/videojuegos/{id}/reactivar")
    public ResponseEntity<?> reactivarVideojuego(@PathVariable(required = false) Integer id) {

        VideojuegoDetalleDto videojuegoDetalle = videojuegoService.reactivarVideojuego(id);

        return ResponseEntity.ok(videojuegoDetalle);
    }

    @GetMapping("/videojuegos/categoria")
    public ResponseEntity<?> buscarPorCategoria(
            @RequestParam(required = false) Integer categoria
    ) {

        VideojuegoFiltroDto filtros = new VideojuegoFiltroDto();
        filtros.setCategoriaId(categoria);

        return ResponseEntity.ok(videojuegoService.filtrarCategoria(filtros));
    }

    @GetMapping("/videojuegos/texto")
    public ResponseEntity<?> buscarPorTexto(
            @RequestParam(required = false) String q
    ) {

        VideojuegoFiltroDto filtros = new VideojuegoFiltroDto();
        filtros.setQ(q);

        return ResponseEntity.ok(videojuegoService.filtrarNombreDescripcion(filtros));
    }

    @GetMapping("/videojuegos/precio")
    public ResponseEntity<?> filtrarPorPrecio(
            @RequestParam(required = false) Integer precioMin,
            @RequestParam(required = false) Integer precioMax
    ) {

        VideojuegoFiltroDto filtros = new VideojuegoFiltroDto();
        filtros.setPrecioMax(BigDecimal.valueOf(precioMax));
        filtros.setPrecioMax(BigDecimal.valueOf(precioMin));

        return ResponseEntity.ok(videojuegoService.filtrarPrecio(filtros));
    }

    @GetMapping("/videojuegos/disponibles")
    public ResponseEntity<?> soloDisponibles(
            @RequestParam(required = false) Boolean soloDisponibles
    ) {

        VideojuegoFiltroDto filtros = new VideojuegoFiltroDto();
        filtros.setSoloDisponibles(soloDisponibles);

        return ResponseEntity.ok(videojuegoService.filtrarDisponibles(filtros));
    }

    @GetMapping("/videojuegos/ordenar")
    public ResponseEntity<?> ordenarVideojuegos(
            @RequestParam(required = false) Direccion direccion,
            @RequestParam(required = false) Orden orden
    ) {

        VideojuegoFiltroDto filtros = new VideojuegoFiltroDto();
        filtros.setDireccion(direccion);
        filtros.setOrden(orden);

        return ResponseEntity.ok(videojuegoService.filtrarOrdenDireccion(filtros));
    }

    @GetMapping("/videojuegos/plataforma")
    public ResponseEntity<?> filtrarPorPlataforma(
            @RequestParam(required = false) Plataforma plataforma
    ) {

        VideojuegoFiltroDto filtros = new VideojuegoFiltroDto();
        filtros.setPlataforma(plataforma);

        return ResponseEntity.ok(videojuegoService.filtrarPlataforma(filtros));
    }

    @GetMapping("/videojuegos/sku")
    public ResponseEntity<?> filtrarPorSku(
            @RequestParam(required = false) String sku
    ) {

        VideojuegoFiltroDto filtros = new VideojuegoFiltroDto();
        filtros.setSku(sku);

        return ResponseEntity.ok(videojuegoService.filtrarSku(filtros));
    }

    @GetMapping("/videojuegos/stock")
    public ResponseEntity<?> filtrarPorStock(
            @RequestParam(required = false) Integer umbral
    ) {

        VideojuegoFiltroDto filtros = new VideojuegoFiltroDto();
        filtros.setUmbral(umbral);

        return ResponseEntity.ok(videojuegoService.filtrarStock(filtros));
    }
}