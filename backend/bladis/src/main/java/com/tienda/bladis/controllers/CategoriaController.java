package com.tienda.bladis.controllers;

import com.tienda.bladis.dtos.CategoriaCrearDto;
import com.tienda.bladis.dtos.CategoriaDto;
import com.tienda.bladis.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    public ResponseEntity<?> listarCategorias(){
        List<CategoriaDto> lista = categoriaService.listarTodas();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<?> consultarCategoria(@PathVariable Integer id){
        CategoriaDto categoriaDto = categoriaService.obtenerPorId(id);

        return ResponseEntity.ok(categoriaDto);
    }

    @GetMapping("/admin/categorias")
    public ResponseEntity<?> listarCategoriasAdmin(){
        List<CategoriaDto> lista = categoriaService.listarTodas();

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/admin/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaCrearDto categoria){
        CategoriaDto nuevaCat = categoriaService.crearCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCat);
    }

    @PutMapping("/admin/categorias/{id}")
    public ResponseEntity<?> modificarCategoria(@PathVariable Integer id,
                                                @RequestBody CategoriaCrearDto entrada){
        CategoriaDto categoriaDto = categoriaService.modificarCategoria(id, entrada);

        return ResponseEntity.ok(categoriaDto);
    }

    @DeleteMapping("/admin/categorias/{id}")
    public ResponseEntity<Void> borrarCategoria(@PathVariable Integer id,
                                             @RequestBody CategoriaDto categoria){
        categoriaService.borrarCategoria(id, categoria);

        return ResponseEntity.noContent().build();
    }
}