package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.SeatStatus;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;
import com.dataartschool2.stadiumticket.dreamteam.service.BookingService;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;
import com.dataartschool2.stadiumticket.dreamteam.service.SeatService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorPriceService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;
import com.dataartschool2.stadiumticket.dreamteam.service.TicketService;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;









import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;


@Controller
public class TicketsController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SectorService sectorService;
    
	@Autowired
	private SectorPriceService sectorPriceService;

	@Autowired
	BookingService bookingService;
	
	@Autowired
	SeatService seatService;
	
	@Autowired
	EventsController eventsController;

    @ModelAttribute("event")
    public Event getEvent(@RequestParam(value = "id", required = false) Integer eventId){  
        if(eventId != null) {        	
            Event event = eventService.findById(eventId);
            if (event != null) {
           	if (event.getEventDate().before(new Date())){
           		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/root-context.xml");
            		throw new RuntimeException(applicationContext.getMessage("error.archiveEvent", new Object[]{}, null));
            		}
              
            }else{
            	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/root-context.xml");
        		throw new RuntimeException(applicationContext.getMessage("error.noEventChosen", new Object[]{}, null));
            }
        }
        return null;
    }
    
    @ModelAttribute("chosenSeats")
    public List<Seat> chosenSeats(){
    	return  new ArrayList<Seat>();
    }
    
    @RequestMapping(value = "/tickets/sell", method = RequestMethod.GET)
    public String getSellTicketsPage(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);
        return "/tickets/sell_tickets";
    }

    @RequestMapping(value = "/tickets/sell", method = RequestMethod.POST)
    public String submit_SellTicketsPage(@Valid @ModelAttribute("chosenSeats") List<Seat> chosenSeats,
    									 @ModelAttribute("event") Event event,                          
            							 BindingResult seatsBindingResult,
            							 ModelMap modelMap){
    	for (Seat st:chosenSeats){
    	System.out.println(st.getRowNumber());
    	System.out.println(st.getSeatNumber());
      	System.out.println(st.getSector().getId()); 
    	}
        if(seatsBindingResult.hasErrors()){
            modelMap.put("result", seatsBindingResult);
            return "/tickets/error";
        }else{
            ticketService.sellTickets(event, chosenSeats);
        }        
        return "/tickets/sell_tickets";
      //  return "redirect:/index";
    }
    
    
    @ModelAttribute("newCustomer")
    public SeatsForm getNewSeatsForm(){
    	SeatsForm sf=new SeatsForm();
    	sf.setBooking(true);    
        return sf;
    }
    
      
    @RequestMapping(value = "/tickets/book", method = RequestMethod.GET)
    public String getBookTicketsPage(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);
    
        return "/tickets/book_tickets";
    }

    @RequestMapping(value = "/tickets/book", method = RequestMethod.POST)
    public String submit_bookTicketsPage(@Valid @ModelAttribute("newCustomer") SeatsForm seatsForm,
    		 							// @ModelAttribute("event") Event event,      -  не существует      									 
            							 BindingResult seatsBindingResult,
            							 ModelMap modelMap){   
        if(seatsBindingResult.hasErrors()){
            modelMap.put("result", seatsBindingResult);
            return "/tickets/book_tickets";
        }else{
          	seatsForm.getChosenSeats().remove(0);
        	seatsForm.getChosenSectorsNums().remove(0);
        	System.out.println(seatsForm.getEventId());
        	List<Sector> sectorSet=sectorService.createSectorsListFromNums(seatsForm.getChosenSectorsNums());  
            List<Seat> seatSet = seatService.modifySeatSet(seatsForm.getChosenSeats().size(), seatsForm.getChosenSeats(), sectorSet);
         	Customer customer =  new Customer();
        	customer.setCustomerName(seatsForm.getCustomerName());        	
            ticketService.bookTickets(seatsForm.getEventId(), customer, seatSet);
        }        
        //return "/tickets/book_tickets";
        return "redirect:/index";
    }
}
