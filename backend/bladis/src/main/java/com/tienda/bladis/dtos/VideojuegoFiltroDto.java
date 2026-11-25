package com.tienda.bladis.dtos;

import com.tienda.bladis.enums.Direccion;
import com.tienda.bladis.enums.Orden;
import com.tienda.bladis.enums.Plataforma;

import java.math.BigDecimal;

public class VideojuegoFiltroDto {

    private Integer categoriaId;
    private String q;
    private BigDecimal precioMin;
    private BigDecimal precioMax;
    private Boolean soloDisponibles;
    private Plataforma plataforma;
    private Orden orden;
    private Direccion direccion;
    private String sku;
    private Integer umbral;

    public VideojuegoFiltroDto() {
    }

    public VideojuegoFiltroDto(Integer categoriaId, String q, BigDecimal precioMin, BigDecimal precioMax, Boolean soloDisponibles, Plataforma plataforma, Orden orden, Direccion direccion, String sku, Integer umbral) {
        this.categoriaId = categoriaId;
        this.q = q;
        this.precioMin = precioMin;
        this.precioMax = precioMax;
        this.soloDisponibles = soloDisponibles;
        this.plataforma = plataforma;
        this.orden = orden;
        this.direccion = direccion;
        this.sku = sku;
        this.umbral = umbral;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public BigDecimal getPrecioMin() {
        return precioMin;
    }

    public void setPrecioMin(BigDecimal precioMin) {
        this.precioMin = precioMin;
    }

    public BigDecimal getPrecioMax() {
        return precioMax;
    }

    public void setPrecioMax(BigDecimal precioMax) {
        this.precioMax = precioMax;
    }

    public Boolean getSoloDisponibles() {
        return soloDisponibles;
    }

    public void setSoloDisponibles(Boolean soloDisponibles) {
        this.soloDisponibles = soloDisponibles;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getUmbral() {
        return umbral;
    }

    public void setUmbral(Integer umbral) {
        this.umbral = umbral;
    }
}
