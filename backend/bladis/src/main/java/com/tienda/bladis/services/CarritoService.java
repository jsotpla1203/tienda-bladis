package com.tienda.bladis.services;

import com.tienda.bladis.dtos.*;
import com.tienda.bladis.enums.Plataforma;
import com.tienda.bladis.exceptions.RecursoNoEncontradoException;
import com.tienda.bladis.models.Carrito;
import com.tienda.bladis.models.LineaCarrito;
import com.tienda.bladis.models.Usuario;
import com.tienda.bladis.models.Videojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class CarritoService {

@Autowired
private UsuarioActualService usuarioActual;

@Autowired
private VideojuegoService videojuegoService;

Carrito carrito;

    public Carrito obtenerCarrito() {

        Usuario usuario = usuarioActual.getUsuarioActual();

        if (usuario == null) {
            throw new RuntimeException("El usuario no está autenticado");
        }

        if (carrito == null) {
            carrito = new Carrito(usuario, new ArrayList<>());
        }

        return carrito;
    }

    @Value("${tienda.envio.coste:}")
    private BigDecimal gastoEnvio;

    @Value("${tienda.carrito.max-unidades-por-producto:}")
    private Integer maximoUnidades;

    @Value("${tienda.envio.gratis-desde:}")
    private BigDecimal envioGratis;


    public CarritoDto verCarrito(){
        Carrito carrito = obtenerCarrito();
        BigDecimal subtotal = BigDecimal.ZERO;
        Integer numArticulos = 0;
        BigDecimal envio = gastoEnvio;
        BigDecimal faltaGratis = BigDecimal.ZERO;

    for (LineaCarrito linea : carrito.getLineas()){

        subtotal = subtotal.add(BigDecimal.valueOf(linea.getCantidad()).multiply(linea.getVideojuego().getPrecio()));
        numArticulos += linea.getCantidad();
    }

    if (subtotal.compareTo(envioGratis) >= 0){
        envio = BigDecimal.ZERO;
    }
    else {
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

public LineaCarritoDto anadirVideojuego(LineaCarritoCrearDto lineaCarritoCrearDto) {

        Carrito carrito = obtenerCarrito();
        LineaCarrito lineaCarrito = null;
        Videojuego videojuego;

    for (LineaCarrito linea : carrito.getLineas()) {

        if (linea.getVideojuego().getId() == lineaCarritoCrearDto.getVideojuegoId()) {
            lineaCarrito = linea;
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

public LineaCarritoDto modificarVideojuego(Integer id, LineaCarritoActualizarDto lineaCarritoActualizarDto){
    Carrito carrito = obtenerCarrito();
    Integer catidad = 0;
    String nombre = null;
    String url = null;
    BigDecimal precio = BigDecimal.ZERO;
    Plataforma plataforma = null;

    for (LineaCarrito linea : carrito.getLineas()) {

        if (linea.getVideojuego().getId() == id){
            catidad = lineaCarritoActualizarDto.getCantidad();
        }
    }

    return new LineaCarritoDto(
            nombre,
            url,
            precio,
            catidad,
            plataforma
    );
}

public void quitarLinea(Integer id){
    Carrito carrito = obtenerCarrito();
    LineaCarrito lineaBorrar = null;

    for (LineaCarrito linea : carrito.getLineas()) {

        if (linea.getVideojuego().getId() == id){
            lineaBorrar = linea;
        }
    }

    carrito.getLineas().remove(lineaBorrar);
}

public void vaciarCarrito(){
    Carrito carrito = obtenerCarrito();
    carrito.getLineas().clear();
}

public List<LineaCarritoDto> crearLineaDto(){
    Carrito carrito = obtenerCarrito();
    List<LineaCarritoDto> lineas = new ArrayList<>();

    for (LineaCarrito linea : carrito.getLineas()){
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

public List<String> crearAvisos(){
    Carrito carrito = obtenerCarrito();
    List<String> avisos = new ArrayList<>();

    for (LineaCarrito linea : carrito.getLineas()){
        if (linea.getVideojuego().getStock() <= 0){
            avisos.add("El videojuego: " + linea.getVideojuego().getNombre() + "  no tiene stock");
        }
        if (!linea.getVideojuego().getActivo()){
            avisos.add("El videojuego: " + linea.getVideojuego().getNombre() + "  ha sido retirado");
        }
    }

    return avisos;
}

}
