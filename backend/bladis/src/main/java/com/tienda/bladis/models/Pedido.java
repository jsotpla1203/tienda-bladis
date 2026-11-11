package com.tienda.bladis.models;

import com.tienda.bladis.enums.EstadoPedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Pedido {

    private static Integer idIncrement = 1;
    private Integer id;
    private String codigo;
    private Usuario usuario;
    private LocalDate fecha;
    private EstadoPedido estado;
    private String direccionEnvio;
    private BigDecimal subtotal;
    private BigDecimal gastoEnvio;
    private BigDecimal total;
    private List<LineaPedido> lineas;

    public Pedido() {
    }

    public Pedido(String codigo, Usuario usuario, LocalDate fecha, EstadoPedido estado, String direccionEnvio, BigDecimal subtotal, BigDecimal gastoEnvio, BigDecimal total, List<LineaPedido> lineas) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.fecha = fecha;
        this.estado = estado;
        this.direccionEnvio = direccionEnvio;
        this.subtotal = subtotal;
        this.gastoEnvio = gastoEnvio;
        this.total = total;
        this.lineas = lineas;
        this.id = idIncrement++;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setGastoEnvio(BigDecimal gastoEnvio) {
        this.gastoEnvio = gastoEnvio;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setLineas(List<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getGastoEnvio() {
        return gastoEnvio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }
}
