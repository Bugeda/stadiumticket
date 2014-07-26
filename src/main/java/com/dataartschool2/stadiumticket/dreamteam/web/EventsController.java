package com.dataartschool2.stadiumticket.dreamteam.web;


import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
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
public class EventsController{

	@Autowired
	private ApplicationContext appContext;
	  
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
        return eventService.createEmptyEvent();
    }

    @ModelAttribute("editEvent")
    public Event getEvent(@RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException{
    
         if(id != null) {

            Event event = eventService.findById(id);
            if (event != null) {
           	if (event.getEventDate().before(new Date())){           			            	
          			throw new RuntimeException(appContext.getMessage("error.archiveEvent", new Object[]{}, null));
            		}
            	else
                return event;
            }else{
            	throw new RuntimeException(appContext.getMessage("error.noEvent", new Object[]{}, null));
            }
        }
    return null;        
    }
       
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Map<String, Object> map, Model model ) {
		model.asMap().clear();
		return "redirect:/index";
	}
	
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String getActiveEvents(ModelMap map,  Model model) {  
    	model.asMap().clear();
    	List<Event> allEvents = eventService.getFutureEvents();    
    	map.put("events", allEvents);
        return "./index";
    }
	
    @RequestMapping(value = "/past_events", method = RequestMethod.GET)
    public String getArchiveEvents(ModelMap modelMap, Model model) {    	
    	model.asMap().clear();
    	List<Event> allEvents = eventService.getPastEvents();
    	modelMap.put("events", allEvents);
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
			modelMap.remove("submit");
            if (submit.equals(appContext.getMessage("event.cancel", new Object[]{}, null))){
                return "redirect:/new_event";
            }else{
                if(bindingResult.hasErrors()){
                 	JOptionPane.showMessageDialog(null, appContext.getMessage("error.eventIsNotAdded", new Object[]{}, null),
                    		"event message", JOptionPane.ERROR_MESSAGE);
                 	
                    modelMap.put("result", bindingResult);
                    return "new_event";
                }else{
                    eventService.createEvent(event);
                    JOptionPane.showMessageDialog(null, appContext.getMessage("message.eventIsAdded", new Object[]{}, null),
                    		"event message", JOptionPane.INFORMATION_MESSAGE);
                    return "redirect:/index";
                }
            }
	}


    @RequestMapping(value = "/edit_event", method = RequestMethod.POST)
    public String submit_edit_event(@ModelAttribute("submit") String submit,
                                    @Valid @ModelAttribute("editEvent") Event event,
                                    BindingResult bindingResult,
                                    ModelMap modelMap) throws ParseException {
    		modelMap.remove("submit");
            if (submit.equals(appContext.getMessage("event.cancel", new Object[]{}, null))){
                return "redirect:/edit_event?id="+event.getId();
            }else {
                if(bindingResult.hasErrors()){            
                    JOptionPane.showMessageDialog(null, appContext.getMessage("error.changesAreNotApplied", new Object[]{}, null), 
                    		"event message", JOptionPane.ERROR_MESSAGE);
                    modelMap.put("result", bindingResult);       
                    return "edit_event";
                }else{
                    eventService.updateEvent(event);            
                    JOptionPane.showMessageDialog(null, appContext.getMessage("message.changesAreApplied", new Object[]{}, null),
                    		"event message", JOptionPane.INFORMATION_MESSAGE);
                    return "redirect:/";
            }
        }
    }


    @RequestMapping(value = "/edit_event" ,method = RequestMethod.GET)
	public String edit_event() {
        return "edit_event";
	}
    

	@RequestMapping(value="/delete_event", method = RequestMethod.POST)
    public String submit_delete_event(@ModelAttribute("editEvent") Event event, Model model) {
		model.asMap().clear();
        eventService.markAsDeleted(event);
        eventService.updateEvent(event);
		return "redirect:/index";
	}
}
