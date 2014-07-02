package com.dataartschool2.stadiumticket.dreamteam.web;


import java.io.ObjectOutputStream.PutField;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public String new_event(Map<String, Object> map) {
	
		return "/events/new_event";
	}

	@RequestMapping(value="/manager/new_event", method = RequestMethod.POST)
    public String updateEvent(@ModelAttribute("newEventForm") NewEventForm evForm) throws Throwable {
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy HH:mm:ss.S");
		Date d = sdf.parse(evForm.getEventDate()+" 17:00:00.0");
		Timestamp stamp = new Timestamp(d.getTime());	
		Event ev=new Event();
		ev.setEventName(evForm.getEventName());
		ev.setEventDate(stamp);
		eventService.updateEvent(ev);	
		Integer evId=ev.getId();
        System.out.println(evId);
        SectorPrice sp = null;
        Sector sector=null;
        int sectorId=1;
        for (String e : evForm.getSectorPrice()){
        //	System.out.println(e);        	
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

	@RequestMapping(value = "/events/edit_event")
	public String edit_event(@RequestParam Integer id, Map<String, Object> map) {
		Event ev=null; 
		try {
		 ev = eventService.findById(id);
		 }
		 catch (Exception e) {  
			    //	System.out.println(e.getMessage());
				}
		if (ev==null)
			return "redirect:/index";
		else
			if (ev.getEventDate().before(new Date()))
				return "redirect:/events/past_events";
			else
			{
				map.put("event", ev);
				List<SectorPrice> sps = sectorPriceService.getPricesSectorsOfEvent(ev);
				map.put("SectorPrices", sps);
				return "/events/edit_event";
			}
	}
}
