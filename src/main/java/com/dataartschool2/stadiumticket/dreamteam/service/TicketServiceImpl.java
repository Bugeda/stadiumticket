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
    
    public boolean checkExistsTickets(SeatsForm seatsForm){
    	boolean result=true;
    	
    	Integer eId = seatsForm.getEventId();

    	List<Seat> chosenSeats = seatsForm.getChosenSeats();
    	List<Integer> chosenSectors = seatsForm.getChosenSectorsNums();
    	int i = 0;
    	for(Seat seat : chosenSeats){         		
            Integer sectorNo = chosenSectors.get(i);
            ++i; 
            Sector sector = sectorService.findById(sectorNo);
            seat.setSector(sector);
            
            List<Seat> st=seatService.findBySeat(seat.getRowNumber(),seat.getSeatNumber(),seat.getSector());  
            if (!st.isEmpty()) 
            	seat = st.get(0);
            List<Ticket> allTickets = getAllTicketsByEvent(eId);
        	
        	for (Ticket tk:allTickets)       	
        		if (tk.getSeat().equals(seat)){
        			if (!tk.getSeatStatus().equals(SeatStatus.vacant)){    				
    					return false;
        			}
        		}
        	result=true;
    	}
    	return result;
    }
    
    @Override
    @Transactional
    public Boolean[] sellTickets(Integer eventId, SeatsForm seatsForm) {
    	Boolean[] results = new Boolean[seatsForm.getChosenSeats().size()];
        Event event  = eventService.findById(eventId);
        
        List<Seat> chosenSeats = seatsForm.getChosenSeats();
        List<Integer> chosenSectors = seatsForm.getChosenSectorsNums();
        
        int i = 0;
        for(Seat seat : chosenSeats){         
        	results[i]=false;
            Integer sectorNo = chosenSectors.get(i);
            ++i;           
            Sector sector = sectorService.findById(sectorNo);
            seat.setSector(sector);
            Ticket ticket = getTicket(event, seat);
            if (ticket!=null){
                ticket.setSeatStatus(SeatStatus.occupied);
            	results[i-1]=true;         
            } 
        }
        return results;
    }
     
    @Override
    @Transactional
    public Boolean[] bookTickets(Integer eventId, SeatsForm seatsForm){
    	Boolean[] results = new Boolean[seatsForm.getChosenSeats().size()];  
		Event event  = eventService.findById(eventId);
       
		List<Seat> chosenSeats = seatsForm.getChosenSeats();
		List<Integer> chosenSectors = seatsForm.getChosenSectorsNums();
           
		Customer customer = new Customer();
		customer.setCustomerName(seatsForm.getCustomerName());
        int i = 0;
        for(Seat seat : chosenSeats){
          	results[i]=false;
            Integer sectorNo = chosenSectors.get(i);
          	++i;
            Sector sector = sectorService.findById(sectorNo);
            seat.setSector(sector);  
                 	
            Ticket ticket = getTicket(event, seat);
            if (ticket != null){             	             
                ticket.setSeatStatus(SeatStatus.booked);                           
            	Booking booking = new  Booking(0, customer, ticket, BookingStatus.Booked);  
            	bookingService.updateBooking(booking);           
            	results[i-1]=true;           
            }
        } 
        return results;
	}

	private  String generateTicketNumber(Event event, Seat seat) {
        return Integer.toString(Objects.hash(event, seat));
    }
    
    private Ticket getTicket(Event event, Seat seat){
    	//ticket is not exist
        List<Seat> st=seatService.findBySeat(seat.getRowNumber(),seat.getSeatNumber(),seat.getSector());       
        if (st.isEmpty()) {
        	Ticket tk=new Ticket();
        	String ticketNumber = generateTicketNumber(event, seat);
            updateTicket(tk);
            tk.setEvent(event);
            tk.setSeat(seat);
            tk.setTicketNumber(ticketNumber); 
           	return tk;           	
        }
        else seat = st.get(0);

        List<Ticket> allTickets = getAllTicketsByEvent(event.getId());
    	
    	for (Ticket tk:allTickets){
    	
    		if (tk.getSeat().equals(seat)){
    			if (tk.getSeatStatus().equals(SeatStatus.vacant)){    				
					return tk;
    			}else {   
					//System.out.println(appContext.getMessage("error.ticketExist", new Object[]{}, null));      	
					//throw new RuntimeException(appContext.getMessage("error.ticketExist", new Object[]{}, null));
					//return null;
				}   
    		}
    	}    	
    	return null;
    }
}


