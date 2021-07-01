package com.uuhnaut69.example.infrastructure.persistence;

import com.uuhnaut69.example.domain.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryAdapterTest {

  @Mock private CustomerR2dbcRepository customerR2dbcRepository;

  @InjectMocks private CustomerRepositoryAdapter customerRepository;

  @Test
  void findById() {
    var customerId = UUID.randomUUID();
    var customerEntity = new CustomerEntity(customerId, "uuhnaut69", "FirstName", "LastName");
    when(customerR2dbcRepository.findById(customerId)).thenReturn(Mono.just(customerEntity));

    StepVerifier.create(customerRepository.findById(customerId))
        .expectNextMatches(
            result -> Objects.nonNull(result) && result.getUsername().equals("uuhnaut69"))
        .verifyComplete();
  }

  @Test
  void existsByUsername() {
    when(customerR2dbcRepository.existsByUsername("uuhnaut69"))
        .thenReturn(Mono.just(Boolean.FALSE));
    StepVerifier.create(customerRepository.existsByUsername("uuhnaut69"))
        .expectNextMatches(exists -> exists.equals(Boolean.FALSE))
        .verifyComplete();
  }

  @Test
  void save() {
    var customerId = UUID.randomUUID();
    var customer = new Customer(customerId, "uuhnaut69", "FirstName", "LastName");
    var customerEntity = new CustomerEntity(customerId, "uuhnaut69", "FirstName", "LastName");
    when(customerR2dbcRepository.save(customerEntity)).thenReturn(Mono.just(customerEntity));

    StepVerifier.create(customerRepository.save(customer))
        .expectNextMatches(
            result -> Objects.nonNull(result) && result.getUsername().equals("uuhnaut69"))
        .verifyComplete();
  }

  @Test
  void deleteById() {
    var customerId = UUID.randomUUID();
    when(customerR2dbcRepository.deleteById(customerId)).thenReturn(Mono.empty());
    StepVerifier.create(customerRepository.deleteById(customerId)).verifyComplete();
  }
}
