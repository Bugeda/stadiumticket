package com.dataartschool2.stadiumticket.dreamteam.service;

import java.util.ArrayList;
import java.util.Arrays;
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
public class BookingServiceTest {
	
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
    public void getBookingsForEvent(){
    	List<Seat> seatsList = createSeats(2);    	
    	ticketService.bookTickets(2, seatsList, "new customer");
    	List<Booking> bookingSet=bookingService.getBookingsForEvent(2);    	
    	assertEquals(bookingSet.size(),270);
    }
    
    @Test
    public void cancelBookingSet(){
       	List<Seat> seatsList = createSeats(2);  	
    	ticketService.bookTickets(2, seatsList, "new customer");
    	List<Booking> bookingSet=bookingService.getBookingsForEvent(2);  
    	assertEquals(bookingSet.size(),270);
    	Integer[] idBooking = new Integer[50];
    	Integer[] idinBookingSet= new Integer[50];
     	List<Integer> use = new ArrayList<Integer>();
    	Integer i = 0;
    	while (i<50){
      		Integer id = (int)(Math.random()*269+1);
      		if (use.contains(id)) {
      			continue;
      		}
      				use.add(id);
      				idBooking[i] = bookingSet.get(id).getId(); 
      				idinBookingSet[i] = id;
      	    		i++;
    	}
		bookingService.cancelBookingSet(idBooking);
		List<Booking> newBookingSet=bookingService.getBookingsForEvent(2);  
		assertEquals(newBookingSet.size(),220);

		for (i=0;i<50;i++){
			assertEquals(bookingSet.get(idinBookingSet[i]).getBookingStatus(),BookingStatus.BookingCancelled);
			assertEquals(ticketService.findById(bookingSet.get(idinBookingSet[i]).getTicket().getId()).getSeatStatus(),SeatStatus.vacant);
		}
    }
    
    @Test
    public void sellBookingSet(){
    	
       	List<Seat> seatsList = createSeats(2);  	
    	ticketService.bookTickets(2, seatsList, "new customer");	     	
    	List<Booking> bookingSet=bookingService.getBookingsForEvent(2);  
    	assertEquals(bookingSet.size(),270);
    	Integer[] idBooking = new Integer[50];
    	Integer[] idinBookingSet= new Integer[50];
     	List<Integer> use = new ArrayList<Integer>();
    	Integer i = 0;
    	while (i<50){
      		Integer id = (int)(Math.random()*269+1);
      		if (use.contains(id)) {
      			continue;
      		}
      				use.add(id);
      				idBooking[i] = bookingSet.get(id).getId(); 
      				idinBookingSet[i] = id;
      	    		i++;
    	}
		bookingService.sellBookingSet(idBooking);
		List<Booking> newBookingSet=bookingService.getBookingsForEvent(2);  
		assertEquals(newBookingSet.size(),220);

		for (i=0;i<50;i++){
			assertEquals(bookingSet.get(idinBookingSet[i]).getBookingStatus(),BookingStatus.BookingRedeemed);
			assertEquals(ticketService.findById(bookingSet.get(idinBookingSet[i]).getTicket().getId()).getSeatStatus(),SeatStatus.occupied);
		}
    }
    
    
}
