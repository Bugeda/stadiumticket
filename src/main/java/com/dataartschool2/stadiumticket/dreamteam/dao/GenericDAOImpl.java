package com.dataartschool2.stadiumticket.dreamteam.dao;

//import com.dataartschool2.stadiumticket.dreamteam.dao.GenericDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.internal.lang.annotation.ajcDeclareEoW;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import sun.awt.windows.awtLocalization;

/* 
 * @param <T>
 *            The persistent type
 * @param <ID>
 *            The primary key type
 */

public abstract class GenericDAOImpl <T, ID extends  Serializable> 
		implements GenericDAO<T, ID> {

	private Class<T> persistentClass;
	private EntityManager entityManager;

    @Resource(name = "sessionFactory")
    private SessionFactoryImpl localSessionFactoryBean;

    private Session getSession(){
        return SessionFactoryUtils.getSession(localSessionFactoryBean, true);


    }

	@Override
	public int countAll() {
		return countByCriteria();
	}

	@Override
	public int countByExample(final T exampleInstance) {
		Session session = getSession();
		Criteria crit = session.createCriteria(getEntityClass());
		crit.setProjection(Projections.rowCount());
		crit.add(Example.create(exampleInstance));

		return (Integer) crit.list().get(0);
	}

	@Override
	public List<T> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByExample(final T exampleInstance) {
		Session session = getSession();
		Criteria crit = session.createCriteria(getEntityClass());
		final List<T> result = crit.list();  
		return result; 
	}

	@Override
	public T findById(final ID id) {
        final T result  = (T) getSession().get(persistentClass, id);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(final String name, Object... params) {
        Query query = getSession().getNamedQuery(name);


		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}

		final List<T> result = (List<T>) query.list();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQueryAndNamedParams(final String name,
			final Map<String, ? extends Object> params) {
		Query query = getSession().getNamedQuery(name);

		for (final Map.Entry<String, ? extends Object> param : params
				.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		final List<T> result = (List<T>) query.list();
		return result;
	}
	
	@Override
	public Class<T> getEntityClass() {
		return persistentClass;
	}
	
	/*
	 * @param entityManager
	 */


	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	protected List<T> findByCriteria(final Criterion... criterion) {
		return findByCriteria(-1, -1, criterion);
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(final int firstResult,
			final int maxResults, final Criterion... criterion) {
		Session session = getSession();
		Criteria crit = session.createCriteria(getEntityClass());

		for (final Criterion c : criterion) {
			crit.add(c);
		}

		if (firstResult > 0) {
			crit.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			crit.setMaxResults(maxResults);
		}

		final List<T> result = crit.list();
		return result;
	}

	protected int countByCriteria(Criterion... criterion) {
		Session session = getSession();
		Criteria crit = session.createCriteria(getEntityClass());
		crit.setProjection(Projections.rowCount());

		for (final Criterion c : criterion) {
			crit.add(c);
		}

		return (Integer) crit.list().get(0);
	}


	@Override
	public void delete(T entity) {
        getSession().delete(entity);
	}

    @Override
    public void closeSession() {
    }

    @Override
	public T save(T entity) {
		getSession().saveOrUpdate(entity);
        getSession().flush();
		return entity;
	}

}