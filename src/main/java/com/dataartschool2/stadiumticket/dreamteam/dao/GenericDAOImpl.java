package com.dataartschool2.stadiumticket.dreamteam.dao;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.ParameterizedType;

public abstract class GenericDAOImpl <EC, ID extends Serializable> implements GenericDAO<EC, ID>{

	@Autowired
 	private SessionFactory sessionFactory;
	private Class<EC> entityClass;
	
	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.setEntityClass((Class<EC>) ((ParameterizedType) 
				getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EC> findAll() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(getEntityClass());
		return  (List<EC>) criteria.list();
		}

	@Override
	public void sysoutMessage(String message) {
		System.out.println(message);
		
	}

	public Class<EC> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<EC> entityClass) {
		this.entityClass = entityClass;
	}
	
	

}
