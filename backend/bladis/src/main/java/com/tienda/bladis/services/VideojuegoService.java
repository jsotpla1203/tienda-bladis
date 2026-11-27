package com.tienda.bladis.services;

import com.tienda.bladis.dtos.*;
import com.tienda.bladis.enums.Direccion;
import com.tienda.bladis.enums.Orden;
import com.tienda.bladis.enums.Plataforma;
import com.tienda.bladis.exceptions.ConflictoException;
import com.tienda.bladis.exceptions.FormatoInvalidoException;
import com.tienda.bladis.exceptions.RecursoNoEncontradoException;
import com.tienda.bladis.models.Categoria;
import com.tienda.bladis.models.Videojuego;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class VideojuegoService {

    @Autowired
    public CategoriaService categoriaService;

    public List<Videojuego> videojuegos = new ArrayList<>();

    @PostConstruct
    public void init() {
        videojuegos.addAll(List.of(
                new Videojuego(
                        "VG-ER-PS5-001",
                        "Elden Ring",
                        "Un RPG de acción y fantasía oscura ambientado en las Tierras Intermedias, creado por Hidetaka Miyazaki y George R. R. Martin.",
                        BigDecimal.valueOf(59.99),
                         42,
                        "https://ejemplo.com/imagenes/elden-ring.jpg",
                        true,
                        true,
                        LocalDate.parse("2022-02-25"),
                        categoriaService.categorias.get(0),
                        Plataforma.PS5,
                        "FromSoftware"
                ),
                new Videojuego(
                        "VG-TOTK-NSW-002",
                        "The Legend of Zelda: Tears of the Kingdom",
                        "Secuela de Breath of the Wild donde Link explora tanto la superficie de Hyrule misteriosas islas celestiales.",
                        BigDecimal.valueOf(9.99),
                        28,
                        "https://ejemplo.com/imagenes/zelda-totk.jpg",
                        true,
                        true,
                        LocalDate.parse("2023-05-12"),
                        categoriaService.categorias.get(4),
                        Plataforma.SWITCH,
                        "Nintendo EPD"
                ),
                new Videojuego(
                        "VG-BG3-PC-003",
                        "Baldur's Gate 3",
                        "Juego de rol narrativo de nueva generación ambientado en el universo de Dragons con combate táctico por turnos.",
                        BigDecimal.valueOf(59.99),
                        100,
                        "https://ejemplo.com/imagenes/baldurs-gate-3.jpg",
                        true,
                        true,
                        LocalDate.parse("2023-08-03"),
                        categoriaService.categorias.get(0),
                        Plataforma.PC,
                        "Larian Studios"
                ),
                new Videojuego(
                        "VG-CP2077-XBX-004",
                        "Cyberpunk 2077",
                        "RPG de acción en mundo abierto ambientado en Night City, una megalópolis n el poder, el glamour y la modificación corporal.",
                        BigDecimal.valueOf(39.95),
                        15,
                        "https://ejemplo.com/imagenes/cyberpunk-2077.jpg",
                        true,
                        false,
                        LocalDate.parse("2020-12-10"),
                        categoriaService.categorias.get(5),
                        Plataforma.XBOX_SERIES_X,
                        "CD Projekt Red"
                ),
                new Videojuego(
                        "VG-HADES-NSW-005",
                        "Hades",
                        "Roguelike de exploración de mazmorras en el que desafías al dios de los muertos m abres paso fuera del Inframundo.",
                        BigDecimal.valueOf(24.99),
                        0,
                        "https://ejemplo.com/imagenes/hades.jpg",
                        false,
                        false,
                        LocalDate.parse("2020-09-17"),
                        categoriaService.categorias.get(0),
                        Plataforma.PC,
                        "Supergiant Games"
                )
        ));
    }


    public VideojuegoDetalleDto devolverVideojuegoActivo(Integer id) {

        for(Videojuego videojuego1:videojuegos) {
            if(videojuego1.getId().equals(id) && videojuego1.getActivo()) {
                return new VideojuegoDetalleDto(
                        videojuego1.getId(),
                        videojuego1.getSku(),
                        videojuego1.getNombre(),
                        videojuego1.getDescripcion(),
                        videojuego1.getPrecio(),
                        videojuego1.getStock(),
                        videojuego1.getImagenUrl(),
                        videojuego1.getActivo(),
                        videojuego1.getDestacado(),
                        videojuego1.getCategoria(),
                        videojuego1.getPlataforma(),
                        videojuego1.getDesarrolladora()
                );
            }
        }

        throw new RecursoNoEncontradoException("No se ha encontrado el videojuego con ID " + id);
    }

    public List<VideojuegoResumenDto> devolverVideojuegosActivos() {

        List<VideojuegoResumenDto> listaActivos = new ArrayList<>();

        for(Videojuego videojuego1:videojuegos) {
            if(videojuego1.getActivo()) {
                listaActivos.add(new VideojuegoResumenDto(
                    videojuego1.getId(),
                    videojuego1.getSku(),
                    videojuego1.getNombre(),
                    videojuego1.getPrecio(),
                    videojuego1.getImagenUrl(),
                    videojuego1.getDestacado(),
                    videojuego1.getCategoria(),
                    videojuego1.getPlataforma(),
                    videojuego1.getDesarrolladora()
                ));
            }
        }

        return listaActivos;
    }

    public List<VideojuegoResumenDto> devolverVideojuegos() {

        List<VideojuegoResumenDto> listaVideojuegos = new ArrayList<>();

        for(Videojuego videojuego1:videojuegos) {
            listaVideojuegos.add(new VideojuegoResumenDto(
                videojuego1.getId(),
                videojuego1.getSku(),

                videojuego1.getNombre(),
                videojuego1.getPrecio(),
                videojuego1.getImagenUrl(),
                videojuego1.getDestacado(),
                videojuego1.getCategoria(),
                videojuego1.getPlataforma(),
                videojuego1.getDesarrolladora()
            ));
        }

        return listaVideojuegos;
    }

    public VideojuegoDetalleDto crearVideojuego(VideojuegoCrearDto videojuego) {

        validarNulls(videojuego);
        validarReglas(videojuego);
        validarSku(videojuego.getSku());

        Categoria categoria = buscarCategoria(videojuego.getCategoria());

        Videojuego videojuegoNuevo = new Videojuego(
                videojuego.getSku(),
                videojuego.getNombre(),
                videojuego.getDescripcion(),
                videojuego.getPrecio(),
                videojuego.getStock(),
                videojuego.getImagenUrl(),
                true,
                videojuego.getDestacado(),
                LocalDate.now(),
                categoria,
                videojuego.getPlataforma(),
                videojuego.getDesarrolladora()
        );

        videojuegos.add(videojuegoNuevo);

        return crearVideojuegoDetalleDto(videojuegoNuevo);
    }

    public VideojuegoDetalleDto actualizarVideojuego(Integer id, VideojuegoCrearDto videojuego) {

        validarNulls(videojuego);
        validarReglas(videojuego);
        validarSku(videojuego.getSku());

        Categoria categoria = buscarCategoria(videojuego.getCategoria());

        Videojuego videojuegoActualizar = null;

        for(Videojuego videojuegoBuscado : videojuegos) {
            if(videojuegoBuscado.getId().equals(id)) {
                videojuegoActualizar = videojuegoBuscado;
            }
        }

        if(videojuegoActualizar == null) {
            throw new RecursoNoEncontradoException("No se ha encontrado el Videojuego con ID " + id);
        }

        videojuegoActualizar.setSku(videojuego.getSku());
        videojuegoActualizar.setNombre(videojuego.getNombre());
        videojuegoActualizar.setDescripcion(videojuego.getDescripcion());
        videojuegoActualizar.setPrecio(videojuego.getPrecio());
        videojuegoActualizar.setStock(videojuego.getStock());
        videojuegoActualizar.setImagenUrl(videojuego.getImagenUrl());
        videojuegoActualizar.setDestacado(videojuego.getDestacado());
        videojuegoActualizar.setCategoria(categoria);
        videojuegoActualizar.setPlataforma(videojuego.getPlataforma());
        videojuegoActualizar.setDesarrolladora(videojuego.getDesarrolladora());

        return crearVideojuegoDetalleDto(videojuegoActualizar);
    }

    public void borrarVideojuego(Integer id) {

        Videojuego videojuegoBorrar = null;

        for(Videojuego videojuegoBuscado : videojuegos) {
            if(videojuegoBuscado.getId().equals(id)) {
                videojuegoBorrar = videojuegoBuscado;
            }
        }

        if(videojuegoBorrar == null) {
            throw new RecursoNoEncontradoException("No se ha encontrado el Videojuego con ID " + id);
        }

        videojuegoBorrar.setActivo(false);
    }

    public VideojuegoDetalleDto actualizarStock(Integer id, StockDto stockDto) {

        Videojuego videojuegoActualizar = null;

        for(Videojuego videojuegoBuscado : videojuegos) {
            if(videojuegoBuscado.getId().equals(id)) {
                videojuegoActualizar = videojuegoBuscado;
            }
        }

        if(videojuegoActualizar == null) {
            throw new RecursoNoEncontradoException("No se ha encontrado el Videojuego con ID " + id);
        }

        if(!videojuegoActualizar.getActivo()) {
            throw new ConflictoException("No se puede actualizar el stock de un producto que no esta activado");
        }

        if(videojuegoActualizar.getStock() + stockDto.getCantidad() < 0) {
            throw new ConflictoException("El stock no se puede quedar en negativo");
        }

        videojuegoActualizar.setStock(videojuegoActualizar.getStock() + stockDto.getCantidad());

        return crearVideojuegoDetalleDto(videojuegoActualizar);
    }

    public VideojuegoDetalleDto reactivarVideojuego(Integer id) {

        Videojuego videojuegoReactivar = null;

        for(Videojuego videojuegoBuscado : videojuegos) {
            if(videojuegoBuscado.getId().equals(id)) {
                videojuegoReactivar = videojuegoBuscado;
            }
        }

        if(videojuegoReactivar == null) {
            throw new RecursoNoEncontradoException("No se ha encontrado el Videojuego con ID " + id);
        }

        videojuegoReactivar.setActivo(true);

        return crearVideojuegoDetalleDto(videojuegoReactivar);
    }

    public VideojuegoDetalleDto crearVideojuegoDetalleDto(Videojuego videojuego) {

        return new VideojuegoDetalleDto(
                videojuego.getId(),
                videojuego.getSku(),
                videojuego.getNombre(),
                videojuego.getDescripcion(),
                videojuego.getPrecio(),
                videojuego.getStock(),
                videojuego.getImagenUrl(),
                videojuego.getActivo(),
                videojuego.getDestacado(),
                videojuego.getCategoria(),
                videojuego.getPlataforma(),
                videojuego.getDesarrolladora()
        );
    }

    public List<VideojuegoResumenDto> crearListaVideojuegoResumenDto(List<Videojuego> videojuegos) {

        List<VideojuegoResumenDto> videojuegoResumenDtoList = new ArrayList<>();

        for(Videojuego videojuego:videojuegos) {

            videojuegoResumenDtoList.add(new VideojuegoResumenDto(
                    videojuego.getId(),
                    videojuego.getSku(),
                    videojuego.getNombre(),
                    videojuego.getPrecio(),
                    videojuego.getImagenUrl(),
                    videojuego.getDestacado(),
                    videojuego.getCategoria(),
                    videojuego.getPlataforma(),
                    videojuego.getDesarrolladora()
            ));
        }

        return videojuegoResumenDtoList;
    }

    public boolean estaVacio(Object valor) {
        return valor == null;
    }

    public void validarNulls(VideojuegoCrearDto videojuego) {
        List<String> errores = new ArrayList<>();

        if(estaVacio(videojuego.getSku())) {
            errores.add("El SKU no puede estar vacio");
        }
        if(estaVacio(videojuego.getPrecio())) {
            errores.add("El precio no puede estar vacio");
        }
        if(estaVacio(videojuego.getStock())) {
            errores.add("El stock no puede estar vacio");
        }
        if(estaVacio(videojuego.getDestacado())) {
            errores.add("El valor destacado no puede estar vacio");
        }
        if(estaVacio(videojuego.getNombre())) {
            errores.add("El nombre no puede estar vacio");
        }
        if(estaVacio(videojuego.getDescripcion())) {
            errores.add("El SKU no puede estar vacio");
        }
        if(estaVacio(videojuego.getImagenUrl())) {
            errores.add("La URL de la imagen no puede estar vacia");
        }
        if(estaVacio(videojuego.getCategoria())) {
            errores.add("La categoria no puede estar vacia");
        }
        if(estaVacio(videojuego.getDesarrolladora())) {
            errores.add("La desarrolladora no puede estar vacia");
        }
        if(estaVacio(videojuego.getPlataforma())) {
            errores.add("La plataforma no puede estar vacia");
        }
        if(!errores.isEmpty()) {
            throw new FormatoInvalidoException(String.join("; ", errores));
        }
    }

    public void validarReglas(VideojuegoCrearDto videojuego) {
        List<String> errores = new ArrayList<>();

        if(videojuego.getPrecio().compareTo(new BigDecimal(0)) == -1) {
            errores.add("El precio no puede ser negativo");
        }

        if(videojuego.getDescripcion().trim().length() < 20) {
            errores.add("La descripcion tiene que tener un minimo de 20 caracteres");
        }

        if(videojuego.getNombre().trim().length() < 3 || videojuego.getNombre().trim().length() > 25) {
            errores.add("El nombre tiene que tener entre 3 y 16 cacacteres");
        }

        if(videojuego.getSku().trim().length() < 6 || videojuego.getSku().trim().length() > 16) {
            errores.add("El SKU tiene que tener entre 6 y 16 cacacteres");
        }

        if(videojuego.getDesarrolladora().trim().length() < 3 || videojuego.getDesarrolladora().trim().length() > 30) {
            errores.add("La desarrolladora tiene que tener entre 3 y 30 cacacteres");
        }

        if(videojuego.getStock() < 0) {
            errores.add("El stock no puede estar negativo");
        }

        if(!errores.isEmpty()) {
            throw new FormatoInvalidoException(String.join("; ", errores));
        }
    }

    public void validarSku(String skuVideojuego) {

        for(Videojuego videojuego:videojuegos) {
            if(videojuego.getSku().equalsIgnoreCase(skuVideojuego.trim())) {
                throw new ConflictoException("El SKU " + skuVideojuego + " ya existe");
            }
        }
    }

    public Categoria buscarCategoria(String categoriaVideojuego) {

        Categoria categoriaBuscada = null;

        for(Categoria categoria: categoriaService.categorias) {
            if(categoria.getNombre().equalsIgnoreCase(categoriaVideojuego.trim())) {
                categoriaBuscada = categoria;
            }
        }

        if(categoriaBuscada == null) {
            throw new RecursoNoEncontradoException("La categoria " + categoriaVideojuego + " no existe");
        }

        return categoriaBuscada;
    }

    public List<VideojuegoResumenDto> filtrarCategoria(VideojuegoFiltroDto filtros) {

        List<Videojuego> videojuegosList = videojuegos.stream().
                filter(v -> filtros.getCategoriaId() == null ||
                        v.getCategoria().getId().equals(filtros.getCategoriaId()))
                .toList();

        return crearListaVideojuegoResumenDto(videojuegosList);
    }

    public List<VideojuegoResumenDto> filtrarNombreDescripcion(VideojuegoFiltroDto filtros) {

        List<Videojuego> videojuegosList = videojuegos.stream().
                filter(v -> filtros.getQ() == null ||
                        v.getNombre().toLowerCase().contains(filtros.getQ().trim().toLowerCase()) ||
                        v.getDescripcion().toLowerCase().contains(filtros.getQ().trim().toLowerCase()))
                .toList();

        return crearListaVideojuegoResumenDto(videojuegosList);
    }

    public List<VideojuegoResumenDto> filtrarPrecio(VideojuegoFiltroDto filtros) {

        if(filtros.getPrecioMin() != null && filtros.getPrecioMax() != null && filtros.getPrecioMax().compareTo(filtros.getPrecioMin()) < 0) {
            throw new FormatoInvalidoException("El precio maximo no puede ser menor al minimo");
        }

        List<Videojuego> videojuegosList = videojuegos.stream()
                .filter(v -> filtros.getPrecioMin() == null ||
                        v.getPrecio().compareTo(filtros.getPrecioMin()) >= 0)
                .filter(v -> filtros.getPrecioMax() == null ||
                        v.getPrecio().compareTo(filtros.getPrecioMax()) <= 0)
                .toList();

        return crearListaVideojuegoResumenDto(videojuegosList);
    }

    public List<VideojuegoResumenDto> filtrarDisponibles(VideojuegoFiltroDto filtros) {

        List<Videojuego> videojuegosList = new ArrayList<>();

        if(filtros.getSoloDisponibles() == null) {

            return crearListaVideojuegoResumenDto(videojuegos);

        } else if(filtros.getSoloDisponibles()) {

            videojuegosList = videojuegos.stream().
                    filter(v -> v.getActivo() && v.getStock() > 0)
                    .toList();
        } else {

            videojuegosList = videojuegos.stream().
                    filter(v -> !v.getActivo() || v.getStock() <= 0)
                    .toList();
        }

        return crearListaVideojuegoResumenDto(videojuegosList);
    }

    public List<VideojuegoResumenDto> filtrarOrdenDireccion(VideojuegoFiltroDto filtros) {

        List<Videojuego> videojuegoList;

        Comparator<Videojuego> comparator = null;

        if(Orden.NOMBRE.equals(filtros.getOrden())) {

            comparator = Comparator.comparing(Videojuego::getNombre);

        } else if(Orden.NOVEDAD.equals(filtros.getOrden())) {

            comparator = Comparator.comparing(Videojuego::getFechaAlta);

        } else if(Orden.PRECIO.equals(filtros.getOrden())) {

            comparator = Comparator.comparing(Videojuego::getPrecio);

        }

        if(comparator != null) {

            if(Direccion.DESC.equals(filtros.getDireccion())) {
                comparator = comparator.reversed();
            }

            videojuegoList = videojuegos.stream()
                    .sorted(comparator)
                    .toList();
        } else {

            videojuegoList = videojuegos.stream()
                    .toList();
        }

        return crearListaVideojuegoResumenDto(videojuegoList);
    }

    public List<VideojuegoResumenDto> filtrarPlataforma(VideojuegoFiltroDto filtros) {

        List<Videojuego> videojuegosList = videojuegos.stream().
                filter(v -> filtros.getPlataforma() == null ||
                        filtros.getPlataforma().equals(v.getPlataforma()))
                .toList();

        return crearListaVideojuegoResumenDto(videojuegosList);
    }

    public VideojuegoDetalleDto filtrarSku(VideojuegoFiltroDto filtros) {

        Videojuego videojuegoSku;

        if(filtros.getSku() != null) {

            for(Videojuego videojuego:videojuegos) {

                if(filtros.getSku().equals(videojuego.getSku())) {
                    return crearVideojuegoDetalleDto(videojuego);
                }
            }
        }

        throw new RecursoNoEncontradoException("No se ha encontrado el videojuego");
    }

    public List<VideojuegoResumenDto> filtrarStock(VideojuegoFiltroDto filtros) {

        List<Videojuego> videojuegosList = videojuegos.stream().
                filter(v -> filtros.getUmbral() == null ||
                        v.getStock() <= filtros.getUmbral())
                .toList();

        return crearListaVideojuegoResumenDto(videojuegosList);
    }
}