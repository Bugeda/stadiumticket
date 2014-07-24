package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.domain.*;
import com.dataartschool2.stadiumticket.dreamteam.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Date;
import java.util.List;


@Controller
public class TicketsController {

	@Autowired
	private ApplicationContext appContext;
	
    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SectorService sectorService;
    
	@Autowired
	private SectorPriceService sectorPriceService;

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
            		throw new RuntimeException(appContext.getMessage("error.archiveEvent", new Object[]{}, null));
            		}
              
            }else{
        		throw new RuntimeException(appContext.getMessage("error.noEvent", new Object[]{}, null));
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
    public String getSellTickets(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);
        return "/tickets/sell_tickets";
    }

    @RequestMapping(value = "/tickets/sell/{id}", method = RequestMethod.POST)
    public String submit_SellTickets(@PathVariable("id") Integer eventId,
                                         @Valid @ModelAttribute("chosenSeats") SeatsForm seatsForm,
             							 BindingResult seatsBindingResult,
            							 ModelMap modelMap){
        if(seatsBindingResult.hasErrors()){
            modelMap.put("result", seatsBindingResult);
            return "redirect:/tickets/sell?id="+eventId;
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
    public String getBookTickets(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);    
        return "/tickets/book_tickets";
    }

    @RequestMapping(value = "/tickets/book/{id}", method = RequestMethod.POST)
    public String submit_bookTickets(@PathVariable("id") Integer eventId,
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
        return "redirect:/index";
    }
}
