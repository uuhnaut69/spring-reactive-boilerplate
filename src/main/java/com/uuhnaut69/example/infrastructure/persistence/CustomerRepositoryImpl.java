package com.uuhnaut69.example.infrastructure.persistence;

import com.uuhnaut69.example.domain.customer.entity.Customer;
import com.uuhnaut69.example.domain.customer.ports.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Transactional
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

  private final CustomerR2dbcRepository customerR2dbcRepository;

  @Override
  @Transactional(readOnly = true)
  public Mono<Customer> findById(UUID customerId) {
    return customerR2dbcRepository.findById(customerId).map(this::toCustomer);
  }

  @Override
  @Transactional(readOnly = true)
  public Mono<Boolean> existsByUsername(final String username) {
    return customerR2dbcRepository.existsByUsername(username);
  }

  @Override
  public Mono<Customer> save(final Customer customer) {
    var entity = toCustomerEntity(customer);
    return customerR2dbcRepository.save(entity).log().map(this::toCustomer);
  }

  @Override
  public Mono<Void> deleteById(UUID customerId) {
    return customerR2dbcRepository.deleteById(customerId);
  }

  private Customer toCustomer(CustomerEntity entity) {
    return new Customer(
        entity.getId(), entity.getUsername(), entity.getFirstName(), entity.getLastName());
  }

  private CustomerEntity toCustomerEntity(Customer customer) {
    return new CustomerEntity(
        customer.getId(), customer.getUsername(), customer.getFirstName(), customer.getLastName());
  }
}
