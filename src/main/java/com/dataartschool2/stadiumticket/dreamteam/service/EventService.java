package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;

import java.util.List;



public interface EventService {

    void deleteEvent(Event event);

    public void updateEvent(Event event);
	
	public Event findById(Integer id);
	
	public List<Event> getFutureEvents();	
	
	public List<Event> getPastEvents();

	
	

}
