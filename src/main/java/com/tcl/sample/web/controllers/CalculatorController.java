package com.tcl.sample.web.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.crud.sample.beans.remote.CalcResult;
import com.crud.sample.beans.remote.State;
import com.crud.sample.exception.remote.RemoteCallException;
import com.crud.sample.remote.service.AbstractRestService;
import com.crud.sample.remote.service.converters.MappingState2HttpMessageConverter;
import com.crud.sample.service.ICalculatorService;


@RestController
@RequestMapping("/calculator")
public class CalculatorController {
	private static final Logger logger = LoggerFactory.getLogger(GenericController.class);
	
	private ICalculatorService calculatorService;
	
	@Autowired	
	AbstractRestService restService;
	
	@Autowired
	CalculatorController(ICalculatorService calculatorService) {
		this.calculatorService = calculatorService;	
	}
	
	@RequestMapping(value="/add/{a}/{b}", method= RequestMethod.GET, headers="Accept=application/json" )	
	public String add(@PathVariable("a") int a, @PathVariable("b") int b) throws RemoteCallException, Exception {
		logger.info("Execution started - add" + Calendar.getInstance().getTime());

		CalcResult result = calculatorService.addNumbers(a, b); 
		
		logger.info("Execution Ends - add" + Calendar.getInstance().getTime());

		return String.valueOf(result.getResult());

	}
	
	@RequestMapping(value="/subtract/{a}/{b}", method= RequestMethod.GET, headers="Accept=application/json" )	
	public String subtract(@PathVariable("a") int a, @PathVariable("b") int b) throws RemoteCallException, Exception {
		logger.info("Execution started - subtract" + Calendar.getInstance().getTime());

		CalcResult result = calculatorService.subtractNumbers(a, b); 
		
		logger.info("Execution Ends - subtract" + Calendar.getInstance().getTime());

		return String.valueOf(result.getResult());

	}
	
	@RequestMapping(value="/multiply/{a}/{b}", method= RequestMethod.GET, headers="Accept=application/json" )	
	public String multiply(@PathVariable("a") int a, @PathVariable("b") int b) throws RemoteCallException, Exception {
		logger.info("Execution started - multiply" + Calendar.getInstance().getTime());

		CalcResult result = calculatorService.multiplyNumbers(a, b); 
		
		logger.info("Execution Ends - multiply" + Calendar.getInstance().getTime());

		return String.valueOf(result.getResult());

	}
	
	@RequestMapping(value="/divide/{a}/{b}", method= RequestMethod.GET, headers="Accept=application/json" )	
	public String divide(@PathVariable("a") int a, @PathVariable("b") int b) throws RemoteCallException, Exception {
		logger.info("Execution started - divide" + Calendar.getInstance().getTime());

		CalcResult result = calculatorService.divideNumbers(a, b); 
		
		logger.info("Execution Ends - divide" + Calendar.getInstance().getTime());

		return String.valueOf(result.getResult());

	}	

}
