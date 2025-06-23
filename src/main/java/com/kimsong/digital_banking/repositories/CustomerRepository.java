package com.kimsong.digital_banking.repositories;

import com.kimsong.digital_banking.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndNationalId(String firstName,String lastName,String nationalId);
}