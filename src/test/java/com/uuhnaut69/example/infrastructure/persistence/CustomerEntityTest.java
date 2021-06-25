package com.uuhnaut69.example.infrastructure.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityTest {

    @Test
    void isNew() {
      var customerEntity = new CustomerEntity();

      assertTrue(customerEntity.isNew());
    }
}