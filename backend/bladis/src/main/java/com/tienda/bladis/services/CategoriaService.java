package com.tienda.bladis.services;

import com.tienda.bladis.dtos.CategoriaCrearDto;
import com.tienda.bladis.dtos.CategoriaDto;
import com.tienda.bladis.exceptions.ConflictoException;
import com.tienda.bladis.exceptions.RecursoNoEncontradoException;
import com.tienda.bladis.models.Categoria;
import com.tienda.bladis.models.Videojuego;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();

    public CategoriaService() {
        categorias.add(new Categoria("RPG", "Juegos de rol con progreso de personaje y mundo abierto"));
        categorias.add(new Categoria("Shooter", "Juegos de combate y disparos"));
        categorias.add(new Categoria("Moba", "Juegos de Multijugador en Línea"));
        categorias.add(new Categoria("Terror", "Juegos de terror"));
        categorias.add(new Categoria("Aventura", "Juegos de exploración y aventuras con grandes historias"));
        categorias.add(new Categoria("CienciaFiccion", "Juegos futuristas y escenarios fantasticos"));
    }

    public List<CategoriaDto> listarTodas(){
        List<CategoriaDto> listaResultado = new ArrayList<>();

        for (Categoria cat : categorias){
            CategoriaDto dto = new CategoriaDto();
            dto.setId(cat.getId());
            dto.setNombre(cat.getNombre());
            dto.setDescripcion(cat.getDescripcion());

            listaResultado.add(dto);
        }
        return listaResultado;
    }

    public CategoriaDto obtenerPorId(Integer id){

        for (Categoria cat : categorias){
            if (cat.getId().equals(id)){
                return new CategoriaDto(cat.getId(), cat.getNombre(), cat.getDescripcion());
            }
        }

        throw new RecursoNoEncontradoException("No se ha encontrado el producto con el id: " + id);
    }

    public CategoriaDto crearCategoria(CategoriaCrearDto categoria){

        for (Categoria catExistente : categorias) {
            if (catExistente.getNombre().equalsIgnoreCase(categoria.getNombre())) {
                throw new ConflictoException("La categoría que intentas crear ya existe");
            }
        }

        Categoria nuevaCat = new Categoria(categoria.getNombre(), categoria.getDescripcion());
        categorias.add(nuevaCat);

        CategoriaDto respuesta = new CategoriaDto();
        respuesta.setId(nuevaCat.getId());
        respuesta.setNombre(nuevaCat.getNombre());
        respuesta.setDescripcion(nuevaCat.getDescripcion());

        return respuesta;
    }

    public CategoriaDto modificarCategoria(Integer id, CategoriaCrearDto categoriaCrearDto){
        Categoria categoriaMod = null;

        for (Categoria cat : categorias){
            if (cat.getId().equals(id)){
                categoriaMod = cat;
            }
        }

        if (categoriaMod == null){
            throw new RecursoNoEncontradoException("No se ha encontrado ninguna categoria con el id: " + id);
        }

        for (Categoria catExistente : categorias){
            if (catExistente.getNombre().equalsIgnoreCase(categoriaCrearDto.getNombre())
                    && !catExistente.getId().equals(id)) {
                throw new ConflictoException("Ya existe otra categoría con el nombre: " + categoriaCrearDto.getNombre());
            }
        }

        categoriaMod.setNombre(categoriaCrearDto.getNombre());
        categoriaMod.setDescripcion(categoriaCrearDto.getDescripcion());

        CategoriaDto respuesta = new CategoriaDto();
        respuesta.setId(categoriaMod.getId());
        respuesta.setNombre(categoriaMod.getNombre());
        respuesta.setDescripcion(categoriaMod.getDescripcion());

        return respuesta;
    }

    public void borrarCategoria(Integer id, List<Videojuego> videojuegos) {
        Categoria categoria = null;

        for (Categoria cat : categorias) {
            if (cat.getId().equals(id)) {
                categoria = cat;
            }
        }
        if (categoria == null) {
            throw new RecursoNoEncontradoException("No se ha encontrado la categoria con el id: " + id);
        }

        for (Videojuego videojuego : videojuegos) {
            if (videojuego.getCategoria().equals(categoria)) {
                throw new ConflictoException("Esta categoría tiene un producto");
            }
        }

        categorias.remove(categoria);
    }
}