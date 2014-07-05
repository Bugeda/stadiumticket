package com.dataartschool2.stadiumticket.dreamteam.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class GenericDAOImpl <EntityClass> implements GenericDAO<EntityClass>{

	@Autowired
 	private SessionFactory sessionFactory;
	private Class<EntityClass> entityClass;
	private Session session;	
		
	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.setEntityClass((Class<EntityClass>) ((ParameterizedType) 
				getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
	
	private Class<EntityClass> getEntityClass() {
		return entityClass;
	}

	private void setEntityClass(Class<EntityClass> entityClass) {
		this.entityClass = entityClass;
	}
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private void setSession(Session session) {
		this.session = session;
	}
	//CRUD
	public EntityClass updateEntity(EntityClass entity) {
	    getSession().saveOrUpdate(entity);
		return entity;
	}  
	//

	public void deleteEntity(EntityClass entity) { 
		 try {
			 getSession().delete(entity);
		 }
		 catch (Exception e) { 	    	
		    //	System.out.println(e.getMessage());
		    }
	}  
	
	@SuppressWarnings("unchecked")
	public EntityClass findById(final Integer id) {
		EntityClass result = null;

	    try {
	    	//result = (EntityClass) getSession().load(getEntityClass(), id);	
	    	result = (EntityClass) getSession().get(getEntityClass(), id);	    	 
	    }
	    catch (Exception e) { 	    	
	    	JOptionPane.showMessageDialog(null,	"incorrect request", "Error",  JOptionPane.ERROR_MESSAGE);
			return null;
	    }

        return (result);
	}	
	
	@Override
	public List<EntityClass> findAll() {  
	    return findByCriteria();  
	}  
	protected List<EntityClass> findByCriteria(final Criterion... criterion) {
		System.out.println(criterion.toString());
		return findByCriteria(-1, -1, false, null, criterion);
	}
	@SuppressWarnings("unchecked")
	protected List<EntityClass> findByCriteria(final int firstResult,
		final int maxResults, final boolean order, final Order addorder, final Criterion... criterion ) {
		List<EntityClass> result = null;
	    try {
	    	Criteria crit = getSession().createCriteria(getEntityClass());

	    	for (final Criterion c : criterion) {
	    		crit.add(c);
	    	}

	    	if (firstResult > 0) {
	    		crit.setFirstResult(firstResult);
	    	}

	    	if (maxResults > 0) {
	    		crit.setMaxResults(maxResults);
	    	}
	    	if (order)  {
	    		crit.addOrder(addorder);
	    	}
	    	System.out.println(crit.toString());
	    	result = crit.list();
	    }
	    catch (Exception e) {
	    //	System.out.println(e.getMessage());	    	
	    }		    
		return result;
	}
	  
	public int countAll() {
	  return countByCriteria();
	}
	
	protected int countByCriteria(Criterion... criterion) {
		Integer result = null;
	    try {
	    	Criteria crit = getSession().createCriteria(getEntityClass());
	  	  	crit.setProjection(Projections.rowCount());

	  	  	for (final Criterion c : criterion) {
	  	  		crit.add(c);
	  	  	}
	  	  	result = (Integer) crit.list().get(0);
	    }
	    catch (Exception e) {
	    //	System.out.println(e.getMessage());
	    }		   
		return result;
	}
	
}




