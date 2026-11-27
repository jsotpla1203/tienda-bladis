package com.tienda.bladis.dtos;

import com.tienda.bladis.enums.EstadoPedido;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FiltrosPedidoDto {

    private EstadoPedido estadoPedido;
    private LocalDate desde;
    private LocalDate hasta;
    private String email;

    public FiltrosPedidoDto() {
    }

    public FiltrosPedidoDto(EstadoPedido estadoPedido, LocalDate desde, LocalDate hasta, String email) {
        this.estadoPedido = estadoPedido;
        this.desde = desde;
        this.hasta = hasta;
        this.email = email;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public LocalDate getDesde() {
        return desde;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
