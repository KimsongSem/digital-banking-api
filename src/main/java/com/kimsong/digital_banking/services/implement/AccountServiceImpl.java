package com.kimsong.digital_banking.services.implement;

import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CustomerAccountResponse;
import com.kimsong.digital_banking.exceptions.ResourceNotFoundException;
import com.kimsong.digital_banking.generators.SequenceGenerator;
import com.kimsong.digital_banking.mappers.AccountMapper;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.repositories.AccountRepository;
import com.kimsong.digital_banking.services.AccountService;
import com.kimsong.digital_banking.services.CustomerService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final SequenceGenerator generator;
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    @Override
    @Transactional
    public DataResponseDto<CustomerAccountResponse> createAccount(CreateCustomerAccountRequest request) {
        Customer customer = customerService.createCustomer(request.getCustomer());
        Account account = accountMapper.mapFromRequest(request, generator.generateAccountNumber());
        account.setCustomer(customer);
        accountRepository.save(account);
        log.info("Account created with account number:{}", account.getAccountNumber());

        return DataResponseDto.<CustomerAccountResponse>builder()
                .data(accountMapper.mapFromEntity(account))
                .build();
    }

    @Override
    @Transactional
    public DataResponseDto<CheckAccountBalanceResponse> checkAccountBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    log.error("{}: {}", ErrorStatusEnum.ACCOUNT_NOT_FOUND.message, accountNumber);
                    return new ResourceNotFoundException(ErrorStatusEnum.ACCOUNT_NOT_FOUND);
                });

        return DataResponseDto.<CheckAccountBalanceResponse>builder()
                .data(accountMapper.mapCheckBalance(account))
                .build();
    }

    @Override
    public Account getAccountWithLockByAccountNumber(String accountNumber, boolean isFromAccount) {
        return accountRepository.findWithLockByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    if (isFromAccount) {
                        log.error("{}: {}", ErrorStatusEnum.FROM_ACCOUNT_NOT_FOUND.message, accountNumber);
                        return new ResourceNotFoundException(ErrorStatusEnum.FROM_ACCOUNT_NOT_FOUND);
                    }
                    else {
                        log.error("{}: {}",ErrorStatusEnum.TO_ACCOUNT_NOT_FOUND.message, accountNumber);
                        return new ResourceNotFoundException(ErrorStatusEnum.TO_ACCOUNT_NOT_FOUND);
                    }
                });
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
        log.info("Account updated with account number:{}", account.getAccountNumber());
    }

}
