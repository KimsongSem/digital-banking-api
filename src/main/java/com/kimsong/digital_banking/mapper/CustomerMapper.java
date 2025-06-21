package com.kimsong.digital_banking.mapper;

import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.dtos.customer.CustomerRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerRequest mapFromEntity(Customer customer);

    Customer mapFromRequest(CustomerRequest request);
}

