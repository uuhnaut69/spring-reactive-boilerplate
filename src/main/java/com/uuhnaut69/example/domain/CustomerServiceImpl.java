package com.uuhnaut69.example.domain;

import com.uuhnaut69.example.domain.exception.CustomerNotFoundException;
import com.uuhnaut69.example.domain.exception.UserNameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Override
  public Mono<Customer> createCustomer(final CustomerDTO customerDTO) {
    return customerRepository
        .existsByUsername(customerDTO.getUsername())
        .flatMap(
            exists -> {
              if (exists.equals(Boolean.TRUE)) {
                return Mono.error(new UserNameAlreadyExistsException(customerDTO.getUsername()));
              }
              var customer = new Customer();
              customer.setId(UUID.randomUUID());
              toCustomer(customer, customerDTO);
              return customerRepository.save(customer);
            });
  }

  @Override
  public Mono<Customer> updateCustomer(final UUID customerId, final CustomerDTO customerDTO) {
    return customerRepository
        .findById(customerId)
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(customerId)))
        .map(customer -> toCustomer(customer, customerDTO))
        .flatMap(customerRepository::save);
  }

  @Override
  public Mono<Void> deleteCustomerById(final UUID customerId) {
    return customerRepository.deleteById(customerId);
  }

  private Customer toCustomer(Customer customer, CustomerDTO customerDTO) {
    customer.setUsername(customerDTO.getUsername());
    customer.setFirstName(customerDTO.getFirstName());
    customer.setLastName(customerDTO.getLastName());
    return customer;
  }
}
