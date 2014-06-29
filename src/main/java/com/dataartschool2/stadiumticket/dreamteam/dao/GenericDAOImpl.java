package com.dataartschool2.stadiumticket.dreamteam.dao;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.ParameterizedType;

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
	
	 public EntityClass makePersistent(EntityClass entity) {  
	        getSession().saveOrUpdate(entity);  
	        return entity;  
	    }  
	  
	    public void deletePersistent(EntityClass entity) {  
	        getSession().delete(entity);  
	    }  
	
	@SuppressWarnings("unchecked")
	public EntityClass findById(final Integer id) {
		return (EntityClass) getSession().load(getEntityClass(), id);
	}	
	
	@Override
	public List<EntityClass> findAll() {  
	    return findByCriteria();  
	}  
	protected List<EntityClass> findByCriteria(final Criterion... criterion) {
		System.out.println("criteria1:");
		return findByCriteria(-1, -1, criterion);
	}
	@SuppressWarnings("unchecked")
	protected List<EntityClass> findByCriteria(final int firstResult,
		final int maxResults, final Criterion... criterion) {			
		Criteria crit = getSession().createCriteria(getEntityClass());
		System.out.println("criteria2:");
		for (final Criterion c : criterion) {
			System.out.println(c.toString());
			crit.add(c);
		}

		if (firstResult > 0) {
			crit.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			crit.setMaxResults(maxResults);
		}

		final List<EntityClass> result = crit.list();
		return result;
	}
	  
	public int countAll() {
	  return countByCriteria();
	}
	
	protected int countByCriteria(Criterion... criterion) {
	  Criteria crit = getSession().createCriteria(getEntityClass());
	  crit.setProjection(Projections.rowCount());

	  for (final Criterion c : criterion) {
		  crit.add(c);
	  }
	  return (Integer) crit.list().get(0);
	}
}  




