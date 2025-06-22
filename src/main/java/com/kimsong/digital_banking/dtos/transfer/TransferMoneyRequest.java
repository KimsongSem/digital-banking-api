package com.kimsong.digital_banking.dtos.transfer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferMoneyRequest {
    @NotBlank
    @Pattern(regexp = "^\\d{9}$", message = "Invalid from account number")
    private String fromAccountNumber;

    @NotBlank
    @Pattern(regexp = "^\\d{9}$", message = "Invalid to account number")
    private String toAccountNumber;

    @NotNull
    private BigDecimal amount;

    private String purpose;
}