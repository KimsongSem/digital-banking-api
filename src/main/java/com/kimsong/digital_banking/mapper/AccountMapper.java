package com.kimsong.digital_banking.mapper;

import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.dtos.account.CustomerAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface AccountMapper {
    CustomerAccountResponse mapFromEntity(Account account);

    @Mapping(target = "customer", source = "customer")
    Account mapFromRequest(CreateCustomerAccountRequest request, Customer customer);

    CheckAccountBalanceResponse mapCheckBalance(Account account);
}

