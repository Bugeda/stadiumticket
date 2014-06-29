package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.io.Serializable;
import java.util.List;


public abstract interface GenericDAO<EntityClass>{
	//entityClass
	EntityClass makePersistent(EntityClass entity);  
    
    void deletePersistent(EntityClass entity);  
    
    List<EntityClass> findAll();  
    
    EntityClass findById(Integer integer); 
    
	int countAll();
 
}
 