package com.api.hibernate.exception;

public class TranslationException extends Exception{

	public TranslationException(String message) {
		super(message);
	}
	
	public TranslationException(Throwable ex, String message) {
		super(message, ex);		
	}
	
}
