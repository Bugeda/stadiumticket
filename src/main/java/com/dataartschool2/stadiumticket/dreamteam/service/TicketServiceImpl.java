package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.dao.TicketDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.*;
import com.dataartschool2.stadiumticket.dreamteam.web.SeatsForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TicketServiceImpl implements TicketService {
    
	@Autowired
	private ApplicationContext appContext;
	
    @Autowired
    private EventService eventService;
    
    @Autowired
    private TicketDAO ticketDAO;
    
    @Autowired
    private BookingService bookingService;
        
    @Autowired
    private SeatService seatService;

    @Autowired
    private SectorService sectorService;

    @Override
    @Transactional
	public void updateTicket(Ticket ticket) {
    	ticketDAO.updateEntity(ticket);
    }

    @Override
    @Transactional
	public List<Ticket> getAllTickets() {
		return ticketDAO.findAll();
	}

	@Override
	@Transactional
	public List<Ticket> findSoldTickets() {
		return ticketDAO.findSoldTickets();
	}
	
	@Override
	@Transactional
	public List<Ticket> findBookedTickets() {
		return ticketDAO.findBookedTickets();
	}
	
	@Override
	@Transactional
	public Ticket findById(int id) {
		return ticketDAO.findById(id);
	}
	
	@Override
	@Transactional
    public List<Ticket> findBySeat(Seat seat) {
		return ticketDAO.findBySeat(seat);
	}

    
    @Override
    @Transactional
    public List<Ticket> getSoldTicketsBySector(Integer eventId, Integer sectorId) {
        List<Ticket> tickets = ticketDAO.findSoldTickets();
        List<Ticket> result = new ArrayList<Ticket>();

        for(Ticket ticket : tickets){
            Event event = ticket.getEvent();
            Seat seat = ticket.getSeat();
            Sector sector = seat.getSector();
          
            if(event.getId().equals(eventId) && sector.getId().equals(sectorId)){
                result.add(ticket);
            }
        }
        return result;
    }
    
    @Override
    @Transactional
    public List<Ticket> getBookedTicketsBySector(Integer eventId, Integer sectorId) {
        List<Ticket> tickets = ticketDAO.findBookedTickets();
        List<Ticket> result = new ArrayList<Ticket>();

        for(Ticket ticket : tickets){
            Event event = ticket.getEvent();
            Seat seat = ticket.getSeat();
            Sector sector = seat.getSector();
            if(event.getId().equals(eventId) && sector.getId().equals(sectorId)){
                result.add(ticket);
            }
        }
        return result;
    }
    
    @Override
    @Transactional
    public List<Ticket> getAllTicketsBySector(Integer eventId, Integer sectorId) {
        List<Ticket> tickets = ticketDAO.findByEvent(eventService.findById(eventId));
        List<Ticket> result = new ArrayList<Ticket>();

        for(Ticket ticket : tickets){
            Event event = ticket.getEvent();
            Seat seat = ticket.getSeat();
            Sector sector = seat.getSector();
            if(event.getId().equals(eventId) && sector.getId().equals(sectorId)){
                result.add(ticket);
            }
        }

        return result;
    }
    
    @Override
    @Transactional
    public List<Ticket> getAllTicketsByEvent(Integer eventId) {
        List<Ticket> tickets = new ArrayList<Ticket>();
        tickets = getAllTickets();
        List<Ticket> result = new ArrayList<Ticket>();
        if (!tickets.isEmpty())
        
        for(Ticket ticket : tickets){
            Event event = ticket.getEvent();
            Seat seat = ticket.getSeat();
            Sector sector = seat.getSector();
            
            if(event.getId().equals(eventId)){
                result.add(ticket);
            }
        }           	 
        return result;
    }
    
    public int checkExistTicket(Integer eventId, Seat seat){      	   
        Integer sectorNo = seat.getSector().getId();
                       
        List<Ticket> allTickets = getAllTicketsBySector(eventId, sectorNo);            
        for (Ticket tk:allTickets) {                 	
        	if ((tk.getSeat().getRowNumber()==seat.getRowNumber())&&(tk.getSeat().getSeatNumber()==seat.getSeatNumber())){            		            			    				
        		return tk.getSeatStatus().ordinal();            			
            	}    
        }
        return 0;
    }
    
    @Override
    @Transactional
    public int[] sellTickets(Integer eventId, List<Seat> seatsList) {
    	int[] results = new int[seatsList.size()];  
        Event event  = eventService.findById(eventId);
       
        int i = 0;
        for(Seat seat : seatsList){  
        	try{
        		results[i]=checkExistTicket(eventId, seat);        	
        		if (results[i]==0){
        			Ticket ticket = getTicket(event, seat);
        			ticket.setSeatStatus(SeatStatus.occupied);
        			results[i]=0;         
        		} 
        	 }catch(Exception e){
             	results[i]=4; 
             }
           	++i; 
        }
        return results;
    }
     
    @Override
    @Transactional
    public int[] bookTickets(Integer eventId, List<Seat> seatsList, String customerName){
    	int[] results = new int[seatsList.size()];  
		Event event  = eventService.findById(eventId);
           
		Customer customer = new Customer();
		customer.setCustomerName(customerName);
        int i = 0;
        for(Seat seat : seatsList){        
            try{              	           	
            	results[i]=checkExistTicket(eventId, seat);
            	if (results[i]==0){     
            		Ticket ticket = getTicket(event, seat);
            		ticket.setSeatStatus(SeatStatus.booked);                           
            		Booking booking = new  Booking(0, customer, ticket, BookingStatus.Booked);  
            		bookingService.updateBooking(booking);           
            		results[i]=0;           
            	}   
            }catch(Exception e){
            	results[i]=4;
            }
          	++i;
        } 
        return results;
	}

	private  String generateTicketNumber(Event event, Seat seat) {
        return Integer.toString(Objects.hash(event, seat));
    }
    
    private Ticket getTicket(Event event, Seat seat){        
        List<Ticket> allTickets = getAllTicketsBySector(event.getId(), seat.getSector().getId());
        
        for (Ticket atk:allTickets) {  
        		if ((atk.getSeat().getRowNumber()==seat.getRowNumber())&&(atk.getSeat().getSeatNumber()==seat.getSeatNumber())){        		
        			if (atk.getSeatStatus().equals(SeatStatus.vacant)){    				
        	        	return atk;
        			}         				
        		}                
    	}    	    	
      	Ticket tk=new Ticket();
      	String ticketNumber = generateTicketNumber(event, seat);    
        tk.setEvent(event);
        tk.setSeat(seat);
        tk.setTicketNumber(ticketNumber);
    	updateTicket(tk);
    	return tk;
    }
}


