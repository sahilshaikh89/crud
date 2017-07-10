package com.crud.sample.remote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.crud.sample.remote.service.converters.MappingState2HttpMessageConverter;

@Component
@Scope( value= BeanDefinition.SCOPE_PROTOTYPE )
public class AbstractRestService {

	@Autowired
	@Qualifier(value="restTemplate")
	RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	public <O, E extends AbstractGenericHttpMessageConverter> O get(String url, Class<?> clazz, Map<String, Object> urlVariables, E converter){

		//Clear the default converters
		List messageConverters = this.restTemplate.getMessageConverters();	

		List newConverter = new ArrayList();		
		newConverter.add(converter);			

		this.restTemplate.setMessageConverters(newConverter);			

		O result = (O) get(url, clazz, urlVariables);

		//restore old converters
		this.restTemplate.setMessageConverters(messageConverters);

		return result;
	}	

	public Object get(String url, Class<?> clazz, Map<String, Object> urlVariables){

		setProxy();

		Object result =  this.restTemplate.getForObject(url, clazz , urlVariables);
		//Do something here
		return result;
	}

	public Object exchange(String url, HttpMethod method, HttpEntity entity, Class<?> clazz){

		setProxy();

		Object result =  this.restTemplate.exchange(url, method, entity, clazz);
		//Do something here
		return result;
	}

	private void setProxy(){
		System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", "proxy.tcs.com");
		System.setProperty("http.proxyPort", "8080");			
		System.setProperty("http.proxyUser", "india\\XXX");
		System.setProperty("http.proxyPassword", "XXXX");
		System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");
	}
}
