package com.dataartschool2.stadiumticket.dreamteam.web;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;

@Controller
public class HelloController{

	@Autowired
	private EventService eventService;
	
	
	@RequestMapping("/")
	public String home(Map<String, Object> map) {
		return "redirect:/index";
	}
	
    @RequestMapping(value = "/index")
    public String getEvents(Map<String, Object> map) {
    	
    	List<Event> allEvents = eventService.getEvents();
		eventService.sysoutMessage("hi there");
    	map.put("events", allEvents);
		   	
        return "index";
    }
	
	
	@RequestMapping("new_event")
	public String new_event() {
		return "new_event";
	}

	
}
