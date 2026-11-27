package com.tienda.bladis.enums;

public enum EstadoPedido {
    PENDIENTE,
    PAGADO,
    ENVIADO,
    ENTREGADO,
    CANCELADO;

    public boolean puedeCambiarA(EstadoPedido nuevo) {
        return switch (this) {
            case PENDIENTE -> nuevo == PAGADO || nuevo == CANCELADO;
            case PAGADO -> nuevo == ENVIADO || nuevo == CANCELADO;
            case ENVIADO -> nuevo == ENTREGADO;
            case ENTREGADO, CANCELADO -> false;
        };
    }
}
