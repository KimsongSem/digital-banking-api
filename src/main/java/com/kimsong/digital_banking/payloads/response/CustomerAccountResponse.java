package com.kimsong.digital_banking.payloads.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.utils.EAccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerAccountResponse implements Serializable {
    private Long id;
    private Customer customer;

    private String accountNumber;

    private EAccountType accountType;

    private BigDecimal balance;
}
