package com.tienda.bladis.controllers;

import com.tienda.bladis.dtos.FiltrosPedidoDto;
import com.tienda.bladis.enums.EstadoPedido;
import com.tienda.bladis.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /*
    @PostMapping("/pedidos")
    public ResponseEntity<?> filtarPorEstado(@RequestParam(required = false) EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.filtrarPorEstado(estado));
    }
    */

    @GetMapping("/pedidos")
    public ResponseEntity<?> filtarPorEstado(@RequestParam(required = false) EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.filtrarPorEstado(estado));
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<?> filtarPorIdCliente(@PathVariable(required = false) Integer id) {
        return ResponseEntity.ok(pedidoService.devolverPedidoCliente(id));
    }

    @PutMapping("/pedidos/{id}/cancelar")
    public ResponseEntity<?> cancelarPedido(@PathVariable(required = false) Integer id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pedidos/admin/{id}")
    public ResponseEntity<?> filtrarPedidos(
            @RequestParam(required = false) EstadoPedido estado,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta,
            @RequestParam(required = false) String email
    ) {

        FiltrosPedidoDto filtros = new FiltrosPedidoDto();
        filtros.setEstadoPedido(estado);
        filtros.setDesde(desde);
        filtros.setHasta(hasta);
        filtros.setEmail(email);

        return ResponseEntity.ok(pedidoService.filtrarPedidos(filtros));
    }
}
