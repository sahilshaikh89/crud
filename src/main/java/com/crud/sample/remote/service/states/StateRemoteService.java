package com.crud.sample.remote.service.states;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.stereotype.Service;

import com.crud.sample.beans.remote.CalcResult;
import com.crud.sample.beans.remote.State;
import com.crud.sample.remote.service.AbstractRestService;

@Service
public class StateRemoteService implements IStateRemoteService{

	@Autowired
	AbstractRestService restService;

	@Override
	public List<State> getStatesOfIndia(String url, Class<?> clazz, Map<String, Object> urlVariables, AbstractGenericHttpMessageConverter converter){

		//CALL TO BE MADE USING CUSTOM CONVERTER 
		return this.restService.get(url, clazz, urlVariables, converter);

	}

	@Override
	public Object addNumbers(String url, Class<?> clazz){
		
		HttpHeaders requestHeaders=new HttpHeaders();	    
	    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity entity = new HttpEntity(null, requestHeaders);
		
		
		return this.restService.exchange(url, HttpMethod.GET, entity, clazz);
	}

}
