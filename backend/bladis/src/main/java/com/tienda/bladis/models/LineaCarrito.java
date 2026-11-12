package com.tienda.bladis.models;

public class LineaCarrito {

    private Videojuego videojuego;
    private Integer cantidad;

    public LineaCarrito() {
    }

    public Videojuego getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
