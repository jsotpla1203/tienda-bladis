package com.tienda.bladis.dtos;

public class UsuarioActualizarDto {
    private String nombre;
    private String direccionEnvio;

    public UsuarioActualizarDto() {
    }

    public UsuarioActualizarDto(String nombre, String direccionEnvio) {
        this.nombre = nombre;
        this.direccionEnvio = direccionEnvio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }
}
