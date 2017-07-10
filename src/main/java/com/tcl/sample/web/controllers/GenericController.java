package com.tcl.sample.web.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.api.hibernate.model.Query;
import com.api.hibernate.model.Restrictions;
import com.crud.sample.beans.validators.IValidator;
import com.crud.sample.exception.db.DbException;
import com.crud.sample.exception.db.ObjectNotFoundException;
import com.crud.sample.exception.validation.ValidationException;
import com.crud.sample.generic.dao.IGenericDao;
import com.google.gson.Gson;

public class GenericController<S extends IGenericDao<T>, T extends Serializable> {	

	private static final Logger logger = LoggerFactory.getLogger(GenericController.class);

	private S service;	
	private Class clazz;
	private IValidator<T> validator;

	GenericController(S service, Class<T> clazz){
		this.service = service;
		this.clazz = clazz;

		//Also Initialize the clazz in abstract hibernate dao
		this.service.setClazz(this.clazz);
	}

	GenericController(S service, Class<T> clazz, IValidator<T> validator){
		this.service = service;
		this.clazz = clazz;
		this.validator = validator;

		//Also Initialize the clazz in abstract hibernate dao
		this.service.setClazz(this.clazz);
	}

	@RequestMapping(value="/all", method= RequestMethod.GET, headers="Accept=application/json" )	
	@Transactional
	public List<T> getAll() throws DbException, Exception {
		logger.info("Execution started - getAll" + Calendar.getInstance().getTime());

		List<T> list = (List<T>) this.service.findAll();

		if(list == null)
			throw new ObjectNotFoundException("No objects found in records");

		for(T obj : list){

			Method[] methods = obj.getClass().getMethods();

			for (Method method:methods)
			{
				if(method.getName().startsWith("get"))
					Hibernate.initialize(method.invoke(obj));
			}
		}


		logger.info("Execution Ends - getAll" + Calendar.getInstance().getTime());

		return list;

	}

	@RequestMapping(value="/id/{id}", method= RequestMethod.GET, headers="Accept=application/json" )	
	@Transactional
	public T getById(@PathVariable("id") long id) throws DbException, Exception {
		logger.info("Execution started - getById" + Calendar.getInstance().getTime());

		T obj = (T) this.service.findById(id);


		if(obj == null)
			throw new ObjectNotFoundException("Object with id " + id + " Not found in database");

		Method[] methods = obj.getClass().getMethods();

		for (Method method:methods)
		{
			if(method.getName().startsWith("get"))
				Hibernate.initialize(method.invoke(obj));
		}

		logger.info("Execution Ends - getById" + Calendar.getInstance().getTime());

		return obj;
	}

	@RequestMapping(value="/create", method= RequestMethod.POST, headers="Accept=application/json" )	
	@Transactional
	public T create(@Valid @RequestBody T obj, BindingResult bindingResult) throws ValidationException, DbException ,Exception {
		logger.info("Execution started - create" + Calendar.getInstance().getTime());


		//Validate object
		if(validator != null)
			this.validator.validate(obj, bindingResult);
		
		if(bindingResult.hasErrors())
			throw new ValidationException("Validation Failed", bindingResult);
		

		Double newId = (Double) this.service.create(obj);

		//Get the newly created object
		T newObj = (T) this.service.findById(newId);


		if(obj == null)
			throw new ObjectNotFoundException("Object with id " + newId + " Not found in database after persist. Seems save failed");

		Method[] methods = obj.getClass().getMethods();

		for (Method method:methods)
		{
			if(method.getName().startsWith("get"))
				Hibernate.initialize(method.invoke(obj));
		}

		logger.info("Execution Ends - create" + Calendar.getInstance().getTime());

		return newObj;
	}
	@RequestMapping(value="/delete/{id}", method= RequestMethod.PUT, headers="Accept=application/json" )	
	@Transactional
	public boolean delete(@PathVariable("id") long id) throws DbException {
		logger.info("Execution started - delete" + Calendar.getInstance().getTime());

		this.service.deleteById(id);	
		//In case of failure, DbException will be called and catch uo by @ExceptionController

		logger.info("Execution Ends - delete" + Calendar.getInstance().getTime());

		return true;
	}

	//Please refrain from usinf POST for update. Use Patch instead
	@RequestMapping(value="/update", method={ RequestMethod.PATCH, RequestMethod.POST}, headers="Accept=application/json" )	
	@Transactional
	public T delete(@Valid @RequestBody T obj, BindingResult bindingResult) throws ValidationException, DbException, Exception {
		logger.info("Execution started - update" + Calendar.getInstance().getTime());

		//Validate the bean
		if(validator != null)
			this.validator.validate(obj, bindingResult);
		
		if(bindingResult.hasErrors())
			throw new ValidationException("Validation Failed", bindingResult);


		T updatedObj = this.service.update(obj);	

		this.initHibernateLazyLoad(updatedObj);

		logger.info("Execution Ends - update" + Calendar.getInstance().getTime());

		return updatedObj;
	}
	
	@RequestMapping(value="/restrict/{queryId}/{map}", method= RequestMethod.GET, headers="Accept=application/json" )	
	@Transactional
	public List<Object> getByRestriction(@PathVariable("queryId") String queryId, @PathVariable("map") String map) throws DbException, Exception {
		logger.info("Execution started - getByRestriction" + Calendar.getInstance().getTime());
		
		Restrictions restrictions = null;
		Map<String, Object> props = null;
		
		try {
			//Read the json file
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("/com/tcl/query/" + queryId + ".json");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			StringBuilder json = new StringBuilder();
			String line;
			while( (line = reader.readLine()) != null ){
				json.append(line);
				line = null;
			}
			
			Gson gson = new Gson();
			
			restrictions = gson.fromJson(json.toString(), Restrictions.class);
					
			props = gson.fromJson(map, Map.class);
		
			reader.close();
			in.close();
		} catch (Exception e) {
			throw new Exception("Unable to handle input properly", e);
		}
		
		List<Object> list = (List<Object>) this.service.findByRestriction(restrictions, props);
		
		if(list == null)
			throw new ObjectNotFoundException("No objects found in records");

		for(Object obj : list){
			
			if(!this.clazz.isAssignableFrom(obj.getClass()) ){
				break;
			}
			
			Method[] methods = obj.getClass().getMethods();

			for (Method method:methods)
			{
				if(method.getName().startsWith("get"))
					Hibernate.initialize(method.invoke(obj));
			}
		}

		logger.info("Execution Ends - getByRestriction" + Calendar.getInstance().getTime());

		return list;
	}

	@RequestMapping(value="/query/{queryId}", method= RequestMethod.POST, headers="Accept=application/json" )	
	@Transactional
	public List<Object> getByQuery(@PathVariable("queryId") String queryId, @RequestBody String map) throws DbException, Exception {
		logger.info("Execution started - getByRestriction" + Calendar.getInstance().getTime());
		
		Query query = null;
		Map<String, Object> props = null;
		
		try {
			//Read the json file
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("/com/tcl/query/hql/" + queryId + ".json");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			StringBuilder json = new StringBuilder();
			String line;
			while( (line = reader.readLine()) != null ){
				json.append(line);
				line = null;
			}
			
			Gson gson = new Gson();
			
			query = gson.fromJson(json.toString(), Query.class);
			
			props = gson.fromJson(map, Map.class);			
						
			reader.close();
			in.close();
		} catch (Exception e) {
			throw new Exception("Unable to handle input properly", e);
		}
		
		List<Object> list = (List<Object>) this.service.findByQuery(query, props);
		
		if(list == null)
			throw new ObjectNotFoundException("No objects found in records");

		for(Object obj : list){
			
			if(!this.clazz.isAssignableFrom(obj.getClass()) ){
				break;
			}
			
			Method[] methods = obj.getClass().getMethods();

			for (Method method:methods)
			{
				if(method.getName().startsWith("get"))
					Hibernate.initialize(method.invoke(obj));
			}
		}

		logger.info("Execution Ends - getByRestriction" + Calendar.getInstance().getTime());

		return list;
	}

	
	//Generic Reflected method to lazily initialize Hibernate Lazy Mode Objects. Please use FetchMode=JOIN for 
	//sake of performance
	private void initHibernateLazyLoad(T obj) throws Exception{
		Method[] methods = obj.getClass().getMethods();

		for (Method method:methods)
		{
			if(method.getName().startsWith("get"))
				Hibernate.initialize(method.invoke(obj));
		}
	}
}
