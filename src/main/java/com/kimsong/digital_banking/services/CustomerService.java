package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.dtos.customer.CustomerRequest;
import com.kimsong.digital_banking.models.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerRequest request);

}
