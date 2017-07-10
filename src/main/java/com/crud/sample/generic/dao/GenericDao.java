package com.crud.sample.generic.dao;

import java.io.Serializable;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.crud.sample.abastrac.dao.AbstractHibernateDao;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class GenericDao<T extends Serializable> extends AbstractHibernateDao<T> implements IGenericDao<T> {

}
