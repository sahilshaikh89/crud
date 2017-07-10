package com.crud.sample.persistence.services;

import java.util.List;

import com.crud.sample.beans.SsoCategories;
import com.crud.sample.exception.db.DbException;

public interface ISsoCategoryService {
	public SsoCategories getSsoApplicationById(long id) throws DbException;
	
	public List<SsoCategories> getAllSsoApplications() throws DbException;
}
