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

    public VideojuegoFiltroDto() {
    }

    public VideojuegoFiltroDto(Integer categoriaId, String q, BigDecimal precioMin, BigDecimal precioMax, Boolean soloDisponibles, Plataforma plataforma) {
        this.categoriaId = categoriaId;
        this.q = q;
        this.precioMin = precioMin;
        this.precioMax = precioMax;
        this.soloDisponibles = soloDisponibles;
        this.plataforma = plataforma;
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
}
