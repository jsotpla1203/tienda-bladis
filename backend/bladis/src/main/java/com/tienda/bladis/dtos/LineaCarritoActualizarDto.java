package com.tienda.bladis.dtos;

public class LineaCarritoActualizarDto {
    private Integer cantidad;

    public LineaCarritoActualizarDto() {
    }

    public LineaCarritoActualizarDto(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
