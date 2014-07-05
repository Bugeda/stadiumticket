package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;



public interface EventService {
	
	public void updateEvent(Event event);
	
	public Event findById(Integer id);
	
	public List<Event> getFutureEvents();	
	
	public List<Event> getPastEvents();

	
	

}
