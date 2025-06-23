package com.kimsong.digital_banking;

import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.customer.CustomerRequest;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyRequest;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.repositories.AccountRepository;
import com.kimsong.digital_banking.services.AccountService;
import com.kimsong.digital_banking.services.TransferService;
import com.kimsong.digital_banking.constants.enums.EAccountType;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DigitalBankingApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TransferService transferService;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AccountService accountService;

	@Test
	void concurrentTransfersMoney_FromSameAccount() throws InterruptedException {
//		// Setup
//		Account from = accountService.getAccountWithLockByAccountNumber("100000009", true);
//		Account to = accountService.getAccountWithLockByAccountNumber("100000010", false);

		int threads = 5;
		BigDecimal transferAmount = new BigDecimal("10.00");
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		CountDownLatch latch = new CountDownLatch(threads);

		for (int i = 0; i < threads; i++) {
			executor.submit(() -> {
				try {
					TransferMoneyRequest req = new TransferMoneyRequest();
					req.setFromAccountNumber("100000009");
					req.setToAccountNumber("100000010");
					req.setAmount(transferAmount);
					req.setPurpose("Test transfer");
					transferService.transferMoney(req);
				} catch (Exception e) {
					System.out.println("Transfer failed: " + e.getMessage());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();

//		// ✅ Check final balance
//		Account updatedFrom = accountRepository.findByAccountNumber("100000009").orElseThrow();
//		Account updatedTo = accountRepository.findByAccountNumber("100000010").orElseThrow();
//
//		System.out.println("FROM BALANCE: " + updatedFrom.getBalance());
//		System.out.println("TO BALANCE: " + updatedTo.getBalance());
//
//		// ✅ Ensure no overdraw
//		assertTrue(updatedFrom.getBalance().compareTo(BigDecimal.ZERO) >= 0);
//		assertEquals(new BigDecimal("100.00"), updatedFrom.getBalance().add(updatedTo.getBalance()));
	}

//	@BeforeEach
//	void setup() {
//		// Create accounts if not exist
//		accountRepository.deleteAll();
//
//		CustomerRequest customer1 = new CustomerRequest();
//		customer1.setFirstName("Kimnsong");
//		customer1.setLastName("SEM");
//		customer1.setPhone("0962192240");
//		CreateCustomerAccountRequest account1 = new CreateCustomerAccountRequest();
//		account1.setAccountNumber(1111);
//		account1.setAccountType(EAccountType.SAVINGS);
//		account1.setCurrency(ECurrency.USD);
//		account1.setBalance(new BigDecimal("1000.00"));
//		account1.setCustomer(customer1);
//		accountService.createAccount(account1);
//
//		CustomerRequest customer2 = new CustomerRequest();
//		customer2.setFirstName("Tra");
//		customer2.setLastName("22");
//		customer2.setPhone("0962192240");
//		CreateCustomerAccountRequest account2 = new CreateCustomerAccountRequest();
//		account2.setAccountNumber(2222);
//		account2.setAccountType(EAccountType.SAVINGS);
//		account2.setCurrency(ECurrency.USD);
//		account2.setBalance(new BigDecimal("0"));
//		account2.setCustomer(customer2);
//		accountService.createAccount(account2);
//
//		CustomerRequest customer3 = new CustomerRequest();
//		customer3.setFirstName("Roth");
//		customer3.setLastName("9T8");
//		customer3.setPhone("0962192240");
//		CreateCustomerAccountRequest account3 = new CreateCustomerAccountRequest();
//		account3.setAccountNumber(3333);
//		account3.setAccountType(EAccountType.SAVINGS);
//		account3.setCurrency(ECurrency.USD);
//		account3.setBalance(new BigDecimal("0"));
//		account3.setCustomer(customer3);
//		accountService.createAccount(account3);
//	}
//
//	@Test
//	void testConcurrentTransfersFromSameAccount1() throws InterruptedException {
//		ExecutorService executor = Executors.newFixedThreadPool(2);
//		CountDownLatch latch = new CountDownLatch(2);
//		BigDecimal transferAmount = new BigDecimal("600.00");
//
//		Runnable transferTo2222 = () -> {
//			try {
//				TransferMoneyRequest req = new TransferMoneyRequest();
//				req.setFromAccountNumber(1111);
//				req.setToAccountNumber(2222);
//				req.setAmount(transferAmount);
//				req.setPurpose("Test transfer");
//				transferService.transferMoney(req);
//			} catch (Exception e) {
//				System.out.println("Transfer to 2222 failed: " + e.getMessage());
//			} finally {
//				latch.countDown();
//			}
//		};
//
//		Runnable transferTo5555 = () -> {
//			try {
//				TransferMoneyRequest req = new TransferMoneyRequest();
//				req.setFromAccountNumber(1111);
//				req.setToAccountNumber(3333);
//				req.setAmount(transferAmount);
//				req.setPurpose("Test transfer");
//				transferService.transferMoney(req);
//			} catch (Exception e) {
//				System.out.println("Transfer to 3333 failed: " + e.getMessage());
//			} finally {
//				latch.countDown();
//			}
//		};
//
//		executor.execute(transferTo2222);
//		executor.execute(transferTo5555);
//
//		latch.await();
//		executor.shutdown();
//
//		System.out.println("---- FINAL BALANCES ----");
//		accountRepository.findAll().forEach(a ->
//				System.out.println("Account " + a.getAccountNumber() + ": " + a.getBalance()));
//	}


}
