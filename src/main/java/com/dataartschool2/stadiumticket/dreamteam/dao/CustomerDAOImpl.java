package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;

@Repository  
@Transactional  
public class CustomerDAOImpl extends GenericDAOImpl<Customer> implements CustomerDAO {

	@Override
	public List<Customer> findLikeCustomerName(String customerName) {
		Criterion criterion = Restrictions.like("customerName", customerName, MatchMode.ANYWHERE);   
		return findByCriteria(criterion);
	}

    

}