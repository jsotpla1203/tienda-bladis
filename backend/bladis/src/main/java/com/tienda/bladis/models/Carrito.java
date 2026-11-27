package com.tienda.bladis.models;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private Usuario usuario;
    private List<LineaCarrito> lineas = new ArrayList<>();

    public Carrito() {
    }

    public Carrito(Usuario usuario, List<LineaCarrito> lineas) {
        this.usuario = usuario;
        this.lineas = lineas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<LineaCarrito> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaCarrito> lineas) {
        this.lineas = lineas;
    }
}
