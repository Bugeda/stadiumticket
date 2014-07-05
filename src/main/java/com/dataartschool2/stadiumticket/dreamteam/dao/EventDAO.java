package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;

public interface EventDAO  extends GenericDAO<Event>{
	
	public List<Event> findFutureEvents();	
	public List<Event> findPastEvents();	
	
}
