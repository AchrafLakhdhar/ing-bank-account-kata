package com.ing.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ing.entities.AccountTransaction;
import com.ing.entities.Client;
import com.ing.entities.Operation;
import com.ing.exceptions.BankException;
import com.ing.services.ClientService;

@SpringBootTest
@EnableTransactionManagement
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientServiceTest {

	@Autowired
	ClientService clientService;

	@Test
	public void testInvalidCreation() {
		Client newClient = ClientTest.prepareValidClient();
		newClient.setEmail(null);
		assertThrows(BankException.class, () -> clientService.addClient(newClient),
				"The creation must throw an exception");

	}

	@Test
	public void testValidCreation() {
		Client newClient = ClientTest.prepareValidClient();
		newClient = clientService.addClient(newClient);
		assertNotNull(newClient.getId());
		Client dbClient = clientService.findClient(newClient.getIdentityDocNumber());
		assertNotNull(dbClient, "The client does not exist in the database");

	}

	@Test
	public void testCreationOfExistingClient() {
		Client newClient = ClientTest.prepareValidClient();
		newClient = clientService.addClient(newClient);
		assertNotNull(newClient.getId());
		assertThrows(BankException.class, () -> clientService.addClient(ClientTest.prepareValidClient()),
				"The creation must throw an exception");

	}

	@Test
	public void testUpdateClient() {
		Client newClient = ClientTest.prepareValidClient();
		newClient = clientService.addClient(newClient);
		newClient.setPhoneNumber("+1 211000000");
		clientService.updateClient(newClient);
		Client dbClient = clientService.findClient(newClient.getIdentityDocNumber());
		assertEquals(dbClient.getPhoneNumber(), "+1 211000000");

	}

	@Test
	public void testDeleteClient() {
		Client newClient = ClientTest.prepareValidClient();
		newClient = clientService.addClient(newClient);
		clientService.deleteClient(newClient.getIdentityDocNumber());
		Client dbClient = clientService.findClient(newClient.getIdentityDocNumber());
		assertNull(dbClient, "Client must be deleted");

	}

	@Test
	public void testAddMultipleAccounts() {
		Client client = ClientTest.prepareValidClient();
		client = clientService.addClient(client);
		clientService.addAccount(client.getIdentityDocNumber());
		Client dbClient = clientService.findClient(client.getIdentityDocNumber());
		assertEquals(1, dbClient.getAccounts().size(), "Number of accounts must be 1");

		// Create a second account for the same user
		clientService.addAccount(client.getIdentityDocNumber());
		dbClient = clientService.findClient(client.getIdentityDocNumber());
		assertEquals(2, dbClient.getAccounts().size(), "Number of accounts must be 2");
	}

	@Test
	public void testDeposit() {
		Client client = ClientTest.prepareValidClient();
		client = clientService.addClient(client);
		clientService.addAccount(client.getIdentityDocNumber());
		Client dbClient = clientService.findClient(client.getIdentityDocNumber());
		String accountUuid = dbClient.getAccounts().get(0).getUuid();
		clientService.deposit(dbClient.getIdentityDocNumber(), accountUuid, 500);
		dbClient = clientService.findClient(client.getIdentityDocNumber());
		assertEquals(500, dbClient.getAccountBalance(accountUuid).getBalance());

	}

	@Test
	public void testWithdraw() {
		Client client = ClientTest.prepareValidClient();
		client = clientService.addClient(client);
		clientService.addAccount(client.getIdentityDocNumber());
		Client dbClient = clientService.findClient(client.getIdentityDocNumber());
		String accountUuid = dbClient.getAccounts().get(0).getUuid();
		clientService.deposit(dbClient.getIdentityDocNumber(), accountUuid, 500);
		clientService.withdraw(dbClient.getIdentityDocNumber(), accountUuid, 400);
		dbClient = clientService.findClient(client.getIdentityDocNumber());
		assertEquals(100, dbClient.getAccountBalance(accountUuid).getBalance());

	}

	@Test
	public void testTransactionHistory() {
		Client client = ClientTest.prepareValidClient();
		client = clientService.addClient(client);
		clientService.addAccount(client.getIdentityDocNumber());
		Client dbClient = clientService.findClient(client.getIdentityDocNumber());
		String accountUuid = dbClient.getAccounts().get(0).getUuid();
		clientService.deposit(dbClient.getIdentityDocNumber(), accountUuid, 500);
		clientService.withdraw(dbClient.getIdentityDocNumber(), accountUuid, 200);

		List<AccountTransaction> accountTransactions = clientService
				.getAccountTransactionHistory(dbClient.getIdentityDocNumber(), accountUuid);
		assertNotNull(accountTransactions, "Transaction history must not be null");
		assertEquals(2, accountTransactions.size(), "Transaction history must have 2 elements");

		/**
		 * Check each operation type and amount
		 */
		assertEquals(Operation.DEPOSIT, accountTransactions.get(0).getOperation());
		assertEquals(500, accountTransactions.get(0).getAmount());
		assertEquals(Operation.WITHDRAW, accountTransactions.get(1).getOperation());
		assertEquals(200, accountTransactions.get(1).getAmount());

	}

}
