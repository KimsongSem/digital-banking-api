package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceRequest;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CustomerAccountResponse;
import com.kimsong.digital_banking.dtos.customer.CustomerRequest;
import com.kimsong.digital_banking.exceptions.ResourceNotFoundException;
import com.kimsong.digital_banking.generators.SequenceGenerator;
import com.kimsong.digital_banking.mappers.AccountMapper;
import com.kimsong.digital_banking.mappers.CustomerMapper;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.repositories.AccountRepository;
import com.kimsong.digital_banking.repositories.CustomerRepository;
import com.kimsong.digital_banking.services.implement.AccountServiceImpl;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.constants.enums.EAccountType;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private SequenceGenerator generator;
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount_whenRequestValid_thenCreateCustomerAndAccount() {
        CreateCustomerAccountRequest request = new CreateCustomerAccountRequest();
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFirstName("Kimsong");
        customerRequest.setLastName("SEM");
        customerRequest.setNationalId("123456");
        request.setCustomer(customerRequest);

        Customer customer = new Customer();

        String generatedAccountNumber = "100000001";
        Account account = new Account();
        account.setAccountNumber(generatedAccountNumber);
        account.setCustomer(customer);

        CustomerAccountResponse response = new CustomerAccountResponse();
        response.setAccountNumber(generatedAccountNumber);

        when(customerService.createCustomer(customerRequest)).thenReturn(customer);
        when(generator.generateAccountNumber()).thenReturn(generatedAccountNumber);
        when(accountMapper.mapFromRequest(request, generatedAccountNumber)).thenReturn(account);
        when(accountMapper.mapFromEntity(account)).thenReturn(response);
        when(accountRepository.save(account)).thenReturn(account);

        DataResponseDto<CustomerAccountResponse> result = accountService.createAccount(request);

        verify(accountRepository).save(account);
        assertNotNull(result.getData());
        assertEquals(generatedAccountNumber, result.getData().getAccountNumber());

    }

    @Test
    void checkAccountBalance_whenAccountExist_thenReturnBalanceResponse() {
        CheckAccountBalanceRequest request = new CheckAccountBalanceRequest();
        request.setAccountNumber("100000001");

        Account account = new Account();
        account.setAccountNumber("100000001");
        account.setBalance(new BigDecimal("100.00"));
        account.setCurrency(ECurrency.USD);

        CheckAccountBalanceResponse response = new CheckAccountBalanceResponse();
        response.setAccountNumber(account.getAccountNumber());
        response.setBalance(account.getBalance());
        response.setCurrency(account.getCurrency());

        when(accountRepository.findByAccountNumber("100000001")).thenReturn(Optional.of(account));
        when(accountMapper.mapCheckBalance(account)).thenReturn(response);

        DataResponseDto<CheckAccountBalanceResponse> result = accountService.checkAccountBalance(request);

        assertNotNull(result.getData());
        assertEquals(account.getBalance(), result.getData().getBalance());
    }

    @Test
    void checkAccountBalance_whenAccountNotFound_thenThrowException() {
        CheckAccountBalanceRequest request = new CheckAccountBalanceRequest();
        request.setAccountNumber("200000001");

        when(accountRepository.findByAccountNumber("200000001")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> accountService.checkAccountBalance(request));
    }

    @Test
    void getAccountWithLockByAccountNumber_whenFromAccountExist_thenResponseAccount() {
        Account account = new Account();
        account.setAccountNumber("100000001");

        when(accountRepository.findWithLockByAccountNumber("100000001")).thenReturn(Optional.of(account));

        Account result = accountService.getAccountWithLockByAccountNumber("100000001", true);
        assertEquals("100000001", result.getAccountNumber());
    }

    @Test
    void getAccountWithLockByAccountNumber_whenToAccountExist_thenResponseAccount() {
        Account account = new Account();
        account.setAccountNumber("100000002");

        when(accountRepository.findWithLockByAccountNumber("100000002")).thenReturn(Optional.of(account));

        Account result = accountService.getAccountWithLockByAccountNumber("100000002", false);
        assertEquals("100000002", result.getAccountNumber());
    }

    @Test
    void getAccountWithLockByAccountNumber_whenFromAccountNotFound_thenThrowException() {
        when(accountRepository.findWithLockByAccountNumber("100000003")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountWithLockByAccountNumber("100000003", true));
    }

    @Test
    void getAccountWithLockByAccountNumber_whenToAccountNotFound_thenThrowException() {
        when(accountRepository.findWithLockByAccountNumber("100000004")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountWithLockByAccountNumber("100000004", false));
    }

    @Test
    void updateAccount_whenCalled_thenSave() {
        Account account = new Account();
        account.setAccountNumber("100000001");
        account.setBalance(new BigDecimal("999.99"));

        accountService.updateAccount(account);

        verify(accountRepository).save(account);
    }

}
