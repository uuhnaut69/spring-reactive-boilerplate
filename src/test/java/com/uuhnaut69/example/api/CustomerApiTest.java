package com.uuhnaut69.example.api;

import com.uuhnaut69.example.domain.customer.entity.Customer;
import com.uuhnaut69.example.domain.customer.CustomerDTO;
import com.uuhnaut69.example.domain.customer.port.CustomerUseCasePort;
import com.uuhnaut69.example.domain.customer.exception.CustomerNotFoundException;
import com.uuhnaut69.example.domain.customer.exception.UserNameAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebFluxTest
class CustomerApiTest {

  @MockBean private CustomerUseCasePort customerUseCasePort;

  @Autowired private WebTestClient webTestClient;

  @Test
  void createCustomerSuccessfully() {
    var customerDTO = new CustomerDTO("uuhnaut69", "FirstName", "LastName");
    when(customerUseCasePort.createCustomer(customerDTO))
        .thenReturn(
            Mono.just(new Customer(UUID.randomUUID(), "uuhnaut69", "FirstName", "LastName")));

    webTestClient
        .post()
        .uri(URI.create("/customers"))
        .body(Mono.just(customerDTO), CustomerDTO.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Customer.class);
  }

  @Test
  void createCustomerFailed() {
    var customerDTO = new CustomerDTO("uuhnaut69", "FirstName", "LastName");
    when(customerUseCasePort.createCustomer(customerDTO))
        .thenReturn(Mono.error(new UserNameAlreadyExistsException(customerDTO.getUsername())));

    webTestClient
        .post()
        .uri(URI.create("/customers"))
        .body(Mono.just(customerDTO), CustomerDTO.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  void updateCustomerSuccessfully() {
    var customerId = UUID.randomUUID();
    var customerDTO = new CustomerDTO("uuhnaut69", "FirstName", "LastName");
    when(customerUseCasePort.updateCustomer(customerId, customerDTO))
        .thenReturn(Mono.just(new Customer(customerId, "uuhnaut69", "FirstName", "LastName")));

    webTestClient
        .put()
        .uri(URI.create("/customers/" + customerId))
        .body(Mono.just(customerDTO), CustomerDTO.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Customer.class);
  }

  @Test
  void updateCustomerFailed() {
    var customerId = UUID.randomUUID();
    var customerDTO = new CustomerDTO("uuhnaut69", "FirstName", "LastName");
    when(customerUseCasePort.updateCustomer(customerId, customerDTO))
        .thenReturn(Mono.error(new CustomerNotFoundException(customerId)));

    webTestClient
        .put()
        .uri(URI.create("/customers/" + customerId))
        .body(Mono.just(customerDTO), CustomerDTO.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void deleteCustomer() {
    var customerId = UUID.randomUUID();
    when(customerUseCasePort.deleteCustomerById(customerId)).thenReturn(Mono.empty());

    webTestClient
        .delete()
        .uri(URI.create("/customers/" + customerId))
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNoContent();
  }
}
