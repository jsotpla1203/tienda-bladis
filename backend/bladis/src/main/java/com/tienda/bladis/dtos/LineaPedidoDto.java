package com.tienda.bladis.dtos;

import com.tienda.bladis.enums.Plataforma;

import java.math.BigDecimal;

public class LineaPedidoDto {

    private String nombreProducto;
    private Plataforma plataforma;
    private String imagenUrl;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private BigDecimal subtotal;

    public LineaPedidoDto() {
    }

    public LineaPedidoDto(String nombreProducto, Plataforma plataforma, String imagenUrl, BigDecimal precioUnitario, Integer cantidad, BigDecimal subtotal) {
        this.nombreProducto = nombreProducto;
        this.plataforma = plataforma;
        this.imagenUrl = imagenUrl;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
