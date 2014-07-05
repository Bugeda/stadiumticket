package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.NewEventForm;

import java.text.ParseException;
import java.util.List;



public interface EventService {

    void deleteEvent(Event event);

    NewEventForm getEventForm(Integer id);

    public void updateEvent(Event event);
	
	public Event findById(Integer id);
	
	public List<Event> getFutureEvents();	
	
	public List<Event> getPastEvents();

    public void createEvent(NewEventForm evForm) throws ParseException;
	

}
