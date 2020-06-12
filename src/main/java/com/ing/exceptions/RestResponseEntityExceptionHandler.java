package com.ing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { BankException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		if (ex instanceof BankException) {
			BankException bankException = (BankException) ex;
			BankErrorResponse bankErrorResponse = new BankErrorResponse(bankException.getErrorCode(),
					bankException.getMessage());
			return new ResponseEntity<Object>(bankErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(new BankErrorResponse("E_0000", ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
