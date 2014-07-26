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
    
    @Override
    @Transactional
    public void sellTickets(Integer eventId, SeatsForm seatsForm) {
        Event event  = eventService.findById(eventId);
        
        List<Seat> chosenSeats = seatsForm.getChosenSeats();
        List<Integer> chosenSectors = seatsForm.getChosenSectorsNums();
        
        int i = 0;
        for(Seat seat : chosenSeats){
         
            Integer sectorNo = chosenSectors.get(i);
            ++i;
           
            Sector sector = sectorService.findById(sectorNo);
            seat.setSector(sector);
            seatService.updateSeat(seat);
            String ticketNumber = generateTicketNumber(event, seat);
            if (findByNumber(ticketNumber).isEmpty()){
            	Ticket ticket = new Ticket();  
            	updateTicket(ticket);
            	ticket.setEvent(event);
            	ticket.setSeat(seat);
            	ticket.setSeatStatus(SeatStatus.occupied);
            	ticket.setTicketNumber(ticketNumber);
            }else {   
            	    System.out.println(appContext.getMessage("error.ticketExist", new Object[]{}, null));      	
              //	throw new RuntimeException(appContext.getMessage("error.ticketExist", new Object[]{}, null));
            }
        }
    }
    
    @Override
    @Transactional
    public List<Ticket> findByNumber(String ticketNumber) {
		return ticketDAO.findByNumber(ticketNumber);
	}

	private  String generateTicketNumber(Event event, Seat seat) {
        return Integer.toString(Objects.hash(event, seat));
    }
    
    public void checkTickets(List<Ticket> ticketSet, Ticket ticket){
    	if (ticketSet.contains(ticket)&&(!ticket.getSeatStatus().equals(SeatStatus.vacant))){
    	    System.out.println(appContext.getMessage("error.ticketExist", new Object[]{}, null));   
         	//throw new RuntimeException(appContext.getMessage("error.ticketExist", new Object[]{}, null));
    	}
    }
    
    @Override
    @Transactional
    public void bookTickets(Integer eventId, SeatsForm seatsForm){   
		Event event  = eventService.findById(eventId);
           
		List<Seat> chosenSeats = seatsForm.getChosenSeats();
		List<Integer> chosenSectors = seatsForm.getChosenSectorsNums();
           
		Customer customer = new Customer();
		customer.setCustomerName(seatsForm.getCustomerName());
        int i = 0;
        for(Seat seat : chosenSeats){
            
            Integer sectorNo = chosenSectors.get(i);
            ++i;
           
            Sector sector = sectorService.findById(sectorNo);
            seat.setSector(sector);
            seatService.updateSeat(seat);  
            String ticketNumber = generateTicketNumber(event, seat);
            if (findByNumber(ticketNumber).isEmpty()){
            	Ticket ticket = new Ticket();  
            	updateTicket(ticket);
            	ticket.setEvent(event);
            	ticket.setSeat(seat);
            	ticket.setSeatStatus(SeatStatus.booked);
            	ticket.setTicketNumber(ticketNumber);
            	Booking booking = new  Booking(0, customer, ticket, BookingStatus.Booked);
            	Boolean booked = (booking.getBookingStatus().equals(BookingStatus.Booked)&&(booking.getTicket().getSeatStatus().equals(SeatStatus.booked)));
            	if (booked){              	
                	ticketDAO.updateEntity(ticket);     	           	    
     			    bookingService.updateBooking(booking);
            	}
            }else {   
            	    System.out.println(appContext.getMessage("error.ticketExist", new Object[]{}, null));      	
              //	throw new RuntimeException(appContext.getMessage("error.ticketExist", new Object[]{}, null));
            }
        }  
           
		/*List<Ticket> AllTickets = getAllTicketsByEvent(eventId);
		Event event = eventService.findById(eventId);
		List<Ticket> tickets =  new ArrayList<Ticket>();
		for(Seat seat : chosenSeats){	 
	            Ticket ticket = new Ticket();
	            ticket.setEvent(event);
	            ticket.setSeat(seat);
	            String ticketNumber = generateTicketNumber(event, seat);	
	            ticket.setTicketNumber(ticketNumber);
	            checkTickets(AllTickets, ticket);
	            ticket.setSeatStatus(SeatStatus.booked);

	     	
	            ticketDAO.updateEntity(ticket);
	            seatService.updateSeat(seat);  	    
			    bookingService.updateBooking(booking);
	     		}
		}      */
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


}
