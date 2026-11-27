package com.tienda.bladis.services;

import com.tienda.bladis.dtos.EstadoPedidoDto;
import com.tienda.bladis.dtos.FiltrosPedidoDto;
import com.tienda.bladis.dtos.LineaPedidoDto;
import com.tienda.bladis.dtos.PedidoDto;
import com.tienda.bladis.enums.EstadoPedido;
import com.tienda.bladis.exceptions.ConflictoException;
import com.tienda.bladis.exceptions.FormatoInvalidoException;
import com.tienda.bladis.exceptions.RecursoNoEncontradoException;
import com.tienda.bladis.models.LineaPedido;
import com.tienda.bladis.models.Pedido;
import com.tienda.bladis.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private UsuarioActualService usuarioActualService;

    @Autowired


    List<Pedido> pedidos = new ArrayList<>();

    public List<PedidoDto> filtrarPorEstado(EstadoPedido estado) {

        List<Pedido> pedidosDtos = pedidos.stream()
                .filter(p -> estado == null ||
                        p.getEstado().equals(estado))
                .toList();

        return crearListaPedidoDto(pedidosDtos);
    }

    public PedidoDto devolverPedidoCliente(Integer id) {

        return crearPedidoDto(buscarPedidoCliente(id));
    }

    public void cancelarPedido(Integer id) {

        Pedido pedido = buscarPedidoCliente(id);

        if(pedido.getEstado().puedeCambiarA(EstadoPedido.CANCELADO)) {
            pedido.setEstado(EstadoPedido.CANCELADO);
        } else {
            throw new ConflictoException(
                    "No se puede cambiar el pedido de " + pedido.getEstado() + " a " + EstadoPedido.CANCELADO
            );
        }
    }

    public List<PedidoDto> filtrarPedidos(FiltrosPedidoDto filtros) {

        if(filtros.getDesde() != null && filtros.getHasta() != null && filtros.getDesde().isAfter(filtros.getHasta())) {
            throw new FormatoInvalidoException("La fecha desde tiene que estar antes que hasta");
        }

        List<Pedido> pedidosList = pedidos.stream()
                .filter(p -> filtros.getEstadoPedido() == null ||
                        p.getEstado().equals(filtros.getEstadoPedido()))
                .filter(p -> filtros.getDesde() == null ||
                        p.getFecha().compareTo(filtros.getDesde()) >= 0)
                .filter(p -> filtros.getHasta() == null ||
                        p.getFecha().compareTo(filtros.getHasta()) <= 0)
                .filter(p -> filtros.getEmail() == null ||
                        p.getUsuario().getEmail().equals(filtros.getEmail()))
                .toList();

        return crearListaPedidoDto(pedidosList);
    }

    public PedidoDto devolverPedidoAdmin(Integer id) {

        return crearPedidoDto(buscarPedidoAdmin(id));
    }

    public void cambiarEstado(Integer id, EstadoPedidoDto estado) {

        Pedido pedido = buscarPedidoAdmin(id);

        if(pedido.getEstado().puedeCambiarA(estado.getEstadoPedido())) {
            pedido.setEstado(estado.getEstadoPedido());
        } else {
            throw new ConflictoException(
                    "No se puede cambiar el pedido de " + pedido.getEstado() + " a " + estado.getEstadoPedido()
            );
        }
    }

    private Pedido buscarPedidoAdmin(Integer id) {

        return pedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado un pedido con ID " + id));
    }

    private Pedido buscarPedidoCliente(Integer id) {

        Usuario usuario = usuarioActualService.getUsuarioActual();

        Pedido pedido;

        for (Pedido pedidoBuscar:pedidos) {

            if(pedidoBuscar.getId().equals(id)) {

                if(!pedidoBuscar.getUsuario().equals(usuario)) {
                    throw new RecursoNoEncontradoException("Pedido no encontrado");
                } else {
                    return pedidoBuscar;
                }
            }
        }

        throw new RecursoNoEncontradoException("Pedido no encontrado");
    }

    private List<LineaPedidoDto> crearLineasPedidoDto(List<LineaPedido> lineas) {

        List<LineaPedidoDto> lineasPedido = new ArrayList<>();

        for (LineaPedido lineaPedido:lineas) {
            lineasPedido.add(new LineaPedidoDto(
                    lineaPedido.getNombreProducto(),
                    lineaPedido.getVideojuego().getPlataforma(),
                    lineaPedido.getVideojuego().getImagenUrl(),
                    lineaPedido.getPrecioUnitario(),
                    lineaPedido.getCantidad(),
                    lineaPedido.getSubtotal()
            ));
        }

        return lineasPedido;
    }

    private PedidoDto crearPedidoDto(Pedido pedido) {

        return new PedidoDto(
                pedido.getId(),
                pedido.getCodigo(),
                pedido.getUsuario().getNombre(),
                pedido.getFecha(),
                pedido.getEstado(),
                pedido.getDireccionEnvio(),
                pedido.getSubtotal(),
                pedido.getGastoEnvio(),
                pedido.getTotal(),
                crearLineasPedidoDto(pedido.getLineas())
        );
    }

    private List<PedidoDto> crearListaPedidoDto(List<Pedido> pedidos) {

        List<PedidoDto> pedidosDtos = new ArrayList<>();

        for (Pedido pedido:pedidos) {
            pedidosDtos.add(crearPedidoDto(pedido));
        }

        return pedidosDtos;
    }
}
