package com.ing.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ing.exceptions.BankException;
import com.ing.exceptions.ErrorCodes;

@Entity(name = "client")
@Table(name = "client")
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6805379347726185966L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	@JsonIgnore
	private long id;

	@Column(unique = true, nullable = false)
	private String identityDocNumber;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String nationality;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String phoneNumber;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "client_id")
	@JsonIgnore
	private List<Account> accounts = new ArrayList<>();

	public String getIdentityDocNumber() {
		return identityDocNumber;
	}

	public void setIdentityDocNumber(String identityDocNumber) {
		this.identityDocNumber = identityDocNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Account> getAccounts() {
		return new ArrayList<Account>(accounts);
	}

	public List<Account> addAccount(Account account) {
		accounts.add(account);
		return new ArrayList<Account>(accounts);
	}

	public List<Account> removeAccount(String uuid) {
		Account account = null;
		for (Account acc : accounts) {
			if (acc.getUuid().equals(uuid)) {
				account = acc;
				break;
			}
		}
		if (account != null) {
			accounts.remove(account);
		}
		return accounts;
	}

	/**
	 * Delegate to the account the responsibility of the deposit
	 * 
	 * @param accountUuid
	 * @param amount
	 */
	public void deposit(String accountUuid, double amount) {
		accounts.stream().filter(acc -> acc.getUuid().equals(accountUuid)).forEach(acc -> acc.deposit(amount));
	}

	/**
	 * Delegate to the account the responsibility of the withdraw
	 * 
	 * @param accountUuid
	 * @param amount
	 */
	public void withdraw(String accountUuid, long amount) {
		accounts.stream().filter(acc -> acc.getUuid().equals(accountUuid)).forEach(acc -> acc.withdraw(amount));
	}

	/**
	 * Get the account balance
	 * 
	 * @param accountUuid
	 * @return The account balance
	 */
	public AccountBalance getAccountBalance(String accountUuid) {
		AccountBalance accountBalance = null;
		for (Account acc : accounts) {
			if (acc.getUuid().equals(accountUuid)) {
				accountBalance = new AccountBalance(acc.getBalance(), ZonedDateTime.now());
				break;
			}
		}
		if (accountBalance == null) {
			throw new BankException(ErrorCodes.E_0009, "No account exists with the provided uuid");
		}
		return accountBalance;

	}

	/**
	 * Get the account transactions history
	 * 
	 * @param accountUuid
	 * @return The list of the account transactions history
	 */
	public List<AccountTransaction> getAccountTransactionHistory(String accountUuid) {

		for (Account acc : accounts) {
			if (acc.getUuid().equals(accountUuid)) {
				return acc.getTransactions();
			}
		}

		throw new BankException(ErrorCodes.E_0009, "No account exists with the provided uuid");

	}

}
