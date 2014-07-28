package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.dao.BookingDAO;
import com.dataartschool2.stadiumticket.dreamteam.dao.CustomerDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	
    @Autowired
    private CustomerDAO customerDAO;
    
	@Override
	@Transactional
	public List<Customer> findLikeCustomerName(String customerName) {		
		return customerDAO.findLikeCustomerName(customerName);
	}
	

}
