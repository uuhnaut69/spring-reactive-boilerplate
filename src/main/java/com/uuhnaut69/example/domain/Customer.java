package com.uuhnaut69.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  private UUID id;

  private String username;

  private String firstName;

  private String lastName;
}
