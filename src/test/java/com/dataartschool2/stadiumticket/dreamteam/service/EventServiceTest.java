package com.dataartschool2.stadiumticket.dreamteam.service;

<<<<<<< HEAD
=======
import static org.junit.Assert.*;

import com.dataartschool2.stadiumticket.dreamteam.dao.EventDAO;
>>>>>>> master
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;

import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;

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
    	
        Event expected = new Event(15, "1", stamp, 30, new ArrayList<SectorPrice>());
    	eventService.updateEvent(expected);
    	
        Event actual = eventService.findById((Integer) 15);        
        Assert.assertEquals(expected, actual);
        Assert.assertNotNull(actual);
    }
    
    @Test
    public void deleteEventTest(){        
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());  	
    	Event template = new Event(15, "1", stamp, 30, new ArrayList<SectorPrice>());
    	
    	Event expected = new Event(15, "1", stamp, 30, new ArrayList<SectorPrice>());         
    	eventService.updateEvent(expected);
   	
    	Event actual = eventService.findById(15);
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(expected, actual);
    	
    	actual.setDelete(true);
    	eventService.updateEvent(actual);
    	Event lastActual = eventService.findById(15);        
        assertNotNull(lastActual);
        Assert.assertEquals(lastActual, actual);
        Assert.assertNotSame(template, actual);        
    }
    
    @Test
    public void editEventTest(){       
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
    	
        //new event
<<<<<<< HEAD
	Event begEvent = new Event(15, "1", stamp, 30, new ArrayList<SectorPrice>());
	Event dbEvent = new Event(15, "1", stamp, 30, new ArrayList<SectorPrice>());

        
        //insert in db
	eventService.updateEvent(dbEvent);
	Event currentEvent = eventService.findById(15);    	        
    	Assert.assertNotNull(currentEvent);
    	Assert.assertEquals(dbEvent, currentEvent);  

    	//change event    	
    	stamp.setYear(10);
    	stamp.setMonth(8);
    	stamp.setDate(25);  
    	//eventService
    	dbEvent.setEventDate(stamp);
    	dbEvent.setEventName("testname");
    	dbEvent.setBookingCanceltime(20);
    	eventService.updateEvent(dbEvent);
    	Assert.assertNotSame(dbEvent, template);

    	Event actual = eventService.findById(15);
    	Assert.assertEquals(dbEvent, actual);    	
        Assert.assertNotSame(template, actual);
        Assert.assertNotNull(actual);       
    }
    
    @Test
    public void createEventTest(){       
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
    	
        //new event
    	Event newEvent = new Event(15, "1", stamp, 30, new ArrayList<SectorPrice>());
        eventService.updateEvent(newEvent);                       

        Event currentEvent = eventService.findById(15);    	        
    	Assert.assertNotNull(currentEvent);
    	Assert.assertEquals(newEvent, currentEvent);  
    }
}