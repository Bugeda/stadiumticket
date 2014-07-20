package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {

    List<Ticket> getSoldTickets(Integer eventId, Integer sectorId);

    public void sellTickets(Event event, List<Seat> chosenSeats);

    public void bookTickets(Event event, Customer customer, List<Seat> chosenSeats);
}
