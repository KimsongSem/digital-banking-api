package com.kimsong.digital_banking.services.implement;

import com.kimsong.digital_banking.exception.NotFoundException;
import com.kimsong.digital_banking.mapper.IAccountMapper;
import com.kimsong.digital_banking.mapper.ICustomerMapper;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.payloads.request.CheckAccountBalanceRequest;
import com.kimsong.digital_banking.payloads.request.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.payloads.response.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.repositories.AccountRepository;
import com.kimsong.digital_banking.repositories.CustomerRepository;
import com.kimsong.digital_banking.services.IAccountService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountImpl implements IAccountService {

    private final IAccountMapper accountMapper;
    private final ICustomerMapper customerMapper;
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
                .orElseThrow(() -> new NotFoundException("Account not found!"));

        return DataResponseDto.<CheckAccountBalanceResponse>builder()
                .data(accountMapper.mapCheckBalance(account))
                .build();
    }

}
