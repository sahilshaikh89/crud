package com.crud.sample.exception.db;

import com.crud.sample.exception.SampleCodeException;

public class DbException extends SampleCodeException{

	String message;
	
	public DbException(Throwable ex) {
		super(ex);
	}
	public DbException(String message) {
		this.message = message;
	}
	
	public DbException(String message, Throwable ex) {
		super(ex);
		this.message = message;
	}
	
	@Override
	public String toString() {		
		return this.message + super.toString();
	}
	
}
