package com.ing.services;

import java.util.List;

import com.ing.entities.Account;
import com.ing.entities.AccountBalance;
import com.ing.entities.AccountTransaction;
import com.ing.entities.Client;

public interface ClientService {

	public Client addClient(Client client);

	public Client updateClient(Client client);

	public Client findClient(String identityDocNumber);

	public void deleteClient(String identityDocNumber);

	public void deleteClient(Client client);

	public List<Account> addAccount(String identityDocNumber);

	public List<Account> removeAccount(String identityDocNumber, String accountUuid);

	public List<Account> findAccounts(String identityDocNumber);

	public void deposit(String identityDocNumber, String accountUuid, double amount);

	public void withdraw(String identityDocNumber, String accountUuid, long amount);

	public AccountBalance getAccountBalance(String identityDocNumber, String accountUuid);

	public List<AccountTransaction> getAccountTransactionHistory(String identityDocNumber, String accountUuid);

	}
