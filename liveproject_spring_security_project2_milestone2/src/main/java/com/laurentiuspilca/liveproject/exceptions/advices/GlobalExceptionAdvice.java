package com.laurentiuspilca.liveproject.exceptions.advices;

import com.laurentiuspilca.liveproject.exceptions.HealthProfileAlreadyExistsException;
import com.laurentiuspilca.liveproject.exceptions.NonExistentHealthProfileException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

  @ExceptionHandler(HealthProfileAlreadyExistsException.class)
  public ResponseEntity<String> handleHealthProfileAlreadyExists(
          HealthProfileAlreadyExistsException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(NonExistentHealthProfileException.class)
  public ResponseEntity<String> handleNonExistentHealthProfile(
          NonExistentHealthProfileException e) {
    return ResponseEntity.notFound().build();
  }
}
