package com.dataartschool2.stadiumticket.dreamteam.web;


import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.NewEventForm;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorPriceService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;
import com.dataartschool2.stadiumticket.dreamteam.web.validator.NewEventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
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
    private NewEventValidator newEventValidator;

    @InitBinder("newEventForm")
    public void bindNewEventFormValidator(WebDataBinder webDataBinder){
        webDataBinder.setValidator(newEventValidator);
    }

    @ModelAttribute("newEventForm")
    public NewEventForm getNewEventForm(){
        return new NewEventForm();
    }

    @ModelAttribute("editEventForm")
    public NewEventForm getEditEventForm(@RequestParam(value = "id", required = false) Integer id){
        if(id != null) {
            NewEventForm newEventForm = eventService.getEventForm(id);
            if (newEventForm != null) {
                return newEventForm;
            }
        }
        return null;
    }

	@RequestMapping("/")
	public String home(Map<String, Object> map) {
		return "redirect:/index";
	}
	
    @RequestMapping(value = "/index")
    public String getActiveEvents(Map<String, Object> map) {
    	
    	List<Event> allEvents = eventService.getFutureEvents();
    	map.put("events", allEvents);
		   	
        return "/index";
    }
	
    @RequestMapping(value = "/past_events")
    public String getArchiveEvents(Map<String, Object> map) {
    	
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
                                   @Valid @ModelAttribute("newEventForm") NewEventForm newEventForm,
                                   BindingResult bindingResult,
                                   ModelMap modelMap) throws ParseException {
        System.out.println("POST");

        if(bindingResult.hasErrors()){
            System.out.println("POST1");
            modelMap.put("result", bindingResult);
            return "new_event";
        }else{
            System.out.println("POST2");
            if (submit.equals("Cancel changes")){
                System.out.println("POST3");
                return "redirect:/new_event";
            }else{
                System.out.println("Creating");
                eventService.createEvent(newEventForm);
                return "redirect:/index";
            }
        }
	}



    @RequestMapping(value = "/edit_event", method = RequestMethod.POST)
    public String submit_edit_event(@ModelAttribute("submit") String submit,
                                    @Valid @ModelAttribute("editEventForm") NewEventForm editEventForm,
                                    BindingResult bindingResult,
                                    ModelMap modelMap) throws ParseException {

        if(bindingResult.hasErrors()){
            modelMap.put("result", bindingResult);
            return "edit_event";
        }else{
            if (submit.equals("Cancel changes")){
                return "redirect:/edit_event?id="+editEventForm.getId();
            }
            eventService.editEvent(editEventForm);
                return "redirect:/index";
        }

    }



    @RequestMapping(value = "/edit_event" ,method = RequestMethod.GET)
	public String edit_event(@RequestParam("id") Integer id, Map<String, Object> map, Model model) {
		model.asMap().clear();
        Event event = eventService.findById(id);
		if (event==null){
			JOptionPane.showMessageDialog(null,	"Event with id="+id +" not found", "Error",  JOptionPane.ERROR_MESSAGE);
			return "/index";
		}
		else if (event.isDelete()) {
			JOptionPane.showMessageDialog(null,	"This event has deleted", "warning",  JOptionPane.WARNING_MESSAGE);
			return "/index";
		}
		else
			if (event.getEventDate().before(new Date())){
				return "/past_events";
			}
			else
			{   
				model.asMap().clear();
				map.put("event", event);
				List<SectorPrice> sps = sectorPriceService.getPricesSectorsOfEvent(event);
				map.put("sectorPrices", sps);
			
				return "/edit_event";
			}
	}
	
	@RequestMapping(value="/submit/delete_event")
    public String submit_delete_event(@ModelAttribute("newEventForm") NewEventForm evForm, Map<String, Object> map, Model model) {
		model.asMap().clear();
		Event ev=null; 
		
		ev = eventService.findById(evForm.getId());
		
		if (ev==null){
			JOptionPane.showMessageDialog(null,	"Event with id="+evForm.getId() +" not found", "Error",  JOptionPane.ERROR_MESSAGE);
			return "redirect:/index";
		}else if (ev.isDelete()) {
			JOptionPane.showMessageDialog(null,	"This event has deleted", "warning",  JOptionPane.WARNING_MESSAGE);
			return "/index";
		}
		ev.setDelete(true);
		eventService.updateEvent(ev);
		return "redirect:/index";
	}
}
