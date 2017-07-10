package com.crud.sample.persistence.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.sample.beans.SsoApplications;
import com.crud.sample.exception.db.DbException;
import com.crud.sample.generic.dao.GenericDao;
import com.crud.sample.generic.dao.IGenericDao;


@Service("ssoApplicationsService")
public class SsoApplicationsService implements ISsoApplicationService{
	
	
	IGenericDao<SsoApplications> genericSsoApplicationsDao;
	
	 @Autowired
	   public void setGenericDao( GenericDao< SsoApplications > genericSsoApplicationsDao ){
	      this.genericSsoApplicationsDao = genericSsoApplicationsDao;
	      this.genericSsoApplicationsDao.setClazz(SsoApplications.class);
	   }
	
	@Override
	@Transactional
	public SsoApplications getSsoApplicationById(long id) throws DbException{
		return genericSsoApplicationsDao.findById(id);
	}
	
	@Override
	@Transactional
	public List<SsoApplications> getAllSsoApplications() throws DbException{
		List<SsoApplications> ssoApplications = genericSsoApplicationsDao.findAll();
		
		for(SsoApplications app : ssoApplications)
			app.getSsoCategories();
		
		return ssoApplications;
	}
	
}
