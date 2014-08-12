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
    
	public List<Ticket> getAllTicketsBySector(Integer eventId, Integer sectorId);
    
    public int checkExistTicket(Integer eventId, Seat seat);
    
    public int[] sellTickets(Integer eventId, List<Seat> seatsList);   
    
    public int[] bookTickets(Integer eventId, List<Seat> seatsList, String customerName);
    
	public List<Ticket> getAllTicketsByEvent(Integer eventId);









}
