package com.ing.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class AccountBalance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2985485735842521626L;

	private double balance;
	private ZonedDateTime time;

	public AccountBalance(double balance, ZonedDateTime time) {
		super();
		this.balance = balance;
		this.time = time;
	}

	public AccountBalance() {
		super();

	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public ZonedDateTime getTime() {
		return time;
	}

	public void setTime(ZonedDateTime time) {
		this.time = time;
	}

}
