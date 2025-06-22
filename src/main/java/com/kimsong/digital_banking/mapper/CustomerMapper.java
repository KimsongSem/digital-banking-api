package com.kimsong.digital_banking.mapper;

import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.dtos.customer.CustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerRequest mapFromEntity(Customer customer);

    @Mapping(target = "id", ignore = true)
    Customer mapFromRequest(CustomerRequest request, String CIF);
}

