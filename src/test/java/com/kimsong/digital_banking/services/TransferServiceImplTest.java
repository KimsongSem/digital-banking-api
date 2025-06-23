package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.config.LimitTransferProperties;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyRequest;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyResponse;
import com.kimsong.digital_banking.exceptions.ValidationException;
import com.kimsong.digital_banking.mappers.TransferMapper;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.services.implement.TransferServiceImpl;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceImplTest {
    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private AccountService accountService;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransferMapper transferMapper;

    @Mock
    private LimitTransferProperties limitTransferProp;

    private TransferMoneyRequest baseRequest(String from, String to, BigDecimal amount) {
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setFromAccountNumber(from);
        request.setToAccountNumber(to);
        request.setAmount(amount);
        return request;
    }

    private Account account(String number, ECurrency currency, BigDecimal balance) {
        Account account = new Account();
        account.setAccountNumber(number);
        account.setCurrency(currency);
        account.setBalance(balance);
        return account;
    }

    @Test
    void transferMoney_fromUSDTOUSD_thenReturnResponse() {
        BigDecimal transferAmount = new BigDecimal("100.00");
        TransferMoneyRequest request = baseRequest("100000001", "100000002", transferAmount);
        Account from = account("100000001", ECurrency.USD, new BigDecimal("1000.00"));
        Account to = account("100000002", ECurrency.USD, new BigDecimal("500.00"));

        TransferMoneyResponse response = new TransferMoneyResponse();
        response.setFromAccountNumber("100000001");
        response.setToAccountNumber("100000002");
        response.setAmount(transferAmount);

        when(accountService.getAccountWithLockByAccountNumber("100000001", true)).thenReturn(from);
        when(accountService.getAccountWithLockByAccountNumber("100000002", false)).thenReturn(to);

        BigDecimal deductAmount = new BigDecimal("100.00");
        BigDecimal amountUSD = new BigDecimal("100.00");
        when(currencyExchangeService.convert(deductAmount, ECurrency.USD, ECurrency.USD)).thenReturn(deductAmount);
        when(currencyExchangeService.convert(amountUSD, ECurrency.USD, ECurrency.USD)).thenReturn(amountUSD);

        when(transactionService.getTodayDebitTotal(from)).thenReturn(BigDecimal.ZERO);
        when(currencyExchangeService.convert(BigDecimal.ZERO, ECurrency.USD, ECurrency.USD)).thenReturn(BigDecimal.ZERO);

        when(limitTransferProp.getSingleTransfer()).thenReturn(new BigDecimal("10000.00"));
        when(limitTransferProp.getDailyTransfer()).thenReturn(new BigDecimal("10000.00"));

        when(transferMapper.mapFromRequest(request)).thenReturn(response);

        DataResponseDto<TransferMoneyResponse> result = transferService.transferMoney(request);

        assertNotNull(result.getData());
        assertEquals(transferAmount, result.getData().getAmount());
        verify(accountService).updateAccount(from);
        verify(accountService).updateAccount(to);
        verify(transactionService, times(2)).createTransaction(any(), any());
    }

    @Test
    void transferMoney_fromUSDTOKHR_thenReturnResponse() {
        BigDecimal transferAmount = new BigDecimal("400000.00");
        TransferMoneyRequest request = baseRequest("100000001", "100000003", transferAmount);
        Account from = account("100000001", ECurrency.USD, new BigDecimal("1000.00"));
        Account to = account("100000003", ECurrency.KHR, new BigDecimal("500000.00"));

        TransferMoneyResponse response = new TransferMoneyResponse();
        response.setFromAccountNumber("100000001");
        response.setToAccountNumber("100000003");
        response.setAmount(transferAmount);

        when(accountService.getAccountWithLockByAccountNumber("100000001", true)).thenReturn(from);
        when(accountService.getAccountWithLockByAccountNumber("100000003", false)).thenReturn(to);

        BigDecimal deductAmount = new BigDecimal("100.00"); //amount after /4000
        BigDecimal amountUSD = new BigDecimal("100.00"); //amount after /4000

        when(currencyExchangeService.convert(transferAmount, ECurrency.KHR, ECurrency.USD)).thenReturn(deductAmount);
        when(currencyExchangeService.convert(transferAmount, ECurrency.KHR, ECurrency.USD)).thenReturn(amountUSD);

        when(transactionService.getTodayDebitTotal(from)).thenReturn(BigDecimal.ZERO);
        when(currencyExchangeService.convert(BigDecimal.ZERO, ECurrency.USD, ECurrency.USD)).thenReturn(BigDecimal.ZERO);

        when(limitTransferProp.getSingleTransfer()).thenReturn(new BigDecimal("10000.00"));
        when(limitTransferProp.getDailyTransfer()).thenReturn(new BigDecimal("10000.00"));

        when(transferMapper.mapFromRequest(request)).thenReturn(response);

        DataResponseDto<TransferMoneyResponse> result = transferService.transferMoney(request);

        assertNotNull(result.getData());
        assertEquals(transferAmount, result.getData().getAmount());

        verify(accountService).updateAccount(from);
        verify(accountService).updateAccount(to);
        verify(transactionService, times(2)).createTransaction(any(), any());
    }

    @Test
    void transferMoney_fromKHRTOUSD_thenReturnResponse() {
        BigDecimal transferAmount = new BigDecimal("100.00");
        TransferMoneyRequest request = baseRequest("100000003", "100000001", transferAmount);
        Account from = account("100000003", ECurrency.KHR, new BigDecimal("500000.00"));
        Account to = account("100000001", ECurrency.USD, new BigDecimal("1000.00"));

        // USD → KHR for deducting from KHR account
        BigDecimal deductAmount = new BigDecimal("400000.00"); //100 * 4000
        BigDecimal amountInUSD = transferAmount;

        TransferMoneyResponse response = new TransferMoneyResponse();
        response.setFromAccountNumber("100000003");
        response.setToAccountNumber("100000001");
        response.setAmount(transferAmount);

        when(accountService.getAccountWithLockByAccountNumber("100000003", true)).thenReturn(from);
        when(accountService.getAccountWithLockByAccountNumber("100000001", false)).thenReturn(to);

        when(currencyExchangeService.convert(transferAmount, ECurrency.USD, ECurrency.KHR)).thenReturn(deductAmount);
        when(currencyExchangeService.convert(transferAmount, ECurrency.USD, ECurrency.USD)).thenReturn(amountInUSD);

        when(transactionService.getTodayDebitTotal(from)).thenReturn(BigDecimal.ZERO);
        when(currencyExchangeService.convert(BigDecimal.ZERO, ECurrency.KHR, ECurrency.USD)).thenReturn(BigDecimal.ZERO);

        when(limitTransferProp.getSingleTransfer()).thenReturn(new BigDecimal("10000.00"));
        when(limitTransferProp.getDailyTransfer()).thenReturn(new BigDecimal("10000.00"));
        when(transferMapper.mapFromRequest(request)).thenReturn(response);

        DataResponseDto<TransferMoneyResponse> result = transferService.transferMoney(request);

        assertNotNull(result);
        assertEquals("100000003", result.getData().getFromAccountNumber());
        assertEquals("100000001", result.getData().getToAccountNumber());
        assertEquals(transferAmount, result.getData().getAmount());

        verify(accountService).updateAccount(from);
        verify(accountService).updateAccount(to);
        verify(transactionService, times(2)).createTransaction(any(), any());
    }

    @Test
    void transferMoney_whenSameAccountTransfer_thenThrowException() {
        BigDecimal transferAmount = new BigDecimal("100.00");
        TransferMoneyRequest request = baseRequest("100000001", "100000001", transferAmount);

        ValidationException ex = assertThrows(ValidationException.class, () -> transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.SAME_ACCOUNT_TRANSFER, ex.getStatusEnum());
    }

    @Test
    void transferMoney_whenInsufficientBalance_thenThrowException() {
        BigDecimal transferAmount = new BigDecimal("100.00");
        TransferMoneyRequest request = baseRequest("100000001", "100000002", transferAmount);
        Account from = account("100000001", ECurrency.USD, new BigDecimal("50.00"));
        Account to = account("100000002", ECurrency.USD, new BigDecimal("50.00"));

        when(accountService.getAccountWithLockByAccountNumber("100000001", true)).thenReturn(from);
        when(accountService.getAccountWithLockByAccountNumber("100000002", false)).thenReturn(to);
        when(currencyExchangeService.convert(transferAmount, ECurrency.USD, ECurrency.USD)).thenReturn(transferAmount);

        ValidationException ex = assertThrows(ValidationException.class, () -> transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.INSUFFICIENT_BALANCE, ex.getStatusEnum());
    }

    @Test
    void transferMoney_whenAmountLessThanMinimum_thenThrowException() {
        BigDecimal transferAmount = new BigDecimal("0.001");
        TransferMoneyRequest request = baseRequest("100000001", "100000002", transferAmount);
        Account from = account("100000001", ECurrency.USD, new BigDecimal("1000.00"));
        Account to = account("100000002", ECurrency.USD, new BigDecimal("500.00"));

        when(accountService.getAccountWithLockByAccountNumber("100000001", true)).thenReturn(from);
        when(accountService.getAccountWithLockByAccountNumber("100000002", false)).thenReturn(to);
        when(currencyExchangeService.convert(transferAmount, ECurrency.USD, ECurrency.USD)).thenReturn(transferAmount);

        ValidationException ex = assertThrows(ValidationException.class, () -> transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.AMOUNT_LESS_THAN_MIN_LIMIT, ex.getStatusEnum());
    }

    @Test
    void transferMoney_whenSingleTransferLimitExceeded_thenThrowException() {
        BigDecimal transferAmount = new BigDecimal("1500.00");
        TransferMoneyRequest request = baseRequest("100000001", "100000002", transferAmount);
        Account from = account("100000001", ECurrency.USD, new BigDecimal("2000.00"));
        Account to = account("100000002", ECurrency.USD, new BigDecimal("500.00"));

        when(accountService.getAccountWithLockByAccountNumber("100000001", true)).thenReturn(from);
        when(accountService.getAccountWithLockByAccountNumber("100000002", false)).thenReturn(to);
        when(currencyExchangeService.convert(transferAmount, ECurrency.USD, ECurrency.USD)).thenReturn(transferAmount);
        when(limitTransferProp.getSingleTransfer()).thenReturn(new BigDecimal("1000"));

        ValidationException ex = assertThrows(ValidationException.class, () ->
                transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.SINGLE_TRANSFER_EXCEEDED_LIMIT, ex.getStatusEnum());
    }

    @Test
    void transferMoney_whenDailyTransferLimitExceeded_thenThrowException() {
        BigDecimal transferAmount = new BigDecimal("1000.00");
        TransferMoneyRequest request = baseRequest("100000001", "100000002", transferAmount);
        Account from = account("100000001", ECurrency.USD, new BigDecimal("2000.00"));
        Account to = account("100000002", ECurrency.USD, new BigDecimal("500.00"));

        when(accountService.getAccountWithLockByAccountNumber("100000001", true)).thenReturn(from);
        when(accountService.getAccountWithLockByAccountNumber("100000002", false)).thenReturn(to);
        when(currencyExchangeService.convert(transferAmount, ECurrency.USD, ECurrency.USD)).thenReturn(transferAmount);
        when(limitTransferProp.getSingleTransfer()).thenReturn(new BigDecimal("1000"));
        when(transactionService.getTodayDebitTotal(from)).thenReturn(new BigDecimal("1500"));
        when(currencyExchangeService.convert(new BigDecimal("1500"), ECurrency.USD, ECurrency.USD)).thenReturn(new BigDecimal("1500"));
        when(limitTransferProp.getDailyTransfer()).thenReturn(new BigDecimal("2000"));
        when(currencyExchangeService.convert(transferAmount, ECurrency.USD, ECurrency.USD)).thenReturn(transferAmount);

        ValidationException ex = assertThrows(ValidationException.class, () ->
                transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.DAILY_TRANSFER_EXCEEDED_LIMIT, ex.getStatusEnum());

    }


}
