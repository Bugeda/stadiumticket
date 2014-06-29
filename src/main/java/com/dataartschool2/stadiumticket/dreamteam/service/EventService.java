package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;


public interface EventService {

	Event findById(Integer id);

	List<Event> getFutureEvents();
	
	List<Event> getPastEvents();

	void updateEvent(Event event);

	void deleteEvent(Event event);

}
