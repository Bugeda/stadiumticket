package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.*;
import com.dataartschool2.stadiumticket.dreamteam.web.SeatsForm;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml"})
@Transactional
public class TicketServiceTest {
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
    private TicketService ticketService;

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private EventService eventService;

    @Autowired
    private SectorService sectorService;
 	
    @Autowired
    private SeatService seatService;
    
    @Test
    public void findAllTicket(){
    	  List<Ticket> tickets = ticketService.getAllTickets();
    	  assertEquals(tickets.size(),44);
    }
    
    private List<Seat> createSeats(Integer eventId){
    	Event event = eventService.findById(eventId);      	
		assertNotNull(event);
		List<Seat> seatsList = new ArrayList<Seat>();
		Integer[] usedRow= new Integer[10];
       	Integer[] usedSeat= new Integer[10];
    	
    	for (int sec=0;sec<27;sec++){
    		Integer sectorId= (int)(Math.random()*26+1);
    	
    		for (int i=0;i<10;i++){
    			usedRow[i] = (int)(Math.random()*19+1);
    			usedSeat[i] = (int)(Math.random()*49+1);
    			Seat seat=new Seat(0, usedSeat[i], usedRow[i], sectorService.findById(sectorId));
    			seatsList.add(seat);
    			assertNotNull(seat);   			
    		}
    	}
  
        return 	seatsList;
    }
    
    @Test
    public void getSoldTicketsBySector(){    	
    	List<Seat> seatsList = createSeats(2);    	    	    	
    	int[] sellResult=ticketService.sellTickets(2, seatsList);

    	List<Ticket> soldTicket=new ArrayList<Ticket>();
    	for (int sec=1;sec<=27;sec++){
    		soldTicket.addAll(ticketService.getSoldTicketsBySector(2, sec));
    		for (Ticket sd:soldTicket){
    			assertTrue(seatsList.contains(sd.getSeat()));
    	      	assertEquals(sd.getSeatStatus(),SeatStatus.occupied);
    		}
    	}
    	int countNotSell = 0;
    	int soldCount = 0;   	
    	for (int r:sellResult){
    		if (r!=0) countNotSell++; 
    		if (r==SeatStatus.occupied.ordinal()) soldCount++;
    	}
     	assertEquals(soldTicket.size(), 270-countNotSell);	
     	assertEquals(soldCount, countNotSell);		
    }
    
    @Test
    public void getAllTicketsByEventFromSell(){
    	List<Seat> seatsList = createSeats(2);    	    	    	
    	int[] sellResult=ticketService.sellTickets(2, seatsList);    	
       	int countNotSell = 0;
    	int soldCount = 0;   	
    	for (int r:sellResult){
    		if (r!=0) countNotSell++; 
    		if (r==SeatStatus.occupied.ordinal()) soldCount++;
    	}

     	assertEquals(soldCount, countNotSell);	
    	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
  
     	assertEquals(allTickets.size(), 270-countNotSell);	
    	for (int i=0;i<allTickets.size();i++){
    		assertTrue(seatsList.contains(allTickets.get(i).getSeat()));
         	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.occupied);
         }            
    }
    
    @Test
    public void sellTickets(){
    	List<Seat> seatsList = createSeats(2);    	    	    	
    	seatsList.add(seatsList.get(5));
    	seatsList.add(seatsList.get(10));
     	final int COUNT_IDENTICAL = 2;
    	
     	int[] sellResult=ticketService.sellTickets(2, seatsList);      	
       	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
       	int countNotSell = 0;
    	int soldCount = 0;   	
    	for (int r:sellResult){
    		if (r!=0) countNotSell++; 
    		if (r==SeatStatus.occupied.ordinal()) soldCount++;
    	}
     	assertEquals(allTickets.size(), 270+COUNT_IDENTICAL-countNotSell);	
     	assertEquals(soldCount, countNotSell);	
     	
        for (int i=0;i<allTickets.size();i++){
        	assertTrue(seatsList.contains(allTickets.get(i).getSeat()));
        	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.occupied);
        }   
		boolean different = true;
        for (Ticket tk:allTickets){
        	List<Ticket> identicalTickets = ticketService.findBySeat(tk.getSeat());
        	if (identicalTickets.size()>1){
        		for (int i=0;i<identicalTickets.size()-1;i++)
        			for (int j=i;j<identicalTickets.size();j++){
        				different=different||identicalTickets.get(i).equals(identicalTickets.get(j));
        			}
        		}
        }
    assertTrue(different);	
    }
    
    @Test
    public void getBookedTicketsBySector(){
    	List<Seat> seatsList = createSeats(2);    	
    	int[] bookResult=ticketService.bookTickets(2, seatsList, "new customer");
    	List<Ticket> bookedTicket=new ArrayList<Ticket>();
    	for (int sec=1;sec<=27;sec++){
    		bookedTicket.addAll(ticketService.getBookedTicketsBySector(2, sec));
    		for (Ticket sd:bookedTicket){
    			assertTrue(seatsList.contains(sd.getSeat()));
    	      	assertEquals(sd.getSeatStatus(),SeatStatus.booked);
    		}
    	}
    	int countNotBook = 0;
    	int bookedCount = 0;   	
    	for (int r:bookResult){
    		if (r!=0) countNotBook++; 
    		if (r==SeatStatus.booked.ordinal()) bookedCount++;
    	}
     	assertEquals(bookedTicket.size(), 270-countNotBook);	
     	assertEquals(bookedCount, countNotBook);	
    }
    
    @Test
    public void getAllTicketsByEventFromBooking(){
    	List<Seat> seatsList = createSeats(2);    	
    	int[] bookResult=ticketService.bookTickets(2, seatsList, "new customer");
    	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
    	int countNotBook = 0;
    	int bookedCount = 0;   	
    	for (int r:bookResult){    		
    		if (r!=0) countNotBook++; 
    		if (r==SeatStatus.booked.ordinal()) bookedCount++;
    	}
     	assertEquals(allTickets.size(), 270-countNotBook);
     	assertEquals(bookedCount, countNotBook);
        for (int i=0;i<allTickets.size();i++){
        	assertTrue(seatsList.contains(allTickets.get(i).getSeat()));
         	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.booked);
         }         
    }
    
    @Test
    public void bookTickets(){
    	List<Seat> seatsList = createSeats(2);    	    	    	
    	seatsList.add(seatsList.get(5));
    	seatsList.add(seatsList.get(10));
     	final int COUNT_IDENTICAL = 2;
    	
    	int[] bookResult=ticketService.bookTickets(2, seatsList, "new customer");
       	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
       	int countNotBook = 0;
    	int bookedCount = 0;   	
    	for (int r:bookResult){
    		if (r!=0) countNotBook++; 
    		if (r==SeatStatus.booked.ordinal()) bookedCount++;
    	}
     	assertEquals(allTickets.size(), 270+COUNT_IDENTICAL-countNotBook);	
     	assertEquals(bookedCount, countNotBook);
        for (int i=0;i<allTickets.size();i++){
        	assertTrue(seatsList.contains(allTickets.get(i).getSeat()));
        	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.booked);
        }   
		boolean different = true;
        for (Ticket tk:allTickets){
        	List<Ticket> identicalTickets = ticketService.findBySeat(tk.getSeat());
        	if (identicalTickets.size()>1){
        		for (int i=0;i<identicalTickets.size()-1;i++)
        			for (int j=i;j<identicalTickets.size();j++){
        				different=different||identicalTickets.get(i).equals(identicalTickets.get(j));
        			}
        		}
        }
    assertTrue(different);	
    }
    
}
