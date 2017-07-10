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
import com.crud.sample.remote.service.converters.MappingState2HttpMessageConverter.StateRemote;
import com.crud.sample.remote.service.states.IStateRemoteService;
import com.crud.sample.remote.service.states.StateRemoteService;
import com.crud.sample.service.ICalculatorService;


@RestController
@RequestMapping("/rest")
public class RestTemplateController {
	private static final Logger logger = LoggerFactory.getLogger(GenericController.class);
	
	
	@Autowired	
	IStateRemoteService stateRemoteService;
		
	@RequestMapping(value="/states", method= RequestMethod.GET, headers="Accept=application/json" )	
	public Object gteStates() throws RemoteCallException, Exception {
		logger.info("Execution started - states" + Calendar.getInstance().getTime());
				
		String url = "http://services.groupkt.com/state/get/{COUNTRY}/all";
		
		Map<String, Object> urlVariables = new HashMap();		
		urlVariables.put("COUNTRY", "IND");
				
		List<State> result = this.stateRemoteService.getStatesOfIndia(url, Object.class, urlVariables, new MappingState2HttpMessageConverter());
		
		logger.info("Execution Ends - states" + Calendar.getInstance().getTime());

		return result;

	}
	@RequestMapping(value="/add/{a}/{b}", method= RequestMethod.GET, headers="Accept=application/json" )	
	public Object rest(@PathVariable("a") int a, @PathVariable("b") int b) throws RemoteCallException, Exception {
		logger.info("Execution started - divide" + Calendar.getInstance().getTime());
		
				
		String url = "http://localhost:8081/SampleCode/calculator/add/"+a + "/" + b;
		
			
		Object result = this.stateRemoteService.addNumbers(url, Object.class);
		
		logger.info("Execution Ends - divide" + Calendar.getInstance().getTime());

		return result;

	}
	
}
