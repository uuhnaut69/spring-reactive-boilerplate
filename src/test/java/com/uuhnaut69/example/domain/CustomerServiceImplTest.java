package com.uuhnaut69.example.domain;

import com.uuhnaut69.example.domain.exception.CustomerNotFoundException;
import com.uuhnaut69.example.domain.exception.UserNameAlreadyExistsException;
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
class CustomerServiceImplTest {

  @Mock private CustomerRepository customerRepository;

  @InjectMocks private CustomerServiceImpl customerService;

  @Test
  void createCustomerSuccessfully() {
    when(customerRepository.existsByUsername(any(String.class)))
        .thenReturn(Mono.just(Boolean.FALSE));
    when(customerRepository.save(any(Customer.class)))
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
    when(customerRepository.existsByUsername(any(String.class)))
        .thenReturn(Mono.just(Boolean.TRUE));

    StepVerifier.create(
            customerService.createCustomer(new CustomerDTO("uuhnaut69", "FirstName", "LastName")))
        .expectErrorMatches(throwable -> throwable instanceof UserNameAlreadyExistsException)
        .verify();
  }

  @Test
  void updateCustomerSuccessfully() {
    var customerId = UUID.randomUUID();
    when(customerRepository.findById(any(UUID.class)))
        .thenReturn(Mono.just(new Customer(customerId, "uuhnaut69", "FirstName", "LastName")));
    when(customerRepository.save(any(Customer.class)))
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
    when(customerRepository.findById(customerId))
        .thenReturn(Mono.error(new CustomerNotFoundException(customerId)));

    StepVerifier.create(customerService.updateCustomer(customerId, any(CustomerDTO.class)))
        .expectErrorMatches(throwable -> throwable instanceof CustomerNotFoundException)
        .verify();
  }

  @Test
  void deleteCustomerByIdSuccessfully() {
    when(customerRepository.deleteById(any(UUID.class))).thenReturn(Mono.empty());
    StepVerifier.create(customerService.deleteCustomerById(UUID.randomUUID())).verifyComplete();
  }
}
