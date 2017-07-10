package com.crud.sample.exception.validation;

import org.springframework.validation.BindingResult;

import com.crud.sample.exception.SampleCodeException;

public class ValidationException extends SampleCodeException {
	
	String message;
	final BindingResult result;
	
	public ValidationException(Throwable ex) {
		super(ex);
		result = null;
	}
	
	public ValidationException(String message, BindingResult result,Throwable ex) {
		super(ex);
		this.message = message;
		this.result = result;
	}
	
	public ValidationException(String message, BindingResult result) {		
		this.message = message;
		this.result = result;
	}

	
	@Override
	public String toString() {		
		return this.message + super.toString();
	}

	public BindingResult getResult() {
		return result;
	}
	

}
