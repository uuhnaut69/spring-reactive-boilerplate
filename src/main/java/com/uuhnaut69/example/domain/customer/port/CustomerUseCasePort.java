package com.uuhnaut69.example.domain.customer.port;

import com.uuhnaut69.example.domain.customer.CustomerDTO;
import com.uuhnaut69.example.domain.customer.entity.Customer;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerUseCasePort {

  Mono<Customer> createCustomer(final CustomerDTO customerDTO);

  Mono<Customer> updateCustomer(final UUID customerId, final CustomerDTO customerDTO);

  Mono<Void> deleteCustomerById(final UUID customerId);
}
