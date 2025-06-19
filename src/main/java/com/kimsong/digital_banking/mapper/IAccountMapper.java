package com.kimsong.digital_banking.mapper;

import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.payloads.request.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.payloads.response.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.payloads.response.CustomerAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ICustomerMapper.class})
public interface IAccountMapper {
    CustomerAccountResponse mapFromEntity(Account account);

    @Mapping(target = "customer", source = "customer")
    Account mapFromRequest(CreateCustomerAccountRequest request, Customer customer);

    CheckAccountBalanceResponse mapCheckBalance(Account account);
}

