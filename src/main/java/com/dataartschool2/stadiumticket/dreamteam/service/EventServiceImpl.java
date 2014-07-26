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
	public Event updateEvent(Event newEvent){
    	Event result = null;   	
    	Date newEventStart = newEvent.getEventDate();
    	Date newEventEnd = newEvent.getEventDate();
    	newEventEnd = new Date(newEvent.getEventDate().getTime()+newEvent.getDurationTime()*ONE_MINUTE_IN_MILLIS);
    	Date exEventStart = null;
    	Date exEventEnd = null;
    	List<Event> existsEvent = eventDAO.findAll();
    	Boolean isEx = true;
    	for (Event ex:existsEvent){
    		exEventStart = ex.getEventDate();    	
    		exEventEnd = new Date(ex.getEventDate().getTime()+ex.getDurationTime()*ONE_MINUTE_IN_MILLIS);    		
    		if (!(newEventEnd.before(exEventStart)||newEventStart.after(exEventEnd))){
    			isEx=false;
    			break;
    		}    		
    	}
    	if (isEx) result = eventDAO.updateEntity(newEvent);
		return result;
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

