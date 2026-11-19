package com.tienda.bladis.exceptions;

public class NoAutorizadoException extends RuntimeException {
  public NoAutorizadoException(String message) {
    super(message);
  }
}
