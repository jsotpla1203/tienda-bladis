package com.tienda.bladis.services;

import com.tienda.bladis.dtos.VideojuegoCrearDto;
import com.tienda.bladis.dtos.VideojuegoDetalleDTo;
import com.tienda.bladis.dtos.VideojuegoResumenDto;
import com.tienda.bladis.enums.Plataforma;
import com.tienda.bladis.models.Categoria;
import com.tienda.bladis.models.Videojuego;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideojuegoService {

    public List<Videojuego> videojuegos = new ArrayList<>(List.of(
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
                    new Categoria(),
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
                    new Categoria(),
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
                    new Categoria(),
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
                    new Categoria(),
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
                    new Categoria(),
                    Plataforma.PC,
                    "Supergiant Games"
            )
    ));

    public VideojuegoDetalleDTo devolverVideojuegoActivo(Integer id) {

        for(Videojuego videojuego1:videojuegos) {
            if(videojuego1.getId().equals(id) && videojuego1.getActivo()) {
                return new VideojuegoDetalleDTo(
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

        return null;
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

    public VideojuegoDetalleDTo crearVideojuego(VideojuegoCrearDto videojuego) {

        return null;
    }
}
