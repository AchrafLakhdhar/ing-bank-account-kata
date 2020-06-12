package com.ing.exceptions;

/**
 * A custom exception used to handle application errors
 * 
 * @author achraf
 *
 */
public class BankException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8278037721180747076L;

	private String errorCode;

	public BankException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;

	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
