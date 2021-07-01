package com.uuhnaut69.example.domain.customer;

import com.uuhnaut69.example.domain.customer.exception.CustomerNotFoundException;
import com.uuhnaut69.example.domain.customer.entity.Customer;
import com.uuhnaut69.example.domain.customer.exception.UserNameAlreadyExistsException;
import com.uuhnaut69.example.domain.customer.port.CustomerRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerUseCaseTest {

  @Mock private CustomerRepositoryPort customerRepositoryPort;

  @InjectMocks private CustomerUseCase customerService;

  @Test
  void createCustomerSuccessfully() {
    when(customerRepositoryPort.existsByUsername(any(String.class)))
        .thenReturn(Mono.just(Boolean.FALSE));
    when(customerRepositoryPort.save(any(Customer.class)))
        .thenReturn(
            Mono.just(new Customer(UUID.randomUUID(), "uuhnaut69", "FirstName", "LastName")));

    StepVerifier.create(
            customerService.createCustomer(new CustomerDTO("uuhnaut69", "FirstName", "LastName")))
        .expectNextMatches(
            customer -> Objects.nonNull(customer) && customer.getUsername().equals("uuhnaut69"))
        .verifyComplete();
  }

  @Test
  void createCustomerFailed() {
    when(customerRepositoryPort.existsByUsername(any(String.class)))
        .thenReturn(Mono.just(Boolean.TRUE));

    StepVerifier.create(
            customerService.createCustomer(new CustomerDTO("uuhnaut69", "FirstName", "LastName")))
        .expectErrorMatches(throwable -> throwable instanceof UserNameAlreadyExistsException)
        .verify();
  }

  @Test
  void updateCustomerSuccessfully() {
    var customerId = UUID.randomUUID();
    when(customerRepositoryPort.findById(any(UUID.class)))
        .thenReturn(Mono.just(new Customer(customerId, "uuhnaut69", "FirstName", "LastName")));
    when(customerRepositoryPort.save(any(Customer.class)))
        .thenReturn(Mono.just(new Customer(customerId, "uuhnaut696", "FirstName", "LastName")));

    StepVerifier.create(
            customerService.updateCustomer(
                customerId, new CustomerDTO("uuhnaut696", "FirstName", "LastName")))
        .expectNextMatches(
            customer -> Objects.nonNull(customer) && customer.getUsername().equals("uuhnaut696"))
        .verifyComplete();
  }

  @Test
  void updateCustomerFailed() {
    var customerId = UUID.randomUUID();
    when(customerRepositoryPort.findById(customerId))
        .thenReturn(Mono.error(new CustomerNotFoundException(customerId)));

    StepVerifier.create(customerService.updateCustomer(customerId, any(CustomerDTO.class)))
        .expectErrorMatches(throwable -> throwable instanceof CustomerNotFoundException)
        .verify();
  }

  @Test
  void deleteCustomerByIdSuccessfully() {
    when(customerRepositoryPort.deleteById(any(UUID.class))).thenReturn(Mono.empty());
    StepVerifier.create(customerService.deleteCustomerById(UUID.randomUUID())).verifyComplete();
  }
}
