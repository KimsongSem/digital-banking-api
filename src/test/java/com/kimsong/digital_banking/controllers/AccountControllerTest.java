package com.kimsong.digital_banking.controllers;

import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceRequest;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CustomerAccountResponse;
import com.kimsong.digital_banking.dtos.customer.CustomerRequest;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.services.AccountService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.constants.enums.EAccountType;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Test
    void createCustomerAccount_whenValidRequest_thenReturnResponse() {
        CreateCustomerAccountRequest request = new CreateCustomerAccountRequest();
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFirstName("Kimsong");
        customerRequest.setLastName("SEM");
        customerRequest.setPhone("0962192240");
        customerRequest.setGender("M");
        request.setCustomer(customerRequest);
        request.setAccountType(EAccountType.SAVINGS);
        request.setBalance(new BigDecimal("100.00"));
        request.setCurrency(ECurrency.USD);

        Customer customer = new Customer();
        customer.setCIF("000001");

        CustomerAccountResponse response = new CustomerAccountResponse();
        response.setAccountNumber("100000001");

        when(accountService.createAccount(request)).thenReturn(DataResponseDto.<CustomerAccountResponse>builder().data(response).build());

        ResponseEntity<DataResponseDto<CustomerAccountResponse>> result = accountController.createCustomerAccount(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("100000001", result.getBody().getData().getAccountNumber());
    }

    @Test
    void checkBalance_whenAccountExist_thenReturnResponse() {
        String accountNumber = "100000001";

        CheckAccountBalanceResponse response = new CheckAccountBalanceResponse();
        response.setAccountNumber(accountNumber);
        response.setBalance(new BigDecimal("100.00"));

        when(accountService.checkAccountBalance(accountNumber)).thenReturn(DataResponseDto.<CheckAccountBalanceResponse>builder().data(response).build());

        ResponseEntity<DataResponseDto<CheckAccountBalanceResponse>> result = accountController.balance(accountNumber);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(accountNumber, result.getBody().getData().getAccountNumber());
        assertEquals(new BigDecimal("100.00"), result.getBody().getData().getBalance());
    }

}
