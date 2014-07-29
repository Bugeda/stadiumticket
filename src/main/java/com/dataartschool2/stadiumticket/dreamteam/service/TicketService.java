package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;
import com.dataartschool2.stadiumticket.dreamteam.web.SeatsForm;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {
	
	public void updateTicket(Ticket ticket);
	
	public List<Ticket> getAllTickets();
	
	public List<Ticket> findSoldTickets();
	
	public List<Ticket> findBookedTickets();
	
	public Ticket findById(int id);

	public List<Ticket> findBySeat(Seat seat);
	
    public List<Ticket> getSoldTicketsBySector(Integer eventId, Integer sectorId);
    
    public List<Ticket> getBookedTicketsBySector(Integer eventId, Integer sectorId);
    
    public boolean checkExistsTickets(SeatsForm seatsForm);
    
    public Boolean[] sellTickets(Integer eventId, SeatsForm seatsForm);

    public Boolean[] bookTickets(Integer eventId, SeatsForm seatsForm);

    public List<Ticket> getAllTicketsByEvent(Integer eventId);





}
