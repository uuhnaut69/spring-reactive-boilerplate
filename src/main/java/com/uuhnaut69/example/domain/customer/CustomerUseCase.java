package com.uuhnaut69.example.domain.customer;

import com.uuhnaut69.example.domain.customer.entity.Customer;
import com.uuhnaut69.example.domain.customer.exception.CustomerNotFoundException;
import com.uuhnaut69.example.domain.customer.exception.UserNameAlreadyExistsException;
import com.uuhnaut69.example.domain.customer.port.CustomerRepositoryPort;
import com.uuhnaut69.example.domain.customer.port.CustomerUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerUseCase implements CustomerUseCasePort {

  private final CustomerRepositoryPort customerRepositoryPort;

  @Override
  public Mono<Customer> createCustomer(final CustomerDTO customerDTO) {
    return customerRepositoryPort
        .existsByUsername(customerDTO.getUsername())
        .flatMap(
            exists -> {
              if (exists.equals(Boolean.TRUE)) {
                return Mono.error(new UserNameAlreadyExistsException(customerDTO.getUsername()));
              }
              var customer = new Customer();
              customer.setId(UUID.randomUUID());
              toCustomer(customer, customerDTO);
              return customerRepositoryPort.save(customer);
            });
  }

  @Override
  public Mono<Customer> updateCustomer(final UUID customerId, final CustomerDTO customerDTO) {
    return customerRepositoryPort
        .findById(customerId)
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(customerId)))
        .map(customer -> toCustomer(customer, customerDTO))
        .flatMap(customerRepositoryPort::save);
  }

  @Override
  public Mono<Void> deleteCustomerById(final UUID customerId) {
    return customerRepositoryPort.deleteById(customerId);
  }

  private Customer toCustomer(Customer customer, CustomerDTO customerDTO) {
    customer.setUsername(customerDTO.getUsername());
    customer.setFirstName(customerDTO.getFirstName());
    customer.setLastName(customerDTO.getLastName());
    return customer;
  }
}
