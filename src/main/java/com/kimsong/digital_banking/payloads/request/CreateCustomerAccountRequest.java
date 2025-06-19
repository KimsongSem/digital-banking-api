package com.kimsong.digital_banking.payloads.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kimsong.digital_banking.utils.EAccountType;
import com.kimsong.digital_banking.utils.ECurrency;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCustomerAccountRequest {
    private CustomerRequest customer;

    private String accountNumber;

    private EAccountType accountType;

    private BigDecimal balance;

    private ECurrency currency;

}
