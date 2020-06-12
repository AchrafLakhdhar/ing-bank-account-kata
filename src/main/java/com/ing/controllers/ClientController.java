package com.ing.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ing.entities.Account;
import com.ing.entities.AccountBalance;
import com.ing.entities.AccountTransaction;
import com.ing.entities.Client;
import com.ing.services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Client addClient(@RequestBody Client client) {
		return clientService.addClient(client);

	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public Client updateClient(@RequestBody Client client) {
		return clientService.updateClient(client);

	}

	@RequestMapping(value = "/{identityDocNumber}", method = RequestMethod.GET, produces = "application/json")
	public Client findClient(@PathVariable String identityDocNumber) {
		return clientService.findClient(identityDocNumber);

	}

	@RequestMapping(value = "/{identityDocNumber}", method = RequestMethod.DELETE)
	public void deleteClient(@PathVariable String identityDocNumber) {
		clientService.deleteClient(identityDocNumber);

	}

	@RequestMapping(value = "/account/{identityDocNumber}", method = RequestMethod.PUT, produces = "application/json")
	public List<Account> addAccount(@PathVariable String identityDocNumber) {
		return clientService.addAccount(identityDocNumber);
	}

	@RequestMapping(value = "/account", method = RequestMethod.DELETE, produces = "application/json")
	public List<Account> removeAccount(@RequestParam String identityDocNumber, @RequestParam String accountUuid) {
		return clientService.removeAccount(identityDocNumber, accountUuid);
	}

	@RequestMapping(value = "/accounts/{identityDocNumber}", method = RequestMethod.GET, produces = "application/json")
	public List<Account> findAccounts(@PathVariable String identityDocNumber) {
		return clientService.findAccounts(identityDocNumber);

	}

	@RequestMapping(value = "/account/deposit", method = RequestMethod.PUT)
	public void deposit(String identityDocNumber, String accountUuid, long amount) {
		clientService.deposit(identityDocNumber, accountUuid, amount);

	}

	@RequestMapping(value = "/account/withdraw", method = RequestMethod.PUT)
	public void withdraw(String identityDocNumber, String accountUuid, long amount) {
		clientService.withdraw(identityDocNumber, accountUuid, amount);

	}

	@RequestMapping(value = "/account/balance", method = RequestMethod.GET, produces = "application/json")
	public AccountBalance getAccountBalance(String identityDocNumber, String accountUuid) {
		return clientService.getAccountBalance(identityDocNumber, accountUuid);

	}

	@RequestMapping(value = "/account/history", method = RequestMethod.GET, produces = "application/json")
	public List<AccountTransaction> getAccountTransactionHistory(String identityDocNumber, String accountUuid) {
		return clientService.getAccountTransactionHistory(identityDocNumber, accountUuid);
	}

}
