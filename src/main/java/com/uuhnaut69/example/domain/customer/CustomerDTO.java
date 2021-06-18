package com.uuhnaut69.example.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  @NotBlank private String username;

  @NotBlank private String firstName;

  @NotBlank private String lastName;
}
