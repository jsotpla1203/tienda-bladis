package com.tienda.bladis.controllers;

import com.tienda.bladis.dtos.CategoriaCrearDto;
import com.tienda.bladis.dtos.CategoriaDto;
import com.tienda.bladis.services.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/categorias")
    public ResponseEntity<?> listarCategorias(){
        List<CategoriaDto> lista = categoriaService.listarTodas();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<?> consultarCategoria(@PathVariable Integer id){
        CategoriaDto categoriaDto = categoriaService.obtenerPorId(id);

        if (categoriaDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(categoriaDto);
    }

    @GetMapping("/admin/categorias")
    public ResponseEntity<?> listarCategoriasAdmin(){
        List<CategoriaDto> lista = categoriaService.listarTodas();

        return ResponseEntity.ok(lista);
    }

    @PostMapping("admin/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaCrearDto categoria){
        CategoriaDto nuevaCat = categoriaService.crearCategoria(categoria);

        if (nuevaCat == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCat);
    }


}
