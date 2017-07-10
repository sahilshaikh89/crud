package com.crud.sample.service;

import com.crud.sample.beans.remote.CalcResult;
import com.crud.sample.exception.remote.RemoteCallException;

public interface ICalculatorService {
	public CalcResult addNumbers(int a, int b) throws RemoteCallException;
	
	public CalcResult subtractNumbers(int a, int b) throws RemoteCallException;
	
	public CalcResult multiplyNumbers(int a, int b) throws RemoteCallException;
	
	public CalcResult divideNumbers(int a, int b) throws RemoteCallException;
}
