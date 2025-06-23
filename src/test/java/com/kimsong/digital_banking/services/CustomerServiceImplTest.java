package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.dtos.customer.CustomerRequest;
import com.kimsong.digital_banking.exceptions.ValidationException;
import com.kimsong.digital_banking.generators.SequenceGenerator;
import com.kimsong.digital_banking.mappers.CustomerMapper;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.repositories.CustomerRepository;
import com.kimsong.digital_banking.services.implement.CustomerServiceImpl;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SequenceGenerator generator;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void createCustomer_whenValidRequest_thenResponseCustomer() {
        CustomerRequest request = new CustomerRequest();
        request.setFirstName("kimsong");
        request.setLastName("sem");
        request.setNationalId("123456");

        String generatedCIF = "000001";

        Customer mappedCustomer = new Customer();
        mappedCustomer.setFirstName(request.getFirstName());
        mappedCustomer.setLastName(request.getLastName());
        mappedCustomer.setNationalId(request.getNationalId());
        mappedCustomer.setCIF(generatedCIF);

        when(customerRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndNationalId(
                request.getFirstName(),
                request.getLastName(),
                request.getNationalId())).thenReturn(Optional.empty());
        when(generator.generateCifNumber()).thenReturn(generatedCIF);
        when(customerMapper.mapFromRequest(request, generatedCIF)).thenReturn(mappedCustomer);
        when(customerRepository.save(mappedCustomer)).thenReturn(mappedCustomer);

        Customer result = customerService.createCustomer(request);

        assertNotNull(result);
        assertEquals(generatedCIF, result.getCIF());
        verify(customerRepository).save(mappedCustomer);
    }

    @Test
    void createCustomer_whenCustomerAlreadyExists_thenThrowException() {
        CustomerRequest request = new CustomerRequest();
        request.setFirstName("kimsong");
        request.setLastName("sem");
        request.setNationalId("123456");

        Customer existsCustomer = new Customer();
        existsCustomer.setFirstName("kimsong");
        existsCustomer.setLastName("sem");
        existsCustomer.setNationalId("123456");
        existsCustomer.setCIF("000001");

        when(customerRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndNationalId(
                request.getFirstName(),
                request.getLastName(),
                request.getNationalId())).thenReturn(Optional.of(existsCustomer));

        ValidationException ex = assertThrows(ValidationException.class,
                () -> customerService.createCustomer(request));

        assertEquals(ErrorStatusEnum.CUSTOMER_ALREADY_EXISTS, ex.getStatusEnum());
        verify(customerRepository, never()).save(any());


    }


}
