package com.crud.sample.abastrac.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.api.hibernate.translator.QueryTranslator;
import com.api.hibernate.translator.RestrictionTranslator;
import com.crud.sample.exception.db.DbException;
import com.crud.sample.query.transformer.QuerySsoAppTransformer;

public abstract class AbstractHibernateDao<T extends Serializable> {
	private Class<T> clazz;
	private static final Logger log = LoggerFactory.getLogger(AbstractHibernateDao.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	public void setClazz(Class<T> clazz){
		this.clazz = clazz;
	}
	
	public T findById( double id ) throws DbException{
		log.debug("Trying to fetch entity " + this.clazz.getName() + " with id : " + id);
		try {
			T result = (T) getCurrentSession().get( this.clazz, id );
			log.debug("Fetch successful");
			
			return result;
		} catch (Exception re) {
			log.error("Error fetching entity with Id : " + id, re);
			throw new DbException("Error fetching entity with Id : " + id + " | ", re);
		}		
	}
	
	public List<T> findAll() throws DbException{
		log.debug("Fetching all the instances of entity " + this.clazz.getName());
		try {
			
			List<T> result = getCurrentSession().createQuery( "from " + this.clazz.getName()).list();
			log.debug("Fetch successful");
			
			return result;
			
		} catch (Exception re) {
			log.error("Error while fetching all instances", re);
			throw new DbException("Error while fetching all instances | ", re);
		}
		
	}
	
	public Object create( T entity ) throws Exception{
		log.debug("Persisting object of nature " + this.clazz.getName());
		try {
			getCurrentSession().persist(entity);
			
			Object id = entity.getClass().getMethod("getId").invoke(entity);
			log.debug("Persist Successful");
			
			return id;
		} catch (DbException re) {
			
			log.error("Persist failed", re);
			throw new DbException("Persist Failed.", re);			
		} catch (Exception e) {
			
			String errMsg = "Persist failed duto to reflect call to getId. Please make sure the Hibernate Bean has a getter method getId | ";
			
			log.error(errMsg, e);
			throw new DbException(errMsg, e);
		}
		
	}
	
	public T update( T entity ) throws DbException{
		log.debug("Trying to update/merge entity " + this.clazz.getName());
		
		try {
			T result = (T) getCurrentSession().merge(entity);
			log.debug("Update successful");
			
			return result;
		} catch (Exception re) {
			String errMsg = "Update failed. | ";
			
			log.error(errMsg, re);
			throw new DbException(errMsg, re);
		}
	}
	
	public void delete( T entity ) throws DbException{
		log.debug("Deleting entity of nature " + this.clazz.getName());
		try {
			getCurrentSession().delete(entity);
			log.debug("Delete successful");
		} catch (Exception re) {
			String errMsg = "Delete failed. | ";
			
			log.error(errMsg, re);
			throw new DbException(errMsg, re);
		}
		
	}
	
	public void deleteById( long id ) throws DbException{
		log.debug("Fetching instance of with id " + id);
		try {
			T entity = findById(id);
			log.debug("Entry found with id : " + id);
			
			delete(entity);
			
		} catch (Exception re) {
			String errMsg = "Delete instance with id failed : " + id + " | ";
			
			log.error(errMsg, re );
			throw new DbException(errMsg, re);
		}
	}
	
	public Object findByRestriction(  com.api.hibernate.model.Restrictions restrictions, Map<String, Object> props ) throws DbException{
		log.debug("Trying to fetch entity based on restriction");
		try {
			Criteria cr = getCurrentSession().createCriteria(this.clazz, "_this");			
				
			cr = RestrictionTranslator.translate(restrictions, props, cr);			
			
			Object result = cr.list();
			log.debug("Fetch successful");
			
			return result;
		} catch (Exception re) {
			log.error("Error fetching entity with Id : ", re);
			throw new DbException("Error fetching entity with restriction" + " | ", re);
		}		
	}
	
	public Object findByQuery( com.api.hibernate.model.Query query, Map<String, Object> props ) throws DbException{
		log.debug("Trying to fetch entity based on query");
		try {
			Query qry = QueryTranslator.translateQuery(query, props, getCurrentSession()); 
					//getCurrentSession().createQuery("SELECT app.name as APP_NAME, cat.name FROM SsoApplications app LEFT JOIN app.ssoCategories cat WHERE app.id>:appId")
					//.setResultTransformer(new QuerySsoAppTransformer());
			
			//qry.setParameter("appId", 1.0);
			
			
			Object result = qry.list();
			log.debug("Fetch successful");
			
			return result;
		} catch (Exception re) {
			log.error("Error fetching entity with Id : ", re);
			throw new DbException("Error fetching entity with restriction" + " | ", re);
		}		
	}
	
	protected final Session getCurrentSession() throws DbException{
		return sessionFactory.getCurrentSession();
		
	}	
}
