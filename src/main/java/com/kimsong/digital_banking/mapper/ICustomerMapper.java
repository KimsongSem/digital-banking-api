package com.kimsong.digital_banking.mapper;

import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.payloads.request.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.payloads.request.CustomerRequest;
import com.kimsong.digital_banking.payloads.response.CustomerAccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {
    CustomerRequest mapFromEntity(Customer customer);

    Customer mapFromRequest(CustomerRequest request);
}

