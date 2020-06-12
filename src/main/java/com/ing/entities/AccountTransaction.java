package com.ing.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "accounttransaction")
public class AccountTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9086140278438276602L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	@JsonIgnore
	private long id;
	private double amount;
	private Operation operation;
	private ZonedDateTime zonedDateTime;

	public AccountTransaction(double amount, Operation operation, ZonedDateTime zonedDateTime) {
		super();
		this.amount = amount;
		this.operation = operation;
		this.zonedDateTime = zonedDateTime;
	}
	
	public AccountTransaction() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public ZonedDateTime getZonedDateTime() {
		return zonedDateTime;
	}

	public void setZonedDateTime(ZonedDateTime zonedDateTime) {
		this.zonedDateTime = zonedDateTime;
	}

}
