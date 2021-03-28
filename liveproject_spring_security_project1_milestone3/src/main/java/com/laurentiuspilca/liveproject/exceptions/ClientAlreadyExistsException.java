package com.laurentiuspilca.liveproject.exceptions;

public class ClientAlreadyExistsException extends RuntimeException {

  public ClientAlreadyExistsException(String message) {
    super(message);
  }
}
