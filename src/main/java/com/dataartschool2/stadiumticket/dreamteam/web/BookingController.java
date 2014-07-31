package com.dataartschool2.stadiumticket.dreamteam.web;

import java.text.ParseException;
import java.util.Date;






import javax.swing.JOptionPane;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.service.BookingService;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;


@Controller
public class BookingController{
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	BookingService bookingService;
	
    @Autowired
	private EventService eventService;
	
    
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
    
	@RequestMapping(value = "/booking/search", method = RequestMethod.GET)
	public String getBookedTickets(@RequestParam("id") Integer eventId, ModelMap modelMap,  Model model){
		modelMap.put("event", eventService.findById(eventId));
	   	return "/booking/search";
	}	
	
	@RequestMapping(value="/booking/search", method = RequestMethod.POST)
    public String getBookedTicketsForCustomer(@RequestParam("id") Integer eventId,
    										  @RequestParam("customerName") String customerName,
    										  ModelMap modelMap,  Model model) throws ParseException {
		modelMap.put("customerName", customerName);
		modelMap.put("event", eventService.findById(eventId));
		modelMap.put("bookingSet", bookingService.findLikeCustomerNameInEvent(eventId, customerName));
	    return "/booking/search";
		}
}
