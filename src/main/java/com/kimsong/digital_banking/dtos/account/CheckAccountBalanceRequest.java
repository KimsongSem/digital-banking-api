package com.kimsong.digital_banking.dtos.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckAccountBalanceRequest {
    private Integer accountNumber;
}
