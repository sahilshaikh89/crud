package com.crud.sample.generic.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.api.hibernate.model.Query;
import com.api.hibernate.model.Restrictions;
import com.crud.sample.exception.db.DbException;

public interface IGenericDao<T extends Serializable> {
	public void setClazz(Class<T> clazz);
	
	T findById(final double id) throws DbException;
	
	List<T> findAll() throws DbException;
	
	Object create(final T entity) throws Exception;
	
	T update(final T entity) throws DbException;
	
	void delete(final T entity) throws DbException;
	
	void deleteById(final long entityId) throws DbException;
	
	Object findByRestriction( Restrictions restrictions, Map<String, Object> props ) throws DbException;
	
	Object findByQuery(Query query, Map<String, Object> props ) throws DbException;
}
