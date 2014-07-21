package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.domain.*;
import com.dataartschool2.stadiumticket.dreamteam.service.*;

import org.hibernate.mapping.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public SeatsForm chosenSeats(){
        SeatsForm seatsForm = new SeatsForm();
        seatsForm.setCustomerName("not used");
    	return seatsForm;
    }
    
    @RequestMapping(value = "/tickets/sell", method = RequestMethod.GET)
    public String getSellTicketsPage(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);
        return "/tickets/sell_tickets";
    }

    @RequestMapping(value = "/tickets/sell/{id}", method = RequestMethod.POST)
    public String submit_SellTicketsPage(@PathVariable("id") Integer eventId,
                                         @Valid @ModelAttribute("chosenSeats") SeatsForm seatsForm,
             							 BindingResult seatsBindingResult,
            							 ModelMap modelMap){
        if(seatsBindingResult.hasErrors()){
            modelMap.put("result", seatsBindingResult);
            return "error";
        }else{
            ticketService.sellTickets(eventId, seatsForm);
            return "redirect:/";
        }
    }
    
    
    @ModelAttribute("newCustomer")
    public SeatsForm getNewSeatsForm(){
    	SeatsForm sf=new SeatsForm(); 
        return sf;
    }
    
      
    @RequestMapping(value = "/tickets/book", method = RequestMethod.GET)
    public String getBookTicketsPage(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);
    
        return "/tickets/book_tickets";
    }

    @RequestMapping(value = "/tickets/book/{id}", method = RequestMethod.POST)
    public String submit_bookTicketsPage(@PathVariable("id") Integer eventId,
    									@Valid @ModelAttribute("newCustomer") SeatsForm seatsForm,
            							 BindingResult seatsBindingResult,
            							 ModelMap modelMap){   
        if(seatsBindingResult.hasErrors()){
            modelMap.put("result", seatsBindingResult);
            return "/tickets/book_tickets";
        }else{
          	seatsForm.getChosenSeats().remove(0);
        	seatsForm.getChosenSectorsNums().remove(0);
         	List<Sector> sectorSet=sectorService.createSectorsListFromNums(seatsForm.getChosenSectorsNums());  
            	List<Seat> seatSet = seatService.modifySeatSet(seatsForm.getChosenSeats().size(), seatsForm.getChosenSeats(), sectorSet);

         	Customer customer =  new Customer();
        	customer.setCustomerName(seatsForm.getCustomerName());        	
            ticketService.bookTickets(eventId, customer, seatSet);
        }        
        //return "/tickets/book_tickets";
        return "redirect:/index";
    }
}
