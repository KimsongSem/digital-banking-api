package com.kimsong.digital_banking.services.implement;

import com.kimsong.digital_banking.exception.ResourceNotFoundException;
import com.kimsong.digital_banking.mapper.AccountMapper;
import com.kimsong.digital_banking.mapper.CustomerMapper;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceRequest;
import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.repositories.AccountRepository;
import com.kimsong.digital_banking.repositories.CustomerRepository;
import com.kimsong.digital_banking.services.AccountService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private static final BigDecimal DAILY_LIMIT = new BigDecimal("10000.00");

    private final AccountMapper accountMapper;
    private final CustomerMapper customerMapper;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public void createAccount(CreateCustomerAccountRequest request) {
        Customer customer = customerMapper.mapFromRequest(request.getCustomer());
        customerRepository.save(customer);
        log.info("Customer created :{}", customer);

        Account account = accountMapper.mapFromRequest(request, customer);
        accountRepository.save(account);
        log.info("Account created :{}", account);
    }

    @Override
    public DataResponseDto<CheckAccountBalanceResponse> checkAccountBalance(CheckAccountBalanceRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorStatusEnum.ACCOUNT_NOT_FOUND));

        return DataResponseDto.<CheckAccountBalanceResponse>builder()
                .data(accountMapper.mapCheckBalance(account))
                .build();
    }

    @Override
    public Account getAccountByAccountNumber(Integer accountNumber, boolean isFromAccount) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    if (isFromAccount) {
                        log.error(ErrorStatusEnum.FROM_ACCOUNT_NOT_FOUND.message);
                        return new ResourceNotFoundException(ErrorStatusEnum.FROM_ACCOUNT_NOT_FOUND);
                    }
                    else {
                        log.error(ErrorStatusEnum.TO_ACCOUNT_NOT_FOUND.message);
                        return new ResourceNotFoundException(ErrorStatusEnum.TO_ACCOUNT_NOT_FOUND);
                    }
                });
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
        log.info("Account updated :{}", account);
    }

}
