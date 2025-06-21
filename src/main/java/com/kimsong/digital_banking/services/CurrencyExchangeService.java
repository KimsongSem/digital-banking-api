package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.utils.ECurrency;

import java.math.BigDecimal;

public interface CurrencyExchangeService {
    BigDecimal convert(BigDecimal amount, ECurrency from, ECurrency to);
}
