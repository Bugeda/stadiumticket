package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;


public interface EventService {

	List<Event> getEvents();

	public void sysoutMessage(String id);
}
