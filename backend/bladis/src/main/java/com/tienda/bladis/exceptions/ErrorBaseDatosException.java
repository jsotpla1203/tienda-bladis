package com.tienda.bladis.exceptions;

import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ErrorBaseDatosException extends ResponseStatusException {
    public ErrorBaseDatosException(String message) {
        super(HttpStatusCode.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), message);
    }
}
