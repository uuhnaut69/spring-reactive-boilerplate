package com.uuhnaut69.example.infrastructure;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CustomerR2dbcRepository extends R2dbcRepository<CustomerEntity, UUID> {

  Mono<Boolean> existsByUsername(String username);
}
