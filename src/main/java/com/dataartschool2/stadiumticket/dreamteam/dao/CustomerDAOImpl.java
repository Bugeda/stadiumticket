package com.dataartschool2.stadiumticket.dreamteam.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;

@Repository  
@Transactional  
public class CustomerDAOImpl extends GenericDAOImpl<Customer> implements CustomerDAO {

    

}