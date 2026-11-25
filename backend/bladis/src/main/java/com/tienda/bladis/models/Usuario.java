package com.tienda.bladis.models;

import com.tienda.bladis.enums.Rol;

public class Usuario {

    private Integer id;
    private String uid;
    private String email;
    private String nombre;
    private String direccionEnvio;
    private Rol rol;
    private String fechaAlta;

    public Usuario() {
    }

    public Usuario(Integer id, String uid, String email, String nombre, String direccionEnvio, Rol rol, String fechaAlta) {
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.nombre = nombre;
        this.direccionEnvio = direccionEnvio;
        this.rol = rol;
        this.fechaAlta = fechaAlta;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }
}