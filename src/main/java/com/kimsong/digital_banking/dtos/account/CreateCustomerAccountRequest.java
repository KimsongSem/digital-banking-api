package com.kimsong.digital_banking.dtos.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kimsong.digital_banking.dtos.customer.CustomerRequest;
import com.kimsong.digital_banking.utils.EAccountType;
import com.kimsong.digital_banking.utils.ECurrency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCustomerAccountRequest {
    @NotNull
    @Valid
    private CustomerRequest customer;

//    @NotBlank
//    private String accountNumber;

    @NotNull
    private EAccountType accountType;

    private BigDecimal balance;

    @NotNull
    private ECurrency currency;

}
