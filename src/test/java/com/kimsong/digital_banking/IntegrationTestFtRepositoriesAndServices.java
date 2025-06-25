package com.kimsong.digital_banking;

import com.kimsong.digital_banking.constants.enums.EChannel;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import com.kimsong.digital_banking.constants.enums.ETransactionStatus;
import com.kimsong.digital_banking.constants.enums.ETransactionType;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyRequest;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyResponse;
import com.kimsong.digital_banking.exceptions.ValidationException;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.models.Transaction;
import com.kimsong.digital_banking.repositories.AccountRepository;
import com.kimsong.digital_banking.repositories.CustomerRepository;
import com.kimsong.digital_banking.repositories.TransactionRepository;
import com.kimsong.digital_banking.services.TransferService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IntegrationTestFtRepositoriesAndServices {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransferService transferService;


    IntegrationTestFtRepositoriesAndServices() {
    }


    @BeforeEach
    void setUpDataForTest() {
        customerRepository.deleteAll();
        accountRepository.deleteAll();

        List<Customer> customerList = new ArrayList<>();
        Customer customerA = new Customer();
        customerA.setCIF("000001");

        Customer customerB = new Customer();
        customerB.setCIF("000002");

        Customer customerC = new Customer();
        customerC.setCIF("000003");

        customerList.add(customerA);
        customerList.add(customerB);
        customerList.add(customerC);
        customerRepository.saveAll(customerList);

        List<Account> accountList = new ArrayList<>();
        Account accountA = new Account();
        accountA.setAccountNumber("100000001");
        accountA.setBalance(new BigDecimal("100.00"));
        accountA.setCurrency(ECurrency.USD);
        accountA.setCustomer(customerA);


        Account accountB = new Account();
        accountB.setAccountNumber("100000002");
        accountB.setBalance(new BigDecimal("100.00"));
        accountB.setCurrency(ECurrency.USD);
        accountB.setCustomer(customerB);

        Account accountC = new Account();
        accountC.setAccountNumber("100000003");
        accountC.setBalance(new BigDecimal("100.00"));
        accountC.setCurrency(ECurrency.USD);
        accountC.setCustomer(customerC);

        accountList.add(accountA);
        accountList.add(accountB);
        accountList.add(accountC);
        accountRepository.saveAll(accountList);
    }

    @Test
    void concurrentTransfers_whenFromAccountIsSame_thenResponse() throws InterruptedException {
        System.out.println("---- ORIGINAL BALANCES ----");
        accountRepository.findAll().forEach(a ->
                System.out.println("Account " + a.getAccountNumber() + ": " + a.getBalance()));
        System.out.println("---------------------------");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        Runnable transferTo100000002 = () -> {
            try {
                TransferMoneyRequest request = baseRequest("100000001", "100000002", new BigDecimal("10.00"));
                System.out.println("transfer from 100000001 to 100000002");
                transferService.transferMoney(request);
            } catch (Exception e) {
                System.out.println("Transfer from 100000001 to 100000002 failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };

        Runnable transferTo100000003 = () -> {
            try {
                TransferMoneyRequest request = baseRequest("100000001", "100000003", new BigDecimal("10.00"));
                System.out.println("transfer from 100000001 to 100000003");
                transferService.transferMoney(request);
            } catch (Exception e) {
                System.out.println("Transfer from 100000001 to 100000003 failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };

        executor.execute(transferTo100000002);
        executor.execute(transferTo100000003);

        latch.await();
        executor.shutdown();

        System.out.println("---- FINAL BALANCES ----");
        accountRepository.findAll().forEach(a ->
                System.out.println("Account " + a.getAccountNumber() + ": " + a.getBalance()));
        System.out.println("------------------------");
    }

    @Test
    void concurrentTransfers_whenAccountA_To_AccountB_And_AccountB_To_AccuntA_thenResponse() throws InterruptedException {
        System.out.println("---- ORIGINAL BALANCES ----");
        accountRepository.findAll().forEach(a ->
                System.out.println("Account " + a.getAccountNumber() + ": " + a.getBalance()));
        System.out.println("---------------------------");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        Runnable transferAccountA_To_AccountB = () -> {
            try {
                TransferMoneyRequest request = baseRequest("100000001", "100000002", new BigDecimal("10.00"));
                System.out.println("transfer from 100000001 to 100000002");
                transferService.transferMoney(request);
            } catch (Exception e) {
                System.out.println("Transfer from 100000001 to 100000002 failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };

        Runnable transferAccountB_To_AccountA = () -> {
            try {
                TransferMoneyRequest request = baseRequest("100000002", "100000001", new BigDecimal("20.00"));

                System.out.println("transfer from 100000002 to 100000001");
                transferService.transferMoney(request);
            } catch (Exception e) {
                System.out.println("Transfer from 100000002 to 100000001 failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };

        executor.execute(transferAccountA_To_AccountB);
        executor.execute(transferAccountB_To_AccountA);

        latch.await();
        executor.shutdown();

        System.out.println("---- FINAL BALANCES ----");
        accountRepository.findAll().forEach(a ->
                System.out.println("Account " + a.getAccountNumber() + ": " + a.getBalance()));
        System.out.println("------------------------");
    }

    @Test
    void transferMoney_fromAccountA_TO_AccountB_thenReturnResponse() {
        TransferMoneyRequest request = baseRequest("100000001", "100000002", new BigDecimal("100.00"));
        DataResponseDto<TransferMoneyResponse> result = transferService.transferMoney(request);

        assertNotNull(result.getData());
        assertEquals("100000001", result.getData().getFromAccountNumber());
        assertEquals("100000002", result.getData().getToAccountNumber());
        assertEquals(new BigDecimal("100.00"), result.getData().getAmount());
    }

    @Test
    void transferMoney_whenSameAccountTransfer_thenThrowException() {
        TransferMoneyRequest request = baseRequest("100000001", "100000001", new BigDecimal("100.00"));

        ValidationException ex = assertThrows(ValidationException.class, () ->
                transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.SAME_ACCOUNT_TRANSFER, ex.getStatusEnum());
    }

    @Test
    void transferMoney_whenInsufficientBalance_thenThrowException() {
        TransferMoneyRequest request = baseRequest("100000001", "100000002", new BigDecimal("500.00"));

        ValidationException ex = assertThrows(ValidationException.class, () ->
                transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.INSUFFICIENT_BALANCE, ex.getStatusEnum());
    }

    @Test
    void transferMoney_whenAmountLessThanMinimum_thenThrowException() {
        TransferMoneyRequest request = baseRequest("100000001", "100000002", new BigDecimal("0.001"));

        ValidationException ex = assertThrows(ValidationException.class, () ->
                transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.AMOUNT_LESS_THAN_MIN_LIMIT, ex.getStatusEnum());
    }

    @Test
    void transferMoney_whenSingleTransferLimitExceeded_thenThrowException() {
        Customer customerD = new Customer();
        customerD.setCIF("000004");
        customerRepository.save(customerD);

        Account accountD = new Account();
        accountD.setAccountNumber("100000005");
        accountD.setBalance(new BigDecimal("1000000.00"));
        accountD.setCurrency(ECurrency.USD);
        accountD.setCustomer(customerD);
        accountRepository.save(accountD);

        TransferMoneyRequest request = baseRequest("100000005", "100000002", new BigDecimal("15000.00"));

        ValidationException ex = assertThrows(ValidationException.class, () ->
                transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.SINGLE_TRANSFER_EXCEEDED_LIMIT, ex.getStatusEnum());
    }

    @Test
    void transferMoney_whenDailyTransferLimitExceeded_thenThrowException() {
        Account account = accountRepository.findByAccountNumber("100000001").get();
        Transaction transactionToday = new Transaction();
        transactionToday.setTransactionReference("TESTING-123456");
        transactionToday.setAccount(account);
        transactionToday.setTransactionType(ETransactionType.DEBIT);
        transactionToday.setAmount(new BigDecimal("9999999"));
        transactionToday.setCurrency(ECurrency.USD);
        transactionToday.setStatus(ETransactionStatus.SUCCESS);
        transactionToday.setChannel(EChannel.API);
        transactionToday.setTransactionDate(Date.from(Instant.now()));
        transactionRepository.save(transactionToday);

        TransferMoneyRequest request = baseRequest("100000001", "100000002", new BigDecimal("100.00"));

        ValidationException ex = assertThrows(ValidationException.class, () ->
                transferService.transferMoney(request));

        assertEquals(ErrorStatusEnum.DAILY_TRANSFER_EXCEEDED_LIMIT, ex.getStatusEnum());
    }

    private TransferMoneyRequest baseRequest(String from, String to, BigDecimal amount) {
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setFromAccountNumber(from);
        request.setToAccountNumber(to);
        request.setAmount(amount);
        return request;
    }


}
