package com.tienda.bladis.dtos;

import com.tienda.bladis.enums.Rol;

public class CambiarRolDto {
    private Rol rol;

    public CambiarRolDto() {
    }

    public CambiarRolDto(Rol rol) {
        this.rol = rol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
