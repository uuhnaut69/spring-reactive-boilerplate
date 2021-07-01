package com.uuhnaut69.example.api;

import com.uuhnaut69.example.domain.customer.CustomerDTO;
import com.uuhnaut69.example.domain.customer.entity.Customer;
import com.uuhnaut69.example.domain.customer.port.CustomerUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/customers")
public class CustomerApi {

  private final CustomerUseCasePort customerUseCasePort;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Customer> createCustomer(final @RequestBody @Valid CustomerDTO customerDTO) {
    return customerUseCasePort.createCustomer(customerDTO);
  }

  @PutMapping("/{customerId}")
  public Mono<Customer> updateCustomer(
      final @PathVariable UUID customerId, final @RequestBody @Valid CustomerDTO customerDTO) {
    return customerUseCasePort.updateCustomer(customerId, customerDTO);
  }

  @DeleteMapping("/{customerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteCustomer(final @PathVariable UUID customerId) {
    return customerUseCasePort.deleteCustomerById(customerId);
  }
}
