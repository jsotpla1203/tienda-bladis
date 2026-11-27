package com.tienda.bladis.dtos;

import com.tienda.bladis.enums.EstadoPedido;
import com.tienda.bladis.models.LineaPedido;
import com.tienda.bladis.models.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoDto {

    private Integer id;
    private String codigo;
    private String usuario;
    private LocalDate fecha;
    private EstadoPedido estado;
    private String direccionEnvio;
    private BigDecimal subtotal;
    private BigDecimal gastoEnvio;
    private BigDecimal total;
    private List<LineaPedidoDto> lineas;

    public PedidoDto() {
    }

    public PedidoDto(Integer id, String codigo, String usuario, LocalDate fecha, EstadoPedido estado, String direccionEnvio, BigDecimal subtotal, BigDecimal gastoEnvio, BigDecimal total, List<LineaPedidoDto> lineas) {
        this.id = id;
        this.codigo = codigo;
        this.usuario = usuario;
        this.fecha = fecha;
        this.estado = estado;
        this.direccionEnvio = direccionEnvio;
        this.subtotal = subtotal;
        this.gastoEnvio = gastoEnvio;
        this.total = total;
        this.lineas = lineas;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getGastoEnvio() {
        return gastoEnvio;
    }

    public void setGastoEnvio(BigDecimal gastoEnvio) {
        this.gastoEnvio = gastoEnvio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<LineaPedidoDto> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaPedidoDto> lineas) {
        this.lineas = lineas;
    }
}
