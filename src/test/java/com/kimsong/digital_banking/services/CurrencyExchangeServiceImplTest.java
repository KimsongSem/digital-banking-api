package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.config.providers.CurrencyRateProvider;
import com.kimsong.digital_banking.exceptions.ValidationException;
import com.kimsong.digital_banking.services.implement.CurrencyExchangeServiceImpl;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeServiceImplTest {
    @Mock
    private CurrencyRateProvider provider;

    private CurrencyExchangeServiceImpl exchangeService;

    @BeforeEach
    void setup() {
        exchangeService = new CurrencyExchangeServiceImpl(List.of(provider));
    }

    @Test
    void convert_whenSameCurrency_thenReturnSameAmount() {
        BigDecimal amount = new BigDecimal("100.00");

        BigDecimal result = exchangeService.convert(amount, ECurrency.USD, ECurrency.USD);

        assertEquals(amount, result);
    }

    @Test
    void convert_whenCurrencyUSD2KHR_thenApplyExchangeRate() {
        BigDecimal amount = new BigDecimal("10.00");
        BigDecimal rate = new BigDecimal("4000.00"); //USD to KHR

        when(provider.supports(ECurrency.USD, ECurrency.KHR)).thenReturn(true);
        when(provider.getRate(ECurrency.USD, ECurrency.KHR)).thenReturn(rate);

        BigDecimal result = exchangeService.convert(amount, ECurrency.USD, ECurrency.KHR);

        assertEquals(new BigDecimal("40000.00"), result.setScale(2, RoundingMode.HALF_UP));
        verify(provider).supports(ECurrency.USD, ECurrency.KHR);
        verify(provider).getRate(ECurrency.USD, ECurrency.KHR);
    }

    @Test
    void convert_whenCurrencyKHR2USD_thenApplyExchangeRate() {
        BigDecimal amount = new BigDecimal("12000.00");
        BigDecimal rate = BigDecimal.ONE.divide(new BigDecimal("4000"), 8, RoundingMode.HALF_UP);//KHR to USD

        when(provider.supports(ECurrency.KHR, ECurrency.USD)).thenReturn(true);
        when(provider.getRate(ECurrency.KHR, ECurrency.USD)).thenReturn(rate);

        BigDecimal result = exchangeService.convert(amount, ECurrency.KHR, ECurrency.USD);

        assertEquals(new BigDecimal("3.00"), result.setScale(2, RoundingMode.HALF_UP));
        verify(provider).supports(ECurrency.KHR, ECurrency.USD);
        verify(provider).getRate(ECurrency.KHR, ECurrency.USD);
    }

    @Test
    void convert_whenCurrencyUnsupported_thenThrowException() {
        when(provider.supports(ECurrency.USD, ECurrency.EUR)).thenReturn(false);

        ValidationException ex = assertThrows(ValidationException.class,
                () -> exchangeService.convert(new BigDecimal("10.00"), ECurrency.USD, ECurrency.EUR));

        assertEquals(ErrorStatusEnum.UNSUPPORTED_CURRENCY_PAIR.message, ex.getMessage());
        verify(provider).supports(ECurrency.USD, ECurrency.EUR);
    }

}