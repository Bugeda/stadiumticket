package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;

import java.text.ParseException;
import java.util.List;



public interface EventService {

	//void deleteEvent(Event event);

	void markAsDeleted(Event event);

	public Event updateEvent(Event event);
	
	public Event findById(Integer id);
	
	public List<Event> getFutureEvents();	
	
	public List<Event> getPastEvents();

	public void createEvent(Event event) throws ParseException;

	public Event createEmptyEvent();

//	public void editEvent(NewEventForm editEventForm) throws ParseException;


}
