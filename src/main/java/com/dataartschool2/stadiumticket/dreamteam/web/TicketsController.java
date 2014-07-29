package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.domain.*;
import com.dataartschool2.stadiumticket.dreamteam.service.*;
import com.dataartschool2.stadiumticket.dreamteam.web.validator.EventValidator;
import com.dataartschool2.stadiumticket.dreamteam.web.validator.TicketsValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    TicketsValidator ticketsValidator;
    
    @InitBinder("newCustomer")
    public void bindBookValidator(WebDataBinder webDataBinder){
        webDataBinder.setValidator(ticketsValidator);
    }
    
    @InitBinder("chosenSeats")
    public void bindSellkValidator(WebDataBinder webDataBinder){
        webDataBinder.setValidator(ticketsValidator);
    }
    
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
    public SeatsForm chosenSeats(@RequestParam("id") Integer eventId){
        SeatsForm seatsForm = new SeatsForm();
        seatsForm.setCustomerName("not used");
        seatsForm.setEventId(eventId);
    	return seatsForm;
    }
    
    @RequestMapping(value = "/tickets/sell", method = RequestMethod.GET)
    public String getSellTickets(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
       	modelMap.put("chosenSeats.event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);
        return "/tickets/sell_tickets";
    }

    @RequestMapping(value = "/tickets/sell", method = RequestMethod.POST)
    public String submit_SellTickets(@ModelAttribute("id") Integer eventId,
                                         @Valid @ModelAttribute("chosenSeats") SeatsForm seatsForm,
             							 BindingResult seatsBindingResult,
            							 ModelMap modelMap,  
                                         RedirectAttributes attr){
    	//seatsForm.setEvent(`);
        if(seatsBindingResult.hasErrors()){
        	modelMap.put("event", eventService.findById(eventId));
        	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
        	modelMap.put("sectorPrices", sectorPrices);   
        	modelMap.put("chosenSeats", seatsForm);
            modelMap.put("results", seatsBindingResult);
            return "/tickets/sell_tickets";
        }else{
          	seatsForm.getChosenSeats().remove(0);
        	seatsForm.getChosenSectorsNums().remove(0);
            Boolean[] result=ticketService.sellTickets(eventId, seatsForm);
            attr.addFlashAttribute("message", appContext.getMessage("message.ticketsAreSold", new Object[]{}, null));
            return "redirect:/";
        }
    }
    
    
    @ModelAttribute("newCustomer")
    public SeatsForm getNewSeatsForm(@RequestParam("id") Integer eventId){
    	SeatsForm seatsForm=new SeatsForm(); 
    	seatsForm.setEventId(eventId);
        return seatsForm;
    }
    
      
    @RequestMapping(value = "/tickets/book", method = RequestMethod.GET)
    public String getBookTickets(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);    
        return "/tickets/book_tickets";
    }

    @RequestMapping(value = "/tickets/book", method = RequestMethod.POST)
    public String submit_bookTickets(@ModelAttribute("id") Integer eventId,
    									 @Valid @ModelAttribute("newCustomer") SeatsForm seatsForm,
            							 BindingResult seatsBindingResult,
            							 ModelMap modelMap,  
                                         RedirectAttributes attr){   
    	
         if(seatsBindingResult.hasErrors()){
        	modelMap.put("event", eventService.findById(eventId));
        	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
        	modelMap.put("newCustomer", seatsForm);
        	modelMap.put("sectorPrices", sectorPrices);   
            modelMap.put("results", seatsBindingResult);
            return "/tickets/book_tickets";
        }else{
          	seatsForm.getChosenSeats().remove(0);
        	seatsForm.getChosenSectorsNums().remove(0);              	
        	Boolean[] result=ticketService.bookTickets(eventId, seatsForm);
            attr.addFlashAttribute("message", appContext.getMessage("message.ticketsAreBooked", new Object[]{}, null));
            return "redirect:/index";
        }        

    }
}
