package com.ing.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ing.entities.Account;
import com.ing.entities.AccountTransaction;
import com.ing.entities.Client;
import com.ing.entities.Operation;
import com.ing.exceptions.BankException;
import com.ing.utilities.ClientUtils;

public class ClientTest {

	private static final String IDENTITY_DOC_NUMBER = "S478554";
	private static final String FIRST_NAME = "Walter";
	private static final String LAST_NAME = "White";
	private static final String NATIONALITY = "American";
	private static final String ADDRESS = "3838 Piermont Drive Albuquerque";
	private static final String EMAIL = "walter.white@gmail.com";
	private static final String PHONE_NUMBER = "+1 500100455";

	private static final String ACCOUNT_UUID = "123-456";
	private static final String ACCOUNT_UUID_BIS = "456-789";

	@Test
	public void testValidClient() {
		Client newClient = prepareValidClient();
		boolean isValid = ClientUtils.isValidClient(newClient);
		assertTrue(isValid, "Client must be valid");

	}

	@Test
	public void testInvalidClient() {
		Client newClient = prepareValidClient();
		newClient.setEmail(null);
		boolean isValid = ClientUtils.isValidClient(newClient);
		assertFalse(isValid, "Client must be invalid");

	}

	@Test
	public void testAccountCreation() {
		Client client = prepareValidClient();
		Account account = new Account(ACCOUNT_UUID);
		client.addAccount(account);
		assertEquals(1, client.getAccounts().size(), "Number of client accounts must be 1");
		assertEquals(0, client.getAccounts().get(0).getBalance(), "Account balance must be 0");
	}

	@Test
	public void testMultipleAccountCreation() {
		Client client = prepareValidClient();
		Account account = new Account(ACCOUNT_UUID);
		Account accountBis = new Account(ACCOUNT_UUID_BIS);
		client.addAccount(account);
		client.addAccount(accountBis);
		assertEquals(2, client.getAccounts().size(), "Number of client accounts must be 2");
		assertEquals(0, client.getAccounts().get(0).getBalance(), "Account balance of first account must be 0");
		assertEquals(0, client.getAccounts().get(1).getBalance(), "Account balance of second account must be 0");

	}

	@Test
	public void testAccountDeposit() {
		Client client = prepareValidClient();
		Account account = new Account(ACCOUNT_UUID);
		client.addAccount(account);
		client.deposit(ACCOUNT_UUID, 500);
		assertEquals(500, client.getAccounts().get(0).getBalance(), "Account balance must be 500");
	}

	@Test
	public void testAccountDepositFail() {
		Client client = prepareValidClient();
		Account account = new Account(ACCOUNT_UUID);
		client.addAccount(account);

		assertThrows(BankException.class, () -> client.deposit(ACCOUNT_UUID, 0.001),
				"The deposit must throw an exception");
	}

	@Test
	public void testAccountWithdraw() {
		Client client = prepareValidClient();
		Account account = new Account(ACCOUNT_UUID);
		client.addAccount(account);
		client.deposit(ACCOUNT_UUID, 500);
		client.withdraw(ACCOUNT_UUID, 100);
		assertEquals(400, client.getAccounts().get(0).getBalance(), "Account balance must be 400");
	}

	@Test
	public void testAccountWithdrawNegativeAmount() {
		Client client = prepareValidClient();
		Account account = new Account(ACCOUNT_UUID);
		client.addAccount(account);
		client.deposit(ACCOUNT_UUID, 500);
		assertThrows(BankException.class, () -> client.withdraw(ACCOUNT_UUID, -100),
				"The withdraw must throw an exception");
	}

	@Test
	public void testAccountWithdrawGreaterAmount() {
		Client client = prepareValidClient();
		Account account = new Account(ACCOUNT_UUID);
		client.addAccount(account);
		client.deposit(ACCOUNT_UUID, 500);
		assertThrows(BankException.class, () -> client.withdraw(ACCOUNT_UUID, 600),
				"The withdraw must throw an exception");
	}
	
	@Test
	public void testDisplayBalance() {
		Client client = prepareValidClient();
		Account account = new Account(ACCOUNT_UUID);
		client.addAccount(account);
		client.deposit(ACCOUNT_UUID, 500);
		client.withdraw(ACCOUNT_UUID, 200);
		client.deposit(ACCOUNT_UUID, 50);
		assertEquals(350, client.getAccountBalance(ACCOUNT_UUID).getBalance());
	}
	
	@Test
	public void testAccountTransactionHistory() {
		Client client = prepareValidClient();
		Account account = new Account(ACCOUNT_UUID);
		client.addAccount(account);
		client.deposit(ACCOUNT_UUID, 500);
		client.withdraw(ACCOUNT_UUID, 200);
		client.deposit(ACCOUNT_UUID, 50);
		List<AccountTransaction> accountTransactions = client.getAccountTransactionHistory(ACCOUNT_UUID);
		assertNotNull(accountTransactions, "Transaction history must not be null");
		assertEquals(3, accountTransactions.size(), "Transaction history must have 3 elements");
		/**
		 * Check each operation type and amount
		 */
		assertEquals(Operation.DEPOSIT, accountTransactions.get(0).getOperation());
		assertEquals(500, accountTransactions.get(0).getAmount());
		assertEquals(Operation.WITHDRAW, accountTransactions.get(1).getOperation());
		assertEquals(200, accountTransactions.get(1).getAmount());
		assertEquals(Operation.DEPOSIT, accountTransactions.get(2).getOperation());
		assertEquals(50, accountTransactions.get(2).getAmount());
	}
	
	

	public static Client prepareValidClient() {
		Client client = new Client();
		client.setIdentityDocNumber(IDENTITY_DOC_NUMBER);
		client.setFirstName(FIRST_NAME);
		client.setLastName(LAST_NAME);
		client.setNationality(NATIONALITY);
		client.setAddress(ADDRESS);
		client.setEmail(EMAIL);
		client.setPhoneNumber(PHONE_NUMBER);
		return client;

	}

}
