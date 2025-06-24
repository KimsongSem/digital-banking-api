package com.kimsong.digital_banking.services.implement;

import com.kimsong.digital_banking.config.LimitTransferProperties;
import com.kimsong.digital_banking.constants.enums.EChannel;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import com.kimsong.digital_banking.constants.enums.ETransactionStatus;
import com.kimsong.digital_banking.constants.enums.ETransactionType;
import com.kimsong.digital_banking.exceptions.ValidationException;
import com.kimsong.digital_banking.mappers.TransferMapper;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Transaction;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyRequest;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyResponse;
import com.kimsong.digital_banking.services.AccountService;
import com.kimsong.digital_banking.services.CurrencyExchangeService;
import com.kimsong.digital_banking.services.TransactionService;
import com.kimsong.digital_banking.services.TransferService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import com.kimsong.digital_banking.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final AccountService accountService;
    private final CurrencyExchangeService currencyExchangeService;
    private final TransactionService transactionService;
    private final TransferMapper transferMapper;
    private final LimitTransferProperties limitTransferProp;

    @Override
    @Transactional
    public DataResponseDto<TransferMoneyResponse> transferMoney(TransferMoneyRequest request) {
        String from = request.getFromAccountNumber();
        String to = request.getToAccountNumber();
        if (from.equals(to)) {
            log.error("Can't transfer for same account: {}", request.getFromAccountNumber());
            throw new ValidationException(ErrorStatusEnum.SAME_ACCOUNT_TRANSFER);
        }

        Account firstLock;
        Account secondLock;
        boolean fromIsFirst;

        if (from.compareTo(to) < 0) {
            firstLock = accountService.getAccountWithLockByAccountNumber(from, true);
            secondLock = accountService.getAccountWithLockByAccountNumber(to, false);
            fromIsFirst = true;
        } else {
            firstLock = accountService.getAccountWithLockByAccountNumber(to, false);
            secondLock = accountService.getAccountWithLockByAccountNumber(from, true);
            fromIsFirst = false;
        }

        Account fromAccount = fromIsFirst ? firstLock : secondLock;
        Account toAccount = fromIsFirst ? secondLock : firstLock;

        BigDecimal creditAmount = request.getAmount();
        BigDecimal deductAmount = currencyExchangeService.convert(
                creditAmount,
                toAccount.getCurrency(),
                fromAccount.getCurrency()
        );
        if (fromAccount.getBalance().compareTo(deductAmount) < 0) {
            log.error("{}, request balance: {}, available balance: {}", ErrorStatusEnum.INSUFFICIENT_BALANCE.message, deductAmount, fromAccount.getBalance());
            throw new ValidationException(ErrorStatusEnum.INSUFFICIENT_BALANCE);
        }

        BigDecimal amountInUSD = currencyExchangeService.convert(
                creditAmount,
                toAccount.getCurrency(),
                ECurrency.USD
        );
        if (amountInUSD.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            log.error(ErrorStatusEnum.AMOUNT_LESS_THAN_MIN_LIMIT.message);
            throw new ValidationException(ErrorStatusEnum.AMOUNT_LESS_THAN_MIN_LIMIT);
        }
        if (amountInUSD.compareTo(limitTransferProp.getSingleTransfer()) > 0) {
            log.error(ErrorStatusEnum.SINGLE_TRANSFER_EXCEEDED_LIMIT.message);
            throw new ValidationException(ErrorStatusEnum.SINGLE_TRANSFER_EXCEEDED_LIMIT);
        }

        BigDecimal getTodayDebitTotal = transactionService.getTodayDebitTotal(fromAccount);
        BigDecimal todayDebitInUSD = currencyExchangeService.convert(
                getTodayDebitTotal,
                fromAccount.getCurrency(),
                ECurrency.USD);

        BigDecimal totalTodayDebitInUSD = todayDebitInUSD.add(amountInUSD);
        if (totalTodayDebitInUSD.compareTo(limitTransferProp.getDailyTransfer()) > 0) {
            log.error(ErrorStatusEnum.DAILY_TRANSFER_EXCEEDED_LIMIT.message);
            throw new ValidationException(ErrorStatusEnum.DAILY_TRANSFER_EXCEEDED_LIMIT);
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(deductAmount));
        toAccount.setBalance(toAccount.getBalance().add(creditAmount));

        accountService.updateAccount(fromAccount);
        accountService.updateAccount(toAccount);
        log.info("Transfer success");

        // Log debit/credit transactions
        String txnReference = RefGeneratorUtil.generateTransactionRef();
        Date txnDate = Date.from(Instant.now());
        Transaction debitTransaction = new Transaction();
        debitTransaction.setTransactionReference(txnReference);
        debitTransaction.setAccount(fromAccount);
        debitTransaction.setTransactionType(ETransactionType.DEBIT);
        debitTransaction.setAmount(deductAmount);
        debitTransaction.setCurrency(fromAccount.getCurrency());
        debitTransaction.setStatus(ETransactionStatus.SUCCESS);
        debitTransaction.setChannel(EChannel.API);
        debitTransaction.setPurpose(request.getPurpose());
        debitTransaction.setTransactionDate(txnDate);
        transactionService.createTransaction(debitTransaction, ETransactionType.DEBIT);

        Transaction creditTransaction = new Transaction();
        creditTransaction.setTransactionReference(txnReference);
        creditTransaction.setAccount(toAccount);
        creditTransaction.setTransactionType(ETransactionType.CREDIT);
        creditTransaction.setAmount(creditAmount);
        creditTransaction.setCurrency(toAccount.getCurrency());
        creditTransaction.setStatus(ETransactionStatus.SUCCESS);
        creditTransaction.setChannel(EChannel.API);
        creditTransaction.setPurpose(request.getPurpose());
        creditTransaction.setTransactionDate(txnDate);
        transactionService.createTransaction(creditTransaction, ETransactionType.CREDIT);

        TransferMoneyResponse response = transferMapper.mapFromRequest(request);
        response.setTransactionReference(txnReference);
        response.setChannel(EChannel.API);
        response.setStatus(ETransactionStatus.SUCCESS);
        response.setTransactionDate(txnDate);
        response.setFromCcy(fromAccount.getCurrency());
        response.setToCcy(toAccount.getCurrency());
        return DataResponseDto.<TransferMoneyResponse>builder()
                .data(response)
                .build();

    }

}
