package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;

import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;
import com.dataartschool2.stadiumticket.dreamteam.web.SeatsForm;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {

    public List<Ticket> getSoldTicketsBySector(Integer eventId, Integer sectorId);

    public void sellTickets(Integer eventId, SeatsForm seatsForm);

    public void bookTickets(Integer eventId, Customer customer, List<Seat> chosenSeats);

    public List<Ticket> getAllTickets(Integer eventId);
}
