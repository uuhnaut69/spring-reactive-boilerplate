package com.uuhnaut69.example.domain;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerService {

  Mono<Customer> createCustomer(final CustomerDTO customerDTO);

  Mono<Customer> updateCustomer(final UUID customerId, final CustomerDTO customerDTO);

  Mono<Void> deleteCustomerById(final UUID customerId);
}
