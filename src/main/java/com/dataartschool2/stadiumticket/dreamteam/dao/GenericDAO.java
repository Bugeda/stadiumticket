package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

public abstract interface GenericDAO<EntityClass>{
	//entityClass
	public EntityClass updateEntity(EntityClass entity);  
	
	public void deleteEntity(EntityClass entity);  
    
    public  List<EntityClass> findAll();  
    
    public EntityClass findById(Integer integer); 
    
    public int countAll();
 
}
 