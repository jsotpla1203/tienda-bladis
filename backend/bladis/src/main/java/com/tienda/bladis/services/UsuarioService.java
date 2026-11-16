package com.tienda.bladis.services;

import com.tienda.bladis.dtos.UsuarioActualizarDto;
import com.tienda.bladis.dtos.UsuarioDto;
import com.tienda.bladis.enums.Rol;
import com.tienda.bladis.exceptions.FormatoInvalidoException;
import com.tienda.bladis.exceptions.RecursoNoEncontradoException;
import com.tienda.bladis.models.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();

    @Value("${tienda.admins:}")// Leer la lista de correos administradores de application.properties
    private String adminsConfig;

    public Usuario obtenerCrearUsuario(String uid, String email, String nombreFirebase) {
        for (Usuario u : usuarios) {
            if (u.getUid().equals(uid)) {
                return u;
            }
        }

        Rol rolAsignado = Rol.CLIENTE;
        if (esAdmin(email)){
            rolAsignado = Rol.ADMIN;
        }

        String nombreInicial = nombreFirebase;
        if (nombreInicial == null || nombreInicial.isBlank()) {
            nombreInicial = email;
        }

        Usuario nuevo = new Usuario(uid, email, nombreInicial, "", rolAsignado, LocalDate.now());
        usuarios.add(nuevo);
        return nuevo;
    }

    public UsuarioDto actualizarPerfil(Usuario usuarioActual, UsuarioActualizarDto dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new FormatoInvalidoException("El nombre no puede estar vacío");
        }

        usuarioActual.setNombre(dto.getNombre());

        if (dto.getDireccionEnvio() != null) {
            usuarioActual.setDireccionEnvio(dto.getDireccionEnvio());
        } else {
            usuarioActual.setDireccionEnvio("");
        }

        return convertirADto(usuarioActual);
    }

    public List<UsuarioDto> listarTodos() {
        List<UsuarioDto> listaDtos = new ArrayList<>();

        for (Usuario u : usuarios) {
            listaDtos.add(convertirADto(u));
        }

        return listaDtos;
    }

    public UsuarioDto cambiarRol(Integer id, Rol nuevoRol) {
        if (nuevoRol == null) {
            throw new FormatoInvalidoException("Debe indicar un rol válido (CLIENTE o ADMIN)");
        }

        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                usuario.setRol(nuevoRol);
                return convertirADto(usuario);
            }
        }

        throw new RecursoNoEncontradoException("No existe ningún usuario con ID: " + id);
    }

    public UsuarioDto convertirADto(Usuario u) {
        return new UsuarioDto(
                u.getId(),
                u.getUid(),
                u.getEmail(),
                u.getNombre(),
                u.getDireccionEnvio(),
                u.getRol(),
                u.getFechaAlta()
        );
    }

    private boolean esAdmin(String email) {
        if (email == null || adminsConfig.isBlank()) {
            return false;
        }

        String[] correosAdmin = adminsConfig.split(",");
        for (String admin : correosAdmin) {
            if (admin.trim().equalsIgnoreCase(email)) {
                return true;
            }
        }

        return false;
    }
}