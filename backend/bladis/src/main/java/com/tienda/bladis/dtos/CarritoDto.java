package com.tienda.bladis.dtos;

import java.math.BigDecimal;
import java.util.List;

public class CarritoDto {
    private Integer numeroArticulos;
    private BigDecimal subtotal;
    private BigDecimal gastosEnvio;
    private BigDecimal total;
    private BigDecimal faltaParaEnvioGratis;
    private List<String> avisos;
    private List<LineaCarritoDto> lineas;

    public CarritoDto() {
    }

    public CarritoDto(Integer numeroArticulos, BigDecimal subtotal, BigDecimal gastosEnvio, BigDecimal total, BigDecimal faltaParaEnvioGratis, List<String> avisos, List<LineaCarritoDto> lineas) {
        this.numeroArticulos = numeroArticulos;
        this.subtotal = subtotal;
        this.gastosEnvio = gastosEnvio;
        this.total = total;
        this.faltaParaEnvioGratis = faltaParaEnvioGratis;
        this.avisos = avisos;
        this.lineas = lineas;
    }

    public Integer getNumeroArticulos() {
        return numeroArticulos;
    }

    public void setNumeroArticulos(Integer numeroArticulos) {
        this.numeroArticulos = numeroArticulos;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(BigDecimal gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getFaltaParaEnvioGratis() {
        return faltaParaEnvioGratis;
    }

    public void setFaltaParaEnvioGratis(BigDecimal faltaParaEnvioGratis) {
        this.faltaParaEnvioGratis = faltaParaEnvioGratis;
    }

    public List<String> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<String> avisos) {
        this.avisos = avisos;
    }

    public List<LineaCarritoDto> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaCarritoDto> lineas) {
        this.lineas = lineas;
    }
}