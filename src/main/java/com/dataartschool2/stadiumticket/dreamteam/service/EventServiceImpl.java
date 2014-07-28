package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.dao.EventDAO;
import com.dataartschool2.stadiumticket.dreamteam.dao.SectorDAO;
import com.dataartschool2.stadiumticket.dreamteam.dao.SectorPriceDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class EventServiceImpl implements EventService{

    public static final int SECTORS_COUNT = 27;
    public static final long ONE_MINUTE_IN_MILLIS=60000;
    
    @Autowired
	private EventDAO eventDAO;
	
	@Autowired
	private SectorPriceDAO sectorPriceDAO;

    @Autowired
    private SectorDAO sectorDAO;

    @Override
    public void markAsDeleted(Event event) {
        event.setDelete(true);            
    }

    @Override
	@Transactional
    public Boolean checkEventDate(Event newEvent){
    	Boolean result = false;   	
        Integer duration=newEvent.getDurationTime();
        System.out.println(newEvent.getDurationTime());
    	if ((duration==null)||((Integer.compare(duration, 0) <= 0))){
    		duration=0;
        }
    	
    	Date newEventStart = newEvent.getEventDate();
    	Date newEventEnd = newEvent.getEventDate();
    	newEventEnd = new Date(newEvent.getEventDate().getTime()+duration*ONE_MINUTE_IN_MILLIS);
    	Date exEventStart = null;
    	Date exEventEnd = null;      	
		System.out.println("newEventStart="+newEventStart);
		System.out.println("newEventEnd="+newEventEnd);
    	List<Event> existsEvent = eventDAO.findAll();
    	for (Event ex:existsEvent){        	
    		if ((ex.isDelete())||(ex.equals(newEvent)))
    			continue;
    		exEventStart = ex.getEventDate(); 
    		System.out.println(ex.getEventName());
    		System.out.println(ex.getDurationTime());
    		exEventEnd = new Date(ex.getEventDate().getTime()+ex.getDurationTime()*ONE_MINUTE_IN_MILLIS);

			System.out.println("exEventStart="+exEventStart);
			System.out.println("exEventEnd="+exEventEnd);      
    		if (!(newEventEnd.before(exEventStart)||newEventStart.after(exEventEnd))){
            	System.out.println("stopp");
    			return false;
    		}    		
    	}
    	return true;
    }
    
    @Override
	@Transactional
	public Event updateEvent(Event newEvent){
        if (checkEventDate(newEvent))    	
		return eventDAO.updateEntity(newEvent);
        else return null;
	}
	
	@Override
	@Transactional
	public Event findById(Integer id){
		return eventDAO.findById(id);	 
	}

	@Override
	@Transactional
	public List<Event> getFutureEvents() {	
		return 	eventDAO.findFutureEvents();
	}

	@Override
	@Transactional
	public List<Event> getPastEvents() {
		return 	eventDAO.findPastEvents();
	}

    @Override
    @Transactional
    public void createEvent(Event event) throws ParseException {
        updateEvent(event);
    }

    @Override
    @Transactional
    public Event createEmptyEvent() {
        Event event = new Event();

        event.setSectorPriceSet(new ArrayList<SectorPrice>());

        for (int sectorId=1; sectorId <= SECTORS_COUNT; ++sectorId){
            SectorPrice sectorPrice = new SectorPrice();
            sectorPrice.setEvent(event);
            Sector sector= sectorDAO.findById(sectorId);
            sectorPrice.setSector(sector);
            event.getSectorPriceSet().add(sectorPrice);
        }
        return event;
    }

}

