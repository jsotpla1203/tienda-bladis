package com.tienda.bladis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NoAutorizadoException extends ResponseStatusException {
    public NoAutorizadoException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
