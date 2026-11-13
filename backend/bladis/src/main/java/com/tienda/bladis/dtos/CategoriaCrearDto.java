package com.tienda.bladis.dtos;

public class CategoriaCrearDto {
    private String nombre;
    private String descripcion;

    public CategoriaCrearDto() {
    }

    public CategoriaCrearDto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
