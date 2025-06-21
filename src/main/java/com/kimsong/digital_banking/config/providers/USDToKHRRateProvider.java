package com.kimsong.digital_banking.config.providers;

import com.kimsong.digital_banking.config.CurrencyProperties;
import com.kimsong.digital_banking.utils.ECurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class USDToKHRRateProvider implements CurrencyRateProvider {
    private final CurrencyProperties currencyProp;

    @Override
    public boolean supports(ECurrency from, ECurrency to) {
        return from.equals(ECurrency.USD) && to.equals(ECurrency.KHR);
    }

    @Override
    public BigDecimal getRate(ECurrency from, ECurrency to) {
        return currencyProp.getUSD_KHR();
    }
}
