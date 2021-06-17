package com.uuhnaut69.example.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNameAlreadyExistsException extends RuntimeException {
  public UserNameAlreadyExistsException(String username) {
    super(String.format("Username %s already exists !!!", username));
  }
}
