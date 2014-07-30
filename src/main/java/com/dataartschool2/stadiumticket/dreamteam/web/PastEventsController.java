package com.dataartschool2.stadiumticket.dreamteam.web;


import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import com.dataartschool2.stadiumticket.dreamteam.service.*;
import com.dataartschool2.stadiumticket.dreamteam.web.validator.EventValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import javax.validation.Valid;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class PastEventsController{

	@Autowired
	private ApplicationContext appContext;
	   
    @Autowired
	private EventService eventService;
	
	@Autowired
	private SectorService sectorService;
	
	@Autowired
	private SectorPriceService sectorPriceService;

	   @ModelAttribute("editEvent")
	    public Event getEvent(@RequestParam(value = "id", required = false) Integer id) throws IOException{
	    
	         if(id != null) {

	            Event event = eventService.findById(id);
	            if (event != null)
	
	                return event;
	         }
	return null;  
	    }
	   
    @ModelAttribute("newCustomer")
    public SeatsForm getNewSeatsForm(@RequestParam("id") Integer eventId){
    	SeatsForm seatsForm=new SeatsForm(); 
    	seatsForm.setEventId(eventId);
        return seatsForm;
    }
    
    @RequestMapping(value = "/past/view_event", method = RequestMethod.GET)
    public String getView_event(@RequestParam("id") Integer eventId, ModelMap modelMap) {    	
       	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices); 
        return "/past/view_event";
    }
    
    
    @RequestMapping(value = "/past/view_tickets", method = RequestMethod.GET)
    public String getBookTickets(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(eventService.findById(eventId));
    	modelMap.put("event", eventService.findById(eventId));
    	modelMap.put("sectorPrices", sectorPrices);    
        return "/past/view_tickets";
    }

    
    
}
