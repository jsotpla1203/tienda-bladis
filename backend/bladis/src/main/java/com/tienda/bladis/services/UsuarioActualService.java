package com.tienda.bladis.services;

import com.tienda.bladis.models.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
/*Este servicio guarda temporalmente al usuario autenticado durante la petición para que cualquier controlador pueda pedirlo sin tener que volver a leer el token:*/

@Service
public class UsuarioActualService {

    private static final String ATRIBUTO_USUARIO = "USUARIO_ACTUAL";
    private final HttpServletRequest request;

    public UsuarioActualService(HttpServletRequest request) {
        this.request = request;
    }

    public void setUsuarioActual(Usuario usuario) {
        request.setAttribute(ATRIBUTO_USUARIO, usuario);
    }

    public Usuario getUsuarioActual() {
        return (Usuario) request.getAttribute(ATRIBUTO_USUARIO);
    }
}