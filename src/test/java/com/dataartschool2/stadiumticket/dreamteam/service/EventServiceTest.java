package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml"})
@Transactional
public class EventServiceTest{

	@Autowired
    private EventService eventService;

    @Test
    public void readEventTest(){
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
    	
        Event expected = new Event(15, "1", stamp, 30, 60, new ArrayList<SectorPrice>());
        Event actual = eventService.updateEvent(expected);
    	    
        assertEquals(expected, actual);
        assertNotNull(actual);
    }
    
    @Test
    public void deleteEventTest(){     
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
    	Event template = new Event(15, "1", stamp, 30, 60, new ArrayList<SectorPrice>());   	
        Event event = new Event(15, "1", stamp, 30, 60, new ArrayList<SectorPrice>());
        Event actual = eventService.updateEvent(event);
    	
    	assertNotNull(actual);
    	assertEquals(event, actual);
  
    	eventService.markAsDeleted(event);;
        actual = eventService.findById(15);        
        assertNotNull(actual);
        assertNotSame(actual,template);
    }
    
    @Test
    public void editEventTest(){    
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
    	
        //new event
    	Event begEvent = new Event(15, "1", stamp, 30, 60, new ArrayList<SectorPrice>());
        Event dbEvent = new Event(15, "1", stamp, 30, 60, new ArrayList<SectorPrice>());
        
        //insert in db
        Event currentEvent = eventService.updateEvent(dbEvent);
 	        
    	assertNotNull(currentEvent);
    	assertEquals(dbEvent, currentEvent);  

    	//change event    	
    	stamp.setYear(10);
    	stamp.setMonth(8);
    	stamp.setDate(25);  
    	//eventService
    	dbEvent.setEventDate(stamp);
    	dbEvent.setEventName("testname");
    	eventService.updateEvent(dbEvent);
    	assertNotSame(dbEvent, begEvent);

    	Event actual = eventService.findById(15);
    	assertEquals(dbEvent, actual);    	
        assertNotSame(begEvent, actual);
        assertNotNull(actual);       
    }
    
    @Test
    public void createEventTest(){
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
    	
        //new event
    	Event newEvent = new Event(15, "1", stamp, 30, 60, new ArrayList<SectorPrice>());
    	Event currentEvent = eventService.updateEvent(newEvent);                       

    	assertNotNull(currentEvent);
    	assertEquals(newEvent, currentEvent);  
    }
    
    @Test
    public void createIdenticalEventTest(){
    	Event existEvent = eventService.findById(2); 
    	assertNotNull(existEvent);  
    	Event newEvent = new Event();
      	newEvent.setEventName(existEvent.getEventName());
    	newEvent.setEventDate(existEvent.getEventDate());
    	newEvent.setDurationTime(existEvent.getDurationTime());
    	newEvent.setBookingCancelTime(existEvent.getBookingCancelTime());
    	newEvent.setDelete(existEvent.isDelete());
       	newEvent.setSectorPriceSet(existEvent.getSectorPriceSet());
       	Event isFoundEvent = eventService.updateEvent(newEvent); 
    	assertFalse(existEvent.equals(isFoundEvent));    
        assertNull(isFoundEvent); 
    
    }
}