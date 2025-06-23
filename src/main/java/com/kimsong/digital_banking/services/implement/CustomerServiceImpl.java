package com.kimsong.digital_banking.services.implement;

import com.kimsong.digital_banking.dtos.customer.CustomerRequest;
import com.kimsong.digital_banking.exceptions.ValidationException;
import com.kimsong.digital_banking.generators.SequenceGenerator;
import com.kimsong.digital_banking.mappers.CustomerMapper;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.repositories.CustomerRepository;
import com.kimsong.digital_banking.services.CustomerService;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final SequenceGenerator generator;

    @Override
    public Customer createCustomer(CustomerRequest request) {
        Optional<Customer> existingCustomer = customerRepository
                .findByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndNationalId(
                        request.getFirstName().toLowerCase(),
                        request.getLastName().toLowerCase(),
                        request.getNationalId()
                );
        if (existingCustomer.isPresent()) {
            log.error("Customer already exists with CIF: {}", existingCustomer.get().getCIF());
            throw new ValidationException(ErrorStatusEnum.CUSTOMER_ALREADY_EXISTS);
        }

        Customer customer = customerMapper.mapFromRequest(request, generator.generateCifNumber());
        customerRepository.save(customer);
        log.info("Customer created with CIF:{}", customer.getCIF());

        return customer;
    }

}
