package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<EC, ID extends  Serializable>{
   // T findById(ID id, boolean lock);  
    
    List<EC> findAll();  
  
   /* List<T> findByExample(T exampleInstance, String[] excludeProperty);  
  
    T makePersistent(T entity);  
  
    void makeTransient(T entity);
    */
	void sysoutMessage(String message);
 
}
 