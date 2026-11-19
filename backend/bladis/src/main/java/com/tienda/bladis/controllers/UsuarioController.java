package com.tienda.bladis.controllers;

import com.tienda.bladis.dtos.CambiarRolDto;
import com.tienda.bladis.dtos.UsuarioActualizarDto;
import com.tienda.bladis.dtos.UsuarioDto;
import com.tienda.bladis.models.Usuario;
import com.tienda.bladis.services.UsuarioActualService;
import com.tienda.bladis.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioActualService usuarioActualService;


    @GetMapping("/usuarios/me")
    public ResponseEntity<UsuarioDto> obtenerMiPerfil() {
        Usuario actual = usuarioActualService.getUsuarioActual();
        return ResponseEntity.ok(usuarioService.convertirADto(actual));
    }

    @PutMapping("/usuarios/me")
    public ResponseEntity<UsuarioDto> modificarMiPerfil(@RequestBody UsuarioActualizarDto dto) {
        Usuario actual = usuarioActualService.getUsuarioActual();
        return ResponseEntity.ok(usuarioService.actualizarPerfil(actual, dto));
    }

    @GetMapping("/admin/usuarios")
    public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PutMapping("/admin/usuarios/{id}/rol")
    public ResponseEntity<UsuarioDto> cambiarRol(@PathVariable Integer id, @RequestBody CambiarRolDto dto) {
        return ResponseEntity.ok(usuarioService.cambiarRol(id, dto.getRol()));
    }
}