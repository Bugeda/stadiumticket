package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.dao.EventDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventDAO eventDAO;
		
	@Override
	@Transactional
	public List<Event> getEvents() {		
		return eventDAO.findAll();
	}
	
	@Override
	@Transactional
	public void sysoutMessage(String message){
		eventDAO.sysoutMessage(message);
	}


}

