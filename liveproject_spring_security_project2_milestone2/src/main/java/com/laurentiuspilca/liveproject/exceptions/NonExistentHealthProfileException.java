package com.laurentiuspilca.liveproject.exceptions;

public class NonExistentHealthProfileException extends RuntimeException {

  public NonExistentHealthProfileException(String message) {
    super(message);
  }
}
