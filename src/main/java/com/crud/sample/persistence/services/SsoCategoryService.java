package com.crud.sample.persistence.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.sample.beans.SsoCategories;
import com.crud.sample.exception.db.DbException;
import com.crud.sample.generic.dao.GenericDao;
import com.crud.sample.generic.dao.IGenericDao;


@Service("ssoCategoryService")
public class SsoCategoryService implements ISsoCategoryService{
	
	
	IGenericDao<SsoCategories> genericSsoCategoryDao;
	
	 @Autowired
	   public void setGenericDao( GenericDao< SsoCategories > genericSsoCategoryDao ){
	      this.genericSsoCategoryDao = genericSsoCategoryDao;
	      this.genericSsoCategoryDao.setClazz(SsoCategories.class);
	   }
	
	@Override
	@Transactional
	public SsoCategories getSsoApplicationById(long id) throws DbException{
		return genericSsoCategoryDao.findById(id);
	}
	
	@Override
	@Transactional
	public List<SsoCategories> getAllSsoApplications() throws DbException{
		List<SsoCategories> ssoApplications = genericSsoCategoryDao.findAll();
		
		for(SsoCategories app : ssoApplications)
			Hibernate.initialize(app.getSsoApplicationses());
		
		return ssoApplications;
	}
	
}
