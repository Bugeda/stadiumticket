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
    
    private SeatsForm createSeatsForm(String customerName, Integer eventId){
    	Event event = eventService.findById(eventId);      	
		assertNotNull(event);
       	SeatsForm seatsForm = new SeatsForm();
		seatsForm.setCustomerName(customerName);
		Integer[] usedRow= new Integer[10];
       	Integer[] usedSeat= new Integer[10];
    	List<Integer> usedSectors= new ArrayList<Integer>();
    	List<Seat> chosenSeats = new ArrayList<Seat>();
    	
    	for (int sec=0;sec<27;sec++){
    		Integer sectorId= (int)(Math.random()*26+1);
    	
    		for (int i=0;i<10;i++){
    			usedSectors.add(sectorId);
    			usedRow[i] = (int)(Math.random()*19+1);
    			usedSeat[i] = (int)(Math.random()*49+1);
    			Seat seat=new Seat(0, usedSeat[i], usedRow[i], sectorService.findById(sectorId));
    			chosenSeats.add(seat);
    			assertNotNull(seat);   			
    		}
    	}
    	seatsForm.setChosenSectorsNums(usedSectors);
        seatsForm.setChosenSeats(chosenSeats);  
        return seatsForm;
    }
    
    @Test
    public void getSoldTicketsBySector(){
    	SeatsForm seatsForm = createSeatsForm("not used", 2); 
    	ticketService.sellTickets(2, seatsForm);
    	List<Ticket> soldTicket=new ArrayList<Ticket>();
    	for (int sec=1;sec<=27;sec++){
    		soldTicket.addAll(ticketService.getSoldTicketsBySector(2, sec));
    		for (Ticket sd:soldTicket){
    			assertTrue(seatsForm.getChosenSeats().contains(sd.getSeat()));
    	      	assertEquals(sd.getSeatStatus(),SeatStatus.occupied);
    		}
    	}
    	assertEquals(soldTicket.size(), 270);	
    }
    
    @Test
    public void getAllTicketsByEventFromSell(){
    	SeatsForm seatsForm = createSeatsForm("not used", 2);
    	ticketService.sellTickets(2, seatsForm);
    	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
    	assertEquals(allTickets.size(), 270);
    	for (int i=0;i<allTickets.size();i++){
    		assertTrue(seatsForm.getChosenSeats().contains(allTickets.get(i).getSeat()));
         	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.occupied);
         }            
    }
    
    @Test
    public void sellTickets(){
    	SeatsForm seatsForm = createSeatsForm("not used", 2); 
    	List<Seat> st = seatsForm.getChosenSeats(); 
    	List<Integer> sc = seatsForm.getChosenSectorsNums();
    	st.add(seatsForm.getChosenSeats().get(5));
    	sc.add(seatsForm.getChosenSectorsNums().get(5));
       	st.add(seatsForm.getChosenSeats().get(10));
    	sc.add(seatsForm.getChosenSectorsNums().get(10));
    	seatsForm.setChosenSeats(st);
    	seatsForm.setChosenSectorsNums(sc);
    	
    	ticketService.sellTickets(2, seatsForm);
       	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
       	assertEquals(allTickets.size(), 270);
        for (int i=0;i<allTickets.size();i++){
        	assertTrue(seatsForm.getChosenSeats().contains(allTickets.get(i).getSeat()));
        	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.occupied);
        }   
		boolean different = true;
        for (Ticket tk:allTickets){
        	List<Ticket> identicalTickets = ticketService.findByNumber(tk.getTicketNumber());
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
    	SeatsForm seatsForm = createSeatsForm("new customer", 2); 
    	ticketService.bookTickets(2, seatsForm);
    	List<Ticket> bookedTicket=new ArrayList<Ticket>();
    	for (int sec=1;sec<=27;sec++){
    		bookedTicket.addAll(ticketService.getBookedTicketsBySector(2, sec));
    		for (Ticket sd:bookedTicket){
    			assertTrue(seatsForm.getChosenSeats().contains(sd.getSeat()));
    	      	assertEquals(sd.getSeatStatus(),SeatStatus.booked);
    		}
    	}
    	assertEquals(bookedTicket.size(), 270);	
    }
    
    @Test
    public void getAllTicketsByEventFromBooking(){
    	SeatsForm seatsForm = createSeatsForm("new customer", 2);
    	ticketService.bookTickets(2, seatsForm);
    	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
    	assertEquals(allTickets.size(), 270);
        for (int i=0;i<allTickets.size();i++){
        	assertTrue(seatsForm.getChosenSeats().contains(allTickets.get(i).getSeat()));
         	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.booked);
         }         
    }
    
    @Test
    public void bookTickets(){
    	SeatsForm seatsForm = createSeatsForm("new customer", 2); 
    	List<Seat> st = seatsForm.getChosenSeats(); 
    	List<Integer> sc = seatsForm.getChosenSectorsNums();
    	st.add(seatsForm.getChosenSeats().get(5));
    	sc.add(seatsForm.getChosenSectorsNums().get(5));
       	st.add(seatsForm.getChosenSeats().get(10));
    	sc.add(seatsForm.getChosenSectorsNums().get(10));
    	seatsForm.setChosenSeats(st);
    	seatsForm.setChosenSectorsNums(sc);
    	
    	ticketService.bookTickets(2, seatsForm);
       	List<Ticket> allTickets = ticketService.getAllTicketsByEvent(2); 
       	assertEquals(allTickets.size(), 270);
        for (int i=0;i<allTickets.size();i++){
        	assertTrue(seatsForm.getChosenSeats().contains(allTickets.get(i).getSeat()));
        	assertEquals(allTickets.get(i).getSeatStatus(),SeatStatus.booked);
        }   
		boolean different = true;
        for (Ticket tk:allTickets){
        	List<Ticket> identicalTickets = ticketService.findByNumber(tk.getTicketNumber());
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
