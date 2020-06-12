package com.ing.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ing.entities.Account;
import com.ing.entities.AccountBalance;
import com.ing.entities.AccountTransaction;
import com.ing.entities.Client;
import com.ing.exceptions.BankException;
import com.ing.exceptions.ErrorCodes;
import com.ing.repositories.ClientRepository;
import com.ing.services.ClientService;
import com.ing.utilities.ClientUtils;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	/**
	 * <br>
	 * Add a client to the database
	 * 
	 * <br>
	 * Checks that all the information are filled <br>
	 * Checks that the client does not exist
	 * 
	 * @param client
	 * @return client
	 */
	public Client addClient(Client client) {
		if (!ClientUtils.isValidClient(client)) {
			throw new BankException(ErrorCodes.E_0001, "All client information must be filled");
		}
		Client existingClient = clientRepository.findByIdentityDocNumber(client.getIdentityDocNumber());
		if (existingClient != null) {
			throw new BankException(ErrorCodes.E_0002, "The client already exists");
		}
		return clientRepository.save(client);

	}

	/**
	 * <br>
	 * Update a client <br>
	 * Checks that all the information are filled <br>
	 * Checks that the client already exists
	 * 
	 * @param client
	 * @return client
	 */
	public Client updateClient(Client client) {
		if (!ClientUtils.isValidClient(client)) {
			throw new BankException(ErrorCodes.E_0001, "All client information must be filled");
		}
		Client existingClient = clientRepository.findByIdentityDocNumber(client.getIdentityDocNumber());
		checkClientExistance(existingClient);
		copyData(client, existingClient);
		return clientRepository.save(existingClient);

	}

	/**
	 * <br>
	 * find a client by the identity document number <br>
	 * Checks that the identity document number is not null or emtpy
	 * 
	 * @param identityDocNumber
	 * @return client
	 */
	public Client findClient(String identityDocNumber) {
		if (StringUtils.isEmpty(identityDocNumber)) {
			throw new BankException(ErrorCodes.E_0004, "identity document number must not be null or empty");
		}
		return clientRepository.findByIdentityDocNumber(identityDocNumber);
	}

	/**
	 * Delete a client
	 * 
	 * @param identityDocNumber
	 * @return
	 */
	public void deleteClient(String identityDocNumber) {
		if (StringUtils.isEmpty(identityDocNumber)) {
			throw new BankException(ErrorCodes.E_0004, "identity document number must not be null or empty");
		}
		Client client = findClient(identityDocNumber);
		checkClientExistance(client);
		clientRepository.delete(client);

	}

	/**
	 * Delete a client
	 * 
	 * @param client
	 * @return
	 */
	public void deleteClient(Client client) {
		if (client != null) {
			clientRepository.delete(client);
		}

	}

	/**
	 * <br>
	 * Add an account to an existing client <br>
	 * Checks that the client already exists <br>
	 * Generate an UUID to the account
	 * 
	 * @param identityDocNumber
	 * @return list of the client accounts
	 */
	public List<Account> addAccount(String identityDocNumber) {
		Client client = findClient(identityDocNumber);
		checkClientExistance(client);
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		Account account = new Account(uuidString);
		client.addAccount(account);
		client = clientRepository.save(client);
		return client.getAccounts();
	}

	/**
	 * <br>
	 * Remove a client account <br>
	 * Checks that the client already exists <br>
	 * Checks that the account UUID is not empty or null <br>
	 * 
	 * @param identityDocNumber
	 * @param accountUuid
	 * @return list of the client accounts
	 */
	public List<Account> removeAccount(String identityDocNumber, String accountUuid) {
		Client client = findClient(identityDocNumber);
		checkClientExistance(client);
		checkAccountUuid(accountUuid);

		client.removeAccount(accountUuid);
		return clientRepository.save(client).getAccounts();
	}

	/**
	 * <br>
	 * find client accounts <br>
	 * Checks that the client already exists <br>
	 * 
	 * @param identityDocNumber
	 * @return list of the client accounts
	 */
	public List<Account> findAccounts(String identityDocNumber) {

		Client client = findClient(identityDocNumber);
		checkClientExistance(client);
		return client.getAccounts();

	}

	/**
	 * Deposit an amount of money from a customer to his account <br>
	 * Checks that the client already exists <br>
	 * Checks that the account UUID is not empty or null <br>
	 * 
	 * @param identityDocNumber
	 * @param accountUuid
	 * @param amount
	 */
	public void deposit(String identityDocNumber, String accountUuid, double amount) {

		Client client = findClient(identityDocNumber);
		checkClientExistance(client);
		checkAccountUuid(accountUuid);
		client.deposit(accountUuid, amount);
		clientRepository.save(client);
	}

	/**
	 * withdraw money from a customer account<br>
	 * Checks that the client already exists <br>
	 * Checks that the account UUID is not empty or null <br>
	 * 
	 * @param identityDocNumber
	 * @param accountUuid
	 * @param amount
	 */
	public void withdraw(String identityDocNumber, String accountUuid, long amount) {

		Client client = findClient(identityDocNumber);
		checkClientExistance(client);
		checkAccountUuid(accountUuid);
		client.withdraw(accountUuid, amount);
		clientRepository.save(client);
	}

	/**
	 * Get the client account balance <br>
	 * Checks that the client already exists <br>
	 * Checks that the account UUID is not empty or null <br>
	 * 
	 * @param identityDocNumber
	 * @param accountUuid
	 * @return AccountBalance
	 */
	public AccountBalance getAccountBalance(String identityDocNumber, String accountUuid) {
		Client client = findClient(identityDocNumber);
		checkClientExistance(client);
		checkAccountUuid(accountUuid);
		return client.getAccountBalance(accountUuid);
	}

	/**
	 * Get the account transaction history <br>
	 * Checks that the client already exists <br>
	 * Checks that the account UUID is not empty or null <br>
	 * 
	 * @param identityDocNumber
	 * @param accountUuid
	 * @return List of account transactions
	 */
	public List<AccountTransaction> getAccountTransactionHistory(String identityDocNumber, String accountUuid) {
		Client client = findClient(identityDocNumber);
		checkClientExistance(client);
		checkAccountUuid(accountUuid);
		return client.getAccountTransactionHistory(accountUuid);
	}

	private void checkClientExistance(Client client) {
		if (client == null) {
			throw new BankException(ErrorCodes.E_0003, "The client does not exist");
		}
	}

	private void checkAccountUuid(String accountUuid) {
		if (StringUtils.isEmpty(accountUuid)) {
			throw new BankException(ErrorCodes.E_0005, "Account UUID must not be null or empty");
		}
	}

	private void copyData(Client c1, Client c2) {

		c2.setIdentityDocNumber(c1.getIdentityDocNumber());
		c2.setFirstName(c1.getFirstName());
		c2.setLastName(c1.getLastName());
		c2.setNationality(c1.getNationality());
		c2.setAddress(c1.getAddress());
		c2.setEmail(c1.getEmail());
		c2.setPhoneNumber(c1.getPhoneNumber());
	}
}
