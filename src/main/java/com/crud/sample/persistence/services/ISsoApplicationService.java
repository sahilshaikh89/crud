package com.crud.sample.persistence.services;

import java.util.List;

import com.crud.sample.beans.SsoApplications;
import com.crud.sample.exception.db.DbException;

public interface ISsoApplicationService {
	public SsoApplications getSsoApplicationById(long id) throws DbException;
	
	public List<SsoApplications> getAllSsoApplications() throws DbException;
}
