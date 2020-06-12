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

@Entity(name = "account")
@Table(name = "account")
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3937668566666809464L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	@JsonIgnore
	private long id;

	@Column(unique = true, nullable = false)
	private String uuid;

	@Column(nullable = false)
	private double balance;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="account_id")
	@JsonIgnore
	private List<AccountTransaction> transactions = new ArrayList<>();

	public Account(String uuid) {
		super();
		this.uuid = uuid;
	}
	
	public Account() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public double getBalance() {
		return balance;
	}

	public List<AccountTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<AccountTransaction> transactions) {
		this.transactions = transactions;
	}

	/**
	 * Deposit an amount of money to the account<br>
	 * Add a new transaction which has DEPOSIT as operation<br>
	 * The amount must be superior of €0.01
	 * @param amount
	 */
	public void deposit(double amount) {
		if (amount < 0.01) {
			throw new BankException(ErrorCodes.E_0006, "The amount must be superior to €0.01");
		}
		this.balance += amount;
		AccountTransaction transaction = new AccountTransaction(amount, Operation.DEPOSIT, ZonedDateTime.now());
		transactions.add(transaction);
	}

	/**
	 * Withdraw an amount of money from the account<br>
	 * Add a new transaction which has WITHDRAW as operation<br>
	 * The account balance must be greater than the account balance <br>
	 * The withdrawal amount must be superior to €0
	 * @param amount
	 */
	public void withdraw(double amount) {
		if (this.balance < amount) {
			throw new BankException(ErrorCodes.E_0007, "The withdrawal amount is greater than your account balance");
		}
		if (amount < 0) {
			throw new BankException(ErrorCodes.E_0008, "The withdrawal amount must be superior to €0");
		}
		this.balance -= amount;
		AccountTransaction transaction = new AccountTransaction(amount, Operation.WITHDRAW, ZonedDateTime.now());
		transactions.add(transaction);
	}

}
