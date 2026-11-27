package com.tienda.bladis.dtos;

import com.tienda.bladis.enums.EstadoPedido;

public class EstadoPedidoDto {

    EstadoPedido estadoPedido;

    public EstadoPedidoDto() {
    }

    public EstadoPedidoDto(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }
}
