package com.tienda.bladis.services;

import com.tienda.bladis.dtos.*;
import com.tienda.bladis.enums.Plataforma;
import com.tienda.bladis.exceptions.ConflictoException;
import com.tienda.bladis.exceptions.FormatoInvalidoException;
import com.tienda.bladis.exceptions.RecursoNoEncontradoException;
import com.tienda.bladis.exceptions.SinPermisosException;
import com.tienda.bladis.models.Carrito;
import com.tienda.bladis.models.LineaCarrito;
import com.tienda.bladis.models.Usuario;
import com.tienda.bladis.models.Videojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CarritoService {

    private final Map<String, Carrito> carritos = new ConcurrentHashMap<>();

    @Autowired
    private UsuarioActualService usuarioActual;

    @Autowired
    private VideojuegoService videojuegoService;

    public Carrito obtenerCarrito() {

        Usuario usuario = usuarioActual.getUsuarioActual();

        if (usuario == null) {
            throw new SinPermisosException("El usuario no está autenticado");
        }

        String uid = usuario.getUid();

        return carritos.computeIfAbsent(
                uid,
                key -> new Carrito(usuario, new ArrayList<>())
        );
    }

    @Value("${tienda.envio.coste:}")
    private BigDecimal gastoEnvio;

    @Value("${tienda.carrito.max-unidades-por-producto:}")
    private Integer maximoUnidades;

    @Value("${tienda.envio.gratis-desde:}")
    private BigDecimal envioGratis;


    public CarritoDto verCarrito() {

        Carrito carrito = obtenerCarrito();
        BigDecimal subtotal = BigDecimal.ZERO;
        Integer numArticulos = 0;
        BigDecimal envio = gastoEnvio;
        BigDecimal faltaGratis = BigDecimal.ZERO;

        for (LineaCarrito linea : carrito.getLineas()) {

            subtotal = subtotal.add(BigDecimal.valueOf(linea.getCantidad()).multiply(linea.getVideojuego().getPrecio()));
            numArticulos += linea.getCantidad();
        }

        if (subtotal.compareTo(envioGratis) >= 0) {
            envio = BigDecimal.ZERO;
        } else {
            faltaGratis = envioGratis.subtract(subtotal);
        }
        BigDecimal total = subtotal.add(envio);

        return new CarritoDto(
                numArticulos,
                subtotal,
                envio,
                total,
                faltaGratis,
                crearAvisos(),
                crearLineaDto()
        );
    }

    public LineaCarritoDto anadirLinea(LineaCarritoCrearDto lineaCarritoCrearDto) {

        if (lineaCarritoCrearDto.getCantidad() == null) {
            throw new FormatoInvalidoException("La cantidad no puede ser null");
        }

        if (lineaCarritoCrearDto.getCantidad() < 0) {
            throw new FormatoInvalidoException("La cantidad no puede ser negativa");
        }

        Carrito carrito = obtenerCarrito();
        LineaCarrito lineaCarrito = null;
        Videojuego videojuego;

        for (LineaCarrito linea : carrito.getLineas()) {

            if (linea.getVideojuego().getId() == lineaCarritoCrearDto.getVideojuegoId()) {

                lineaCarrito = linea;

                if (linea.getCantidad() + lineaCarritoCrearDto.getCantidad() > linea.getVideojuego().getStock()) {
                    throw new ConflictoException("No se pueden añadir mas productos que el stock");
                }

                if (linea.getCantidad() + lineaCarritoCrearDto.getCantidad() > maximoUnidades) {
                    throw new ConflictoException("No se puede superar el maximo de unidades");
                }

                lineaCarrito.setCantidad(lineaCarritoCrearDto.getCantidad() + lineaCarrito.getCantidad());

                return new LineaCarritoDto(
                        linea.getVideojuego().getNombre(),
                        linea.getVideojuego().getImagenUrl(),
                        linea.getVideojuego().getPrecio(),
                        linea.getCantidad(),
                        linea.getVideojuego().getPlataforma()
                );
            }
        }

        videojuego = videojuegoService.videojuegos.stream()
                .filter(v -> v.getId().equals(lineaCarritoCrearDto.getVideojuegoId()))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado el Videojuego"));

        if (!videojuego.getActivo()) {
            throw new ConflictoException("No se pueden añadir productos retirados");
        }

        if (lineaCarritoCrearDto.getCantidad() > videojuego.getStock()) {
            throw new ConflictoException("No se pueden añadir mas productos que el stock");
        }

        if (lineaCarritoCrearDto.getCantidad() > maximoUnidades) {
            throw new ConflictoException("No se puede superar el maximo de unidades");
        }

        carrito.getLineas().add(new LineaCarrito(
                videojuego,
                lineaCarritoCrearDto.getCantidad()));


        return new LineaCarritoDto(
                videojuego.getNombre(),
                videojuego.getImagenUrl(),
                videojuego.getPrecio(),
                lineaCarritoCrearDto.getCantidad(),
                videojuego.getPlataforma()
        );
    }

    public LineaCarritoDto modificarLinea(Integer id, LineaCarritoActualizarDto lineaCarritoActualizarDto) {

        if (lineaCarritoActualizarDto.getCantidad() == null) {
            throw new FormatoInvalidoException("La cantidad no puede ser null");
        }

        if (lineaCarritoActualizarDto.getCantidad() < 0) {
            throw new FormatoInvalidoException("La cantidad no puede ser negativa");
        }

        Carrito carrito = obtenerCarrito();

        LineaCarrito linea = carrito.getLineas().stream()
                .filter(v -> v.getVideojuego().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado el videojuego"));

        if (lineaCarritoActualizarDto.getCantidad() > linea.getVideojuego().getStock()) {
            throw new ConflictoException("No se pueden añadir mas productos que el stock");
        }

        if (lineaCarritoActualizarDto.getCantidad() > maximoUnidades) {
            throw new ConflictoException("No se puede superar el maximo de unidades");
        }

        linea.setCantidad(lineaCarritoActualizarDto.getCantidad());

        return new LineaCarritoDto(
                linea.getVideojuego().getNombre(),
                linea.getVideojuego().getImagenUrl(),
                linea.getVideojuego().getPrecio(),
                linea.getCantidad(),
                linea.getVideojuego().getPlataforma()
        );
    }

    public void quitarLinea(Integer id) {

        Carrito carrito = obtenerCarrito();

        carrito.getLineas().removeIf(
          l -> l.getVideojuego().getId().equals(id)
        );
    }

    public void vaciarCarrito() {
        Carrito carrito = obtenerCarrito();
        carrito.getLineas().clear();
    }

    public List<LineaCarritoDto> crearLineaDto() {
        Carrito carrito = obtenerCarrito();
        List<LineaCarritoDto> lineas = new ArrayList<>();

        for (LineaCarrito linea : carrito.getLineas()) {
            lineas.add(new LineaCarritoDto(
                    linea.getVideojuego().getNombre(),
                    linea.getVideojuego().getImagenUrl(),
                    linea.getVideojuego().getPrecio(),
                    linea.getCantidad(),
                    linea.getVideojuego().getPlataforma()
            ));
        }

        return lineas;
    }

    public List<String> crearAvisos() {

        Carrito carrito = obtenerCarrito();
        List<String> avisos = new ArrayList<>();

        for (LineaCarrito linea : carrito.getLineas()) {
            if (linea.getVideojuego().getStock() <= 0) {
                avisos.add("El videojuego: " + linea.getVideojuego().getNombre() + "  no tiene stock");
            }
            if (!linea.getVideojuego().getActivo()) {
                avisos.add("El videojuego: " + linea.getVideojuego().getNombre() + "  ha sido retirado");
            }
        }

        return avisos;
    }

}
