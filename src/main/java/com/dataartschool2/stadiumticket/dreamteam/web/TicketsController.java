package com.dataartschool2.stadiumticket.dreamteam.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;
import com.dataartschool2.stadiumticket.dreamteam.service.SeatService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorPriceService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;
import com.dataartschool2.stadiumticket.dreamteam.service.TicketService;


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
    

    
    @RequestMapping(value = "/tickets/sell", method = RequestMethod.GET)
    public String getSellTickets(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
       	modelMap.put("chosenSeats.event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);
        return "/tickets/sell_tickets";
    }
     
    @RequestMapping(value = "/tickets/book", method = RequestMethod.GET)
    public String getBookTickets(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);    
        return "/tickets/book_tickets";
    }

 }
