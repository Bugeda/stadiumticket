package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.dao.EventDAO;
import com.dataartschool2.stadiumticket.dreamteam.dao.SectorDAO;
import com.dataartschool2.stadiumticket.dreamteam.dao.SectorPriceDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.NewEventForm;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventDAO eventDAO;
	
	@Autowired
	private SectorPriceDAO sectorPriceDAO;

    @Autowired
    private SectorDAO sectorDAO;

    @Override
    public void deleteEvent(Event event) {
        eventDAO.deleteEntity(event);
    }

    @Override
    public NewEventForm getEventForm(Integer id) {
        NewEventForm newEventForm = new NewEventForm();
        return newEventForm;
    }

    @Override
	@Transactional
	public void updateEvent(Event event){
		eventDAO.updateEntity(event);
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
    public void createEvent(NewEventForm evForm) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date d = sdf.parse(evForm.getEventDate());
        Timestamp stamp = new Timestamp(d.getTime());
        stamp.setSeconds(0);
        Event ev=new Event();
        ev.setEventName(evForm.getEventName());
        ev.setEventDate(stamp);
        ev.setBookingCanceltime(Integer.parseInt(evForm.getBookingCanceltime()));
        updateEvent(ev);
        Integer evId=ev.getId();
        SectorPrice sp = null;
        Sector sector=null;
        int sectorId=1;
        for (String e : evForm.getSectorPrice()){
            System.out.println(e);
            sp=new SectorPrice();
            sp.setEvent(ev);
            sector= sectorDAO.findById(sectorId);
            sp.setSector(sector);
            sp.setPrice(Double.parseDouble(e));
            sectorPriceDAO.updateEntity(sp);
            sectorId++;
        }
    }

}

