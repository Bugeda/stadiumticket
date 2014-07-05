package com.dataartschool2.stadiumticket.dreamteam.web;


import java.io.ObjectOutputStream.PutField;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.NewEventForm;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorPriceService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;

@Controller
public class EventsController{

	@Autowired
	private EventService eventService;
	
	@Autowired
	private SectorService sectorService;
	
	@Autowired
	private SectorPriceService sectorPriceService;
	
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
	
    @RequestMapping(value = "/events/past_events")
    public String getArchiveEvents(Map<String, Object> map) {
    	
    	List<Event> allEvents = eventService.getPastEvents();
    	map.put("events", allEvents);
		   	
        return "/events/past_events";
    }
    

    @RequestMapping(value = "/events/new_event")
    public String new_event(Map<String, Object> map, Model model) {
    	model.asMap().clear();
        return "/events/new_event";
    }
	
	@RequestMapping(value="/events/submitnew_event")
    public String submit_new_event(@ModelAttribute("submit") String submit, @ModelAttribute("newEventForm") NewEventForm evForm, Map<String, Object> map, Model model) throws ParseException {
		model.asMap().clear();
		if (submit.equals("Cancel changes")){ 
			
			return "redirect:/events/new_event";
		}
	
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy HH:mm:ss.S");
		Date d = sdf.parse(evForm.getEventDate()+" 17:00:00.0");
		Timestamp stamp = new Timestamp(d.getTime());	
		Event ev=new Event();
		ev.setEventName(evForm.getEventName());
		ev.setEventDate(stamp);
		ev.setBookingCanceltime(Integer.parseInt(evForm.getBookingCanceltime()));
		eventService.updateEvent(ev);	
		Integer evId=ev.getId();
        SectorPrice sp = null;
        Sector sector=null;
        int sectorId=1;
        for (String e : evForm.getSectorPrice()){
        	System.out.println(e);        	
        	sp=new SectorPrice();
        	sp.setEvent(ev);
        	sector=sectorService.findById(sectorId);        	
        	sp.setSector(sector);
        	sp.setPrice(Double.parseDouble(e));
        	sectorPriceService.updateSectorPrice(sp);
        	sectorId++;        	
        }
        
        return "redirect:/index";     
	}
    @RequestMapping(value = "/events/submitedit_event")
    public String submit_edit_event(@ModelAttribute("submit") String submit, @ModelAttribute("newEventForm") NewEventForm evForm, Map<String, Object> map, Model model) throws ParseException {
    	model.asMap().clear();
    	if (submit.equals("Cancel changes")){ 
			 return "redirect:/events/edit_event?id="+evForm.getId();
		}
   		Integer evId=evForm.getId();
		Event ev=null; 
		ev = eventService.findById(evId);
		if (ev==null){
			JOptionPane.showMessageDialog(null,	"Event with id="+evId +" not found", "Error",  JOptionPane.ERROR_MESSAGE);
			return "/index";
		}
		else
			if (ev.getEventDate().before(new Date()))
				return "/events/past_events";
			else{   
				SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy HH:mm:ss.S");
				Date d = sdf.parse(evForm.getEventDate()+" 17:00:00.0");
				Timestamp stamp = new Timestamp(d.getTime());	
				
				ev.setEventName(evForm.getEventName());
				ev.setEventDate(stamp);
				ev.setBookingCanceltime(Integer.parseInt(evForm.getBookingCanceltime()));
				eventService.updateEvent(ev);	
	
				SectorPrice sp = null;
				Sector sector=null;
				int sectorId=1;
				for (String e : evForm.getSectorPrice()){
               	
					sp=new SectorPrice();
					sp.setEvent(ev);
					sector=sectorService.findById(sectorId);        	
					sp.setSector(sector);
					sp.setPrice(Double.parseDouble(e));
					sectorPriceService.updateSectorPrice(sp);
					sectorId++;        	
				}    
        return "redirect:/index";
		}
    }
	
	@RequestMapping(value = "/events/edit_event")
	public String edit_event(@RequestParam Integer id, Map<String, Object> map, Model model) {	
		model.asMap().clear();
		Event ev=null; 
		ev = eventService.findById(id);
		if (ev==null){
			JOptionPane.showMessageDialog(null,	"Event with id="+id +" not found", "Error",  JOptionPane.ERROR_MESSAGE);
			return "/index";
		}
		else if (ev.isDelete()) {
			JOptionPane.showMessageDialog(null,	"This event has deleted", "warning",  JOptionPane.WARNING_MESSAGE);
			return "/index";
		}
		else
			if (ev.getEventDate().before(new Date())){
				return "/events/past_events";
			}
			else
			{   
				model.asMap().clear();
				map.put("event", ev);
				List<SectorPrice> sps = sectorPriceService.getPricesSectorsOfEvent(ev);
				map.put("sectorPrices", sps);
			
				return "/events/edit_event";
			}
	}
	
	@RequestMapping(value="/events/submitdelete_event")
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
