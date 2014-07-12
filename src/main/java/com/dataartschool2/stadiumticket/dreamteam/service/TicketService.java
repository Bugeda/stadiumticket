package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {

    void sellTickets(Event event, List<Seat> chosenSeats);
}
