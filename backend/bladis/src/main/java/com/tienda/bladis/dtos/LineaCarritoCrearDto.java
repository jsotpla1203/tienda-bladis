package com.tienda.bladis.dtos;

public class LineaCarritoCrearDto {
    private Integer videojuegoId;
    private Integer cantidad;

    public LineaCarritoCrearDto() {
    }

    public LineaCarritoCrearDto(Integer videojuegoId, Integer cantidad) {
        this.videojuegoId = videojuegoId;
        this.cantidad = cantidad;
    }

    public Integer getVideojuegoId() {
        return videojuegoId;
    }

    public void setVideojuegoId(Integer videojuegoId) {
        this.videojuegoId = videojuegoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
