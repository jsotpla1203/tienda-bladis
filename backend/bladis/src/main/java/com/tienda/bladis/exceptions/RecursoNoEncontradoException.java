package com.tienda.bladis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RecursoNoEncontradoException extends ResponseStatusException {
    public RecursoNoEncontradoException(String mensaje){
        super(HttpStatus.NOT_FOUND, mensaje);
    }
}
