package com.tienda.bladis.services;

import com.tienda.bladis.dtos.CategoriaCrearDto;
import com.tienda.bladis.dtos.CategoriaDto;
import com.tienda.bladis.models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoriaService {

    @Autowired
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
        return null;
    }

    public CategoriaDto crearCategoria(CategoriaCrearDto categoria){
        if(categoria.getNombre().isEmpty()){
            return null;
        }

        Categoria nuevaCat = new Categoria(categoria.getNombre(), categoria.getDescripcion());
        categorias.add(nuevaCat);

        CategoriaDto respuesta = new CategoriaDto();
        respuesta.setId(nuevaCat.getId());
        respuesta.setNombre(nuevaCat.getNombre());
        respuesta.setDescripcion(nuevaCat.getDescripcion());

        return respuesta;
    }

}
