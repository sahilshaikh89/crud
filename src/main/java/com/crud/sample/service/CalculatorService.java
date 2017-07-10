package com.crud.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.crud.sample.beans.remote.CalcResult;
import com.crud.sample.exception.remote.RemoteCallException;
import com.crud.sample.remote.service.calc.CalculatorRemoteService;

@Service("calculatorService")
public class CalculatorService implements ICalculatorService{
	
	@Autowired
	private CalculatorRemoteService calculatorRemoteService;
	
	public CalcResult addNumbers(int a, int b) throws RemoteCallException{		
		return calculatorRemoteService.add(a, b);		
	} 
	
	public CalcResult subtractNumbers(int a, int b) throws RemoteCallException{		
		return calculatorRemoteService.subtract(a, b);		
	} 
	
	public CalcResult multiplyNumbers(int a, int b) throws RemoteCallException{		
		return calculatorRemoteService.multiply(a, b);		
	} 
	
	public CalcResult divideNumbers(int a, int b) throws RemoteCallException{		
		return calculatorRemoteService.divide(a, b);		
	} 
	

}
