package com.tienda.bladis.dtos;

public class StockDto {

    private Integer cantidad;

    public StockDto() {
    }

    public StockDto(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
