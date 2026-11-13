package com.tienda.bladis.dtos;

import com.tienda.bladis.enums.Plataforma;
import com.tienda.bladis.models.Categoria;

import java.math.BigDecimal;

public class VideojuegoResumenDto {

    private Integer id;
    private String sku;
    private String nombre;
    private BigDecimal precio;
    private String imagenUrl;
    private Boolean destacado;
    private Categoria categoria;
    private Plataforma plataforma;
    private String desarrolladora;

    public VideojuegoResumenDto() {
    }

    public VideojuegoResumenDto(Integer id, String sku, String nombre, BigDecimal precio, String imagenUrl, Boolean destacado, Categoria categoria, Plataforma plataforma, String desarrolladora) {
        this.id = id;
        this.sku = sku;
        this.nombre = nombre;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.destacado = destacado;
        this.categoria = categoria;
        this.plataforma = plataforma;
        this.desarrolladora = desarrolladora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }
}
