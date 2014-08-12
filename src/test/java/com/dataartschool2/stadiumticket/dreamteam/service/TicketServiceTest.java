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
    
    private final static int EVENT_ID = 2;
    private final static String customerName = "new customer";
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
    	List<Seat> seatsList = createSeats(EVENT_ID);    	    	    	
    	int[] sellResult=ticketService.sellTickets(EVENT_ID, seatsList);

    	List<Ticket> soldTicket=new ArrayList<Ticket>();
    	for (int sec=1;sec<=27;sec++){
    		soldTicket.addAll(ticketService.getSoldTicketsBySector(EVENT_ID, sec));
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
     	assertEquals(soldTicket.size(), seatsList.size()-countNotSell);	
     	assertEquals(soldCount, countNotSell);		
    }
    
    @Test
    public void getAllTicketsByEventFromSell(){
    	List<Seat> seatsList = createSeats(EVENT_ID);    	    	    	
    	int[] sellResult=ticketService.sellTickets(EVENT_ID, seatsList);    	
       	int countNotSell = 0;
    	int soldCount = 0;   	
    	for (int r:sellResult){
    		if (r!=0) countNotSell++; 
    		if (r==SeatStatus.occupied.ordinal()) soldCount++;
    	}

     	assertEquals(soldCount, countNotSell);	
    	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
  
     	assertEquals(allTickets.size(), seatsList.size()-countNotSell);	
    	for (int i=0;i<allTickets.size();i++){
    		assertTrue(seatsList.contains(allTickets.get(i).getSeat()));
         	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.occupied);
         }            
    }
    
    @Test
    public void sellTickets(){
    	List<Seat> seatsList = createSeats(EVENT_ID);    	    	    	
    	seatsList.add(seatsList.get(5));
    	seatsList.add(seatsList.get(10));

     	int[] sellResult=ticketService.sellTickets(EVENT_ID, seatsList);      	
       	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
       	int countNotSell = 0;
    	int soldCount = 0;   	
    	for (int r:sellResult){
    		if (r!=0) countNotSell++; 
    		if (r==SeatStatus.occupied.ordinal()) soldCount++;
    	}
     	assertEquals(allTickets.size(), seatsList.size()-countNotSell);	
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
    	List<Seat> seatsList = createSeats(EVENT_ID);    	
    	int[] bookResult=ticketService.bookTickets(EVENT_ID, seatsList, customerName);
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
     	assertEquals(bookedTicket.size(), seatsList.size()-countNotBook);	
     	assertEquals(bookedCount, countNotBook);	
    }
    
    @Test
    public void getAllTicketsByEventFromBooking(){
    	List<Seat> seatsList = createSeats(EVENT_ID);    	
    	int[] bookResult=ticketService.bookTickets(EVENT_ID, seatsList, customerName);
    	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
    	int countNotBook = 0;
    	int bookedCount = 0;   	
    	for (int r:bookResult){    		
    		if (r!=0) countNotBook++; 
    		if (r==SeatStatus.booked.ordinal()) bookedCount++;
    	}
     	assertEquals(allTickets.size(), seatsList.size()-countNotBook);
     	assertEquals(bookedCount, countNotBook);
        for (int i=0;i<allTickets.size();i++){
        	assertTrue(seatsList.contains(allTickets.get(i).getSeat()));
         	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.booked);
         }         
    }
    
    @Test
    public void bookTickets(){
    	List<Seat> seatsList = createSeats(EVENT_ID);    	    	    	
    	seatsList.add(seatsList.get(5));
    	seatsList.add(seatsList.get(10));
    	
    	int[] bookResult=ticketService.bookTickets(EVENT_ID, seatsList, customerName);
       	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
       	int countNotBook = 0;
    	int bookedCount = 0;   	
    	for (int r:bookResult){
    		if (r!=0) countNotBook++; 
    		if (r==SeatStatus.booked.ordinal()) bookedCount++;
    	}
     	assertEquals(allTickets.size(), seatsList.size()-countNotBook);	
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
    
    
    private List<Seat> parseSeats(String[] seats){
    	List<Seat> result=new ArrayList<Seat>();
    	

    	for (String s:seats){
    		String[] seatString = s.split("[_]");
    		try{
    	    	Seat seat=new Seat();
    	    	Sector sector=new Sector();
    			seat.setSeatNumber(Integer.parseInt(seatString[2]));
    			seat.setRowNumber(Integer.parseInt(seatString[1]));
    			sector = sectorService.findById((Integer.parseInt(seatString[0])));
    			seat.setSector(sector);
    			result.add(seat);
    		}
    		catch(Exception e){
    			result.add(null);
    		}		
    	}      	       
    	return result;
    }
    
    
    
    @Test
    public void submitTicket(){    	
      	List<Seat> seatsList = createSeats(EVENT_ID); 
    	String[] seats = new String[seatsList.size()];
    	int i=0;
	    for (Seat seat:seatsList){
	    	seats[i]=seat.getSector().getId()+"_"+seat.getRowNumber()+"_"+seat.getSeatNumber();
	       	i++;
	    }
	 	
    	int[] bookResult = new int[seats.length];
    	i=0;
    	boolean isTicketkCorrect = true;
    	List<Seat> parseSeatsList=parseSeats(seats);
    	for (Seat st:parseSeatsList){    		
    		if (st.equals(null)){
    			//error of parse ticket
    			bookResult[i]=5;
    			isTicketkCorrect=false;
    		} else {
    			bookResult[i]=ticketService.checkExistTicket(EVENT_ID, st);
    		}
    		i++;
    	}
    	if ((isTicketkCorrect)&&(!customerName.isEmpty())&&(!customerName.equals(null)))
    		bookResult=ticketService.bookTickets(EVENT_ID, parseSeatsList, customerName);
    	
    	List<Ticket> bookedTicket=new ArrayList<Ticket>();
    	for (int sec=1;sec<=27;sec++){
    		bookedTicket.addAll(ticketService.getBookedTicketsBySector(2, sec));
    		for (Ticket sd:bookedTicket){
    			assertTrue(seatsList.contains(sd.getSeat()));
    			assertTrue(parseSeatsList.contains(sd.getSeat()));
    	      	assertEquals(sd.getSeatStatus(),SeatStatus.booked);
    		}
    	}

    	int countNotBook = 0;
    	int bookedCount = 0;   	
    	for (int r:bookResult){
    		if (r!=0) countNotBook++; 
    		if (r==SeatStatus.booked.ordinal()) bookedCount++;
    	}
     	assertEquals(bookedTicket.size(), seatsList.size()-countNotBook);	
     	assertEquals(bookedCount, countNotBook);
    }
}
