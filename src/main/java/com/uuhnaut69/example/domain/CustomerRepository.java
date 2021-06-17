package com.uuhnaut69.example.domain;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerRepository {

  Mono<Customer> findById(final UUID customerId);

  Mono<Boolean> existsByUsername(final String username);

  Mono<Customer> save(final Customer customer);

  Mono<Void> deleteById(final UUID customerId);
}
