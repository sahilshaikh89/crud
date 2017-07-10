package com.tcl.sample.web.controllers;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.sample.beans.SsoApplications;
import com.crud.sample.beans.SsoCategories;
import com.crud.sample.exception.SampleCodeException;
import com.crud.sample.generic.dao.GenericDao;
import com.crud.sample.generic.dao.IGenericDao;
import com.crud.sample.persistence.services.SsoApplicationsService;
import com.crud.sample.persistence.services.SsoCategoryService;


@RestController
@RequestMapping("/applications")
public class SsoApplicationController extends GenericController<IGenericDao<SsoApplications>, SsoApplications>{
	
	@Autowired
	SsoApplicationController(GenericDao<SsoApplications> service) {
		super(service, SsoApplications.class);		
	}
}
