package com.dataartschool2.stadiumticket.dreamteam.web;

import java.util.ArrayList;
import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorStatus;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;
import com.dataartschool2.stadiumticket.dreamteam.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketsRestController {

    @Autowired
    private SectorService sectorService;
   
    @Autowired
    private TicketsController ticketsController;
    
    @Autowired
    private TicketService ticketService;
    
    @RequestMapping(value = "/tickets/get_sector_seats", method = RequestMethod.GET)
    public SectorStatus getSectorStatus(@RequestParam("event") Integer eventId,
                                        @RequestParam("sector") Integer sectorId){
        return sectorService.getSectorStatus(eventId, sectorId);
    }
    
    
    private List<Seat> parseSeats(String[] seats){
    	List<Seat> result=new ArrayList<Seat>();
    	
    	Seat seat=new Seat();
    	Sector sector=new Sector();
    	for (String s:seats){
    		String[] seatString = s.split("[_]");
    		try{
    			seat.setSeatNumber(Integer.parseInt(seatString[0]));
    			seat.setRowNumber(Integer.parseInt(seatString[1]));
    			sector = sectorService.findById((Integer.parseInt(seatString[2])));
    			seat.setSector(sector);
    			result.add(seat);
    		}
    		catch(Exception e){
    			result.add(null);
    		}		
    	}      	       
    	return result;
    }
    
    @RequestMapping(value = "/tickets/setbook", method = RequestMethod.GET)
    public int[] submit_bookTickets(@RequestParam("id") Integer eventId, 
    								@RequestParam("seat") String[] seats,
    								@RequestParam("customer") String customer) {
    	int[] result = new int[seats.length];
    	int i=0;
    	boolean isTicketkCorrect = true;
    	List<Seat> seatsList=parseSeats(seats);
    	for (Seat st:seatsList){    		
    		if (st.equals(null)){
    			//error of parse ticket
    			result[i]=5;
    			isTicketkCorrect=false;
    		} else {
    			result[i]=ticketService.checkExistTicket(eventId, st);
    		}
    		i++;
    	}
    	if ((isTicketkCorrect)&&(!customer.isEmpty())&&(!customer.equals(null)))
    		result=ticketService.bookTickets(eventId, seatsList, customer);
    	return result;
    }
    
    @RequestMapping(value = "/tickets/setsell", method = RequestMethod.GET)
    public int[] submit_sellTickets(@RequestParam("id") Integer eventId, 
    								@RequestParam("seat") String[] seats) {
    	int[] result = new int[seats.length];
    	int i=0;
    	boolean isTicketkCorrect = true;
    	List<Seat> seatsList=parseSeats(seats);
    	for (Seat st:seatsList){    		
    		if (st.equals(null)){
    			//error of parse ticket
    			result[i]=5;
    			isTicketkCorrect=false;
    		} else {
    			result[i]=ticketService.checkExistTicket(eventId, st);
    		}
    		i++;
    	}
    	if (isTicketkCorrect)
    		result=ticketService.sellTickets(eventId, seatsList);
    	return result;
    }
}
