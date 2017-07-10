package com.crud.sample.remote.service.states;

import java.util.List;
import java.util.Map;

import org.springframework.http.converter.AbstractGenericHttpMessageConverter;

import com.crud.sample.beans.remote.CalcResult;
import com.crud.sample.beans.remote.State;

public interface IStateRemoteService {
	
	public List<State> getStatesOfIndia(String url, Class<?> clazz, Map<String, Object> urlVariables, AbstractGenericHttpMessageConverter converter);

	public Object addNumbers(String url, Class<?> clazz);

}
