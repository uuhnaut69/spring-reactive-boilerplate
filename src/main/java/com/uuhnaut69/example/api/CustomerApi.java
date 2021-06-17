package com.uuhnaut69.example.api;

import com.uuhnaut69.example.domain.Customer;
import com.uuhnaut69.example.domain.CustomerService;
import com.uuhnaut69.example.domain.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/customers")
public class CustomerApi {

  private final CustomerService customerService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Customer> createCustomer(final @RequestBody CustomerDTO customerDTO) {
    return customerService.createCustomer(customerDTO);
  }

  @PutMapping("/{customerId}")
  public Mono<Customer> updateCustomer(
      final @PathVariable UUID customerId, final @RequestBody CustomerDTO customerDTO) {
    return customerService.updateCustomer(customerId, customerDTO);
  }

  @DeleteMapping("/{customerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteCustomer(final @PathVariable UUID customerId) {
    return customerService.deleteCustomerById(customerId);
  }
}
