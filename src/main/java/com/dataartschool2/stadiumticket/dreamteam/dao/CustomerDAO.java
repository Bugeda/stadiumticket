package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;

public interface CustomerDAO extends GenericDAO<Customer>{

	List<Customer> findLikeCustomerName(String customerName);

}
