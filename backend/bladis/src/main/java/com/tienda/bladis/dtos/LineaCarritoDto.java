package com.tienda.bladis.dtos;

import com.tienda.bladis.enums.Plataforma;

import java.math.BigDecimal;

public class LineaCarritoDto {
    private String nombre;
    private String imagenUrl;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private Plataforma plataforma;

    public LineaCarritoDto() {
    }

    public LineaCarritoDto(String nombre, String imagenUrl, BigDecimal precioUnitario, Integer cantidad, Plataforma plataforma) {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.plataforma = plataforma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }
}
