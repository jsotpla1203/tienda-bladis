package com.tienda.bladis.dtos;

import com.tienda.bladis.enums.Rol;

public class UsuarioDto {
    private Integer id;
    private String uid;
    private String email;
    private String nombre;
    private String direccionEnvio;
    private Rol rol;
    private String fechaAlta;

    public UsuarioDto(Integer id, String uid, String email, String nombre, String direccionEnvio, Rol rol, String fechaAlta) {
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.nombre = nombre;
        this.direccionEnvio = direccionEnvio;
        this.rol = rol;
        this.fechaAlta = fechaAlta;
    }

    public Integer getId() { return id; }
    public String getUid() { return uid; }
    public String getEmail() { return email; }
    public String getNombre() { return nombre; }
    public String getDireccionEnvio() { return direccionEnvio; }
    public Rol getRol() { return rol; }
    public String getFechaAlta() { return fechaAlta; }
}