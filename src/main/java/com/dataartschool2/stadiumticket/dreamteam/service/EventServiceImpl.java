package com.dataartschool2.stadiumticket.dreamteam.service;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.dao.EventDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.mysql.jdbc.Field;

@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventDAO eventDAO;
	
	
	@Override
	@Transactional
	public void updateEvent(Event event){
		eventDAO.updateEntity(event);
	}
	
	@Override
	@Transactional
	public void deleteEvent(Event event){
		eventDAO.deleteEntity(event);
	}
		
	@Override
	@Transactional
	public Event findById(Integer id){
		return eventDAO.findById(id);	 
	}

	@Override
	@Transactional
	public List<Event> getFutureEvents() {	
		return 	eventDAO.findFutureEvents();
	}

	@Override
	@Transactional
	public List<Event> getPastEvents() {
		return 	eventDAO.findPastEvents();
	}




	
}

