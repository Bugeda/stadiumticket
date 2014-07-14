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
import java.util.List;


@Service
public class EventServiceImpl implements EventService{

    public static final int SECTORS_COUNT = 27;
    @Autowired
	private EventDAO eventDAO;
	
	@Autowired
	private SectorPriceDAO sectorPriceDAO;

    @Autowired
    private SectorDAO sectorDAO;

   /* @Override
    public void deleteEvent(Event event) {
        eventDAO.deleteEntity(event);
    }*/

    @Override
    public void markAsDeleted(Event event) {
        event.setDelete(true);
        updateEvent(event);
    }

    @Override
	@Transactional
	public Event updateEvent(Event event){
		return eventDAO.updateEntity(event);
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

    /*@Override
    @Transactional
    public void editEvent(NewEventForm editEventForm) throws ParseException {
        Integer eventId=editEventForm.getId();

        Event event = findById(eventId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date d = sdf.parse(editEventForm.getEventDate());
        Timestamp stamp = new Timestamp(d.getTime());
        stamp.setSeconds(0);
        event.setEventName(editEventForm.getEventName());
        event.setEventDate(stamp);
        event.setBookingCanceltime(Integer.parseInt(editEventForm.getBookingCanceltime()));
        updateEvent(event);
        int sectorId=1;
        for (String e : editEventForm.getSectorPrice()){

            SectorPrice sp=new SectorPrice();
            sp.setEvent(event);
            Sector sector = sectorDAO.findById(sectorId);
            sp.setSector(sector);
            sp.setPrice(Double.parseDouble(e));
            sectorPriceDAO.updateEntity(sp);
            sectorId++;
        }
    }*/

}

