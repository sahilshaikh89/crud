package com.crud.sample.exception.db;

import com.crud.sample.exception.SampleCodeException;

public class ObjectNotFoundException extends DbException {
	String message;

	public ObjectNotFoundException(Throwable ex) {
		super(ex);
	}
	
	public ObjectNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public ObjectNotFoundException(String message, Throwable ex) {
		super(ex);
		this.message = message;
	}

	@Override
	public String toString() {		
		return this.message + super.toString();
	}
}
