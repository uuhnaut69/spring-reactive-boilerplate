package com.uuhnaut69.example.domain.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {
  public CustomerNotFoundException(UUID customerId) {
    super(String.format("Customer %s not found !!!", customerId));
  }
}
