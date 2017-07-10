package com.crud.sample.remote.service.converters;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.crud.sample.beans.remote.State;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;

public class MappingState2HttpMessageConverter extends AbstractGenericHttpMessageConverter<Object>{
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	ObjectMapper objectMapper;

	public MappingState2HttpMessageConverter() {
		this(Jackson2ObjectMapperBuilder.json().build());
	}

	public MappingState2HttpMessageConverter(ObjectMapper objectMapper) {
		super(MediaType.APPLICATION_JSON);
		this.objectMapper = objectMapper;
	}





	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		JavaType javaType = getJavaType(type, Object.class);
		return  readJavaType(javaType, inputMessage);
	}

	private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
		try {

			LinkedHashMap<String, Object> object = this.objectMapper.readValue(inputMessage.getBody(), javaType);

			object = (LinkedHashMap<String, Object>) object.get("RestResponse");
			List<Object> objects =  (List<Object>) object.get("result");

			List<State> states = new ArrayList();

			Gson gson = new Gson();
			for(Object state : objects){
			
				StateRemote stateObj = gson.fromJson(gson.toJson(state), StateRemote.class);

				State objToAdd = new State(stateObj.country, stateObj.name, stateObj.abbr, stateObj.largest_city, stateObj.capital);

				if(stateObj.area != null){
					Long area = new Long(stateObj.area.replace("SKM", ""));
					objToAdd.setArea(area);
				}
				states.add(objToAdd);
			}

			return states;
		}
		catch (IOException ex) {
			throw new HttpMessageNotReadableException("Could not read document: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected void writeInternal(Object t, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		System.out.println("WRITE INTERNAL");

	}


	protected JavaType getJavaType(Type type, Class<?> contextClass) {
		TypeFactory tf = this.objectMapper.getTypeFactory();
		// Conditional call because Jackson 2.7 does not support null contextClass anymore
		// TypeVariable resolution will not work with Jackson 2.7, see SPR-13853 for more details
		return (contextClass != null ? tf.constructType(type, contextClass) : tf.constructType(type));
	}


	public static class StateRemote {
		public String country;
		public String name;
		public String abbr;
		public String largest_city;
		public String capital;		
		public String area;
	}


	@Override
	protected boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}






}
