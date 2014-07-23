package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;

public interface TicketDAO extends GenericDAO<Ticket>{


	public List<Ticket> findByEvent(Event event);

}
