package com.dataartschool2.stadiumticket.dreamteam.web;


import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorPriceService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;
import com.dataartschool2.stadiumticket.dreamteam.web.validator.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
public class EventsController{

    @Autowired
	private EventService eventService;
	
	@Autowired
	private SectorService sectorService;
	
	@Autowired
	private SectorPriceService sectorPriceService;

    @Autowired
    private EventValidator eventValidator;

    @InitBinder("newEvent")
    public void bindNewEventFormValidator(WebDataBinder webDataBinder){
        webDataBinder.setValidator(eventValidator);
    }

    @InitBinder("editEvent")
    public void bindEditEventValidator(WebDataBinder webDataBinder){
        webDataBinder.setValidator(eventValidator);
    }

    @ModelAttribute("newEvent")
    public Event getNewEvent(){
        Event event = eventService.createEmptyEvent();
        return event;
    }

    @ModelAttribute("editEvent")
    public Event getEditEvent(@RequestParam(value = "id", required = false) Integer id){
        if(id != null) {

            Event event = eventService.findById(id);
            if (event != null) {
                return event;
            }
        }
        return null;
    }

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Map<String, Object> map) {
		return "redirect:/index";
	}
	
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getActiveEvents(ModelMap map) {
    	
    	List<Event> allEvents = eventService.getFutureEvents();
    	map.put("events", allEvents);
		   	
        return "/index";
    }
	
    @RequestMapping(value = "/past_events", method = RequestMethod.GET)
    public String getArchiveEvents(ModelMap map) {
    	
    	List<Event> allEvents = eventService.getPastEvents();
    	map.put("events", allEvents);
		   	
        return "/past_events";
    }
    

    @RequestMapping(value = "/new_event", method = RequestMethod.GET)
    public String new_event() {
        return "new_event";
    }
	
	@RequestMapping(value="/new_event", method = RequestMethod.POST)
    public String submit_new_event(@ModelAttribute("submit") String submit,
                                   @Valid @ModelAttribute("newEvent") Event event,
                                   BindingResult bindingResult,
                                   ModelMap modelMap) throws ParseException {

            if (submit.equals("Cancel changes")){
                return "redirect:/new_event";
            }else{
                if(bindingResult.hasErrors()){
                    modelMap.put("result", bindingResult);
                    return "new_event";
                }else{
                    eventService.createEvent(event);
                    return "redirect:/index";
                }
            }
	}



    @RequestMapping(value = "/edit_event", method = RequestMethod.POST)
    public String submit_edit_event(@ModelAttribute("submit") String submit,
                                    @Valid @ModelAttribute("editEvent") Event event,
                                    BindingResult bindingResult,
                                    ModelMap modelMap) throws ParseException {


            if (submit.equals("Cancel changes")){
                return "redirect:/edit_event?id="+event.getId();
            }else {
                if(bindingResult.hasErrors()){
                    modelMap.put("result", bindingResult);
                    return "edit_event";
                }else{
                    eventService.updateEvent(event);
                    return "redirect:/index";
            }
        }
    }



    @RequestMapping(value = "/edit_event" ,method = RequestMethod.GET)
	public String edit_event() {
        return "edit_event";
	}
	
	@RequestMapping(value="/delete_event", method = RequestMethod.POST)
    public String submit_delete_event(@ModelAttribute("editEvent") Event event) {
        eventService.markAsDeleted(event);
		return "redirect:/index";
	}
	
	   @RequestMapping(value = "/booking/book_tickets")
	    public String book_tickets(@RequestParam Integer id, Map<String, Object> map, Model model) {
			model.asMap().clear();
			Event ev=null; 
			ev = eventService.findById(id);
			if ((ev==null)||(ev.isDelete())){
				JOptionPane.showMessageDialog(null,	"Event with id="+id +" not found", "Error",  JOptionPane.ERROR_MESSAGE);
				return "redirect:/index";
			}
			else
				if (ev.getEventDate().before(new Date())){
					return "redirect:/index";
				}
				else
				{   
					model.asMap().clear();
					map.put("event", ev);
					List<SectorPrice> sps = sectorPriceService.getPricesSectorsOfEvent(ev);
					map.put("sectorPrices", sps);
				
					return "/booking/book_tickets";
				}
	    }
	  	   
}
