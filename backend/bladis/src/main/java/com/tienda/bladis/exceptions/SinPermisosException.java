package com.tienda.bladis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class SinPermisosException extends ResponseStatusException {
    public SinPermisosException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
