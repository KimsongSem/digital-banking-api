package com.kimsong.digital_banking.services.implement;

import com.kimsong.digital_banking.exceptions.ValidationException;
import com.kimsong.digital_banking.services.CurrencyExchangeService;
import com.kimsong.digital_banking.config.providers.CurrencyRateProvider;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
    private final List<CurrencyRateProvider> providers;

    @Override
    public BigDecimal convert(BigDecimal amount, ECurrency from, ECurrency to) {
        if (from.equals(to)) {
            return amount;
        }

        CurrencyRateProvider provider = providers.stream()
                .filter(p -> p.supports(from, to))
                .findFirst()
                .orElseThrow(() -> new ValidationException(ErrorStatusEnum.UNSUPPORTED_CURRENCY_PAIR));

        return amount.multiply(provider.getRate(from, to));
    }

}