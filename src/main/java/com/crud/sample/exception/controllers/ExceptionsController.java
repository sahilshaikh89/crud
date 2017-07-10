package com.crud.sample.exception.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crud.sample.exception.beas.ErrorResource;
import com.crud.sample.exception.beas.FieldErrorResource;
import com.crud.sample.exception.db.DbException;
import com.crud.sample.exception.remote.RemoteCallException;
import com.crud.sample.exception.validation.ValidationException;

@ControllerAdvice
public class ExceptionsController {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionsController.class);

	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResource> handleValidationException(ValidationException e, HttpServletResponse response){
		
		logger.debug("Exception Called for handling ValidationException");
		
		BindingResult result = e.getResult();	
		List<FieldErrorResource> fieldErrorResources = new ArrayList<FieldErrorResource>();

		List<FieldError> fieldErrors = result.getFieldErrors();
		for(FieldError fieldError : fieldErrors){
			FieldErrorResource fieldErrorResource = new FieldErrorResource();

			fieldErrorResource.setResource(fieldError.getObjectName());
			fieldErrorResource.setField(fieldError.getField());
			fieldErrorResource.setCode(fieldError.getCode());
			fieldErrorResource.setMessage(fieldError.getDefaultMessage());

			fieldErrorResources.add(fieldErrorResource);
		}

		ErrorResource error = new ErrorResource(HttpStatus.BAD_REQUEST, e.getMessage());
		error.setFieldErrors(fieldErrorResources);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		return new ResponseEntity<ErrorResource>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DbException.class)
	public ResponseEntity<ErrorResource> handleDbException(DbException e, HttpServletResponse response){
		
		ErrorResource error = new ErrorResource(HttpStatus.BAD_REQUEST, e.getMessage());		

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		e.printStackTrace();
		return new ResponseEntity<ErrorResource>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RemoteCallException.class)
	public ResponseEntity<ErrorResource> handleDbException(RemoteCallException e, HttpServletResponse response){
		
		ErrorResource error = new ErrorResource(HttpStatus.BAD_REQUEST, e.getMessage());		

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		return new ResponseEntity<ErrorResource>(error, HttpStatus.BAD_REQUEST);
	}
	

}


