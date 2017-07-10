package com.crud.sample.exception.remote;

import com.crud.sample.exception.SampleCodeException;

public class RemoteCallException extends SampleCodeException{
	String message;

	public RemoteCallException(Throwable ex) {
		super(ex);
	}

	public RemoteCallException(String message, Throwable ex) {
		super(ex);
		this.message = message;
	}

	@Override
	public String toString() {		
		return this.message + super.toString();
	}
}
