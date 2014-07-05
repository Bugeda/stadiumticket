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
import java.util.ArrayList;
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
    public NewEventForm getEventForm(int id) {
        NewEventForm newEventForm = new NewEventForm();
        Event event = eventDAO.findById(id);
        List<SectorPrice> prices = sectorPriceDAO.getPricesSectorsOfEvent(event);

        newEventForm.setId(event.getId());
        newEventForm.setBookingCanceltime(Integer.toString(event.getBookingCanceltime()));
        newEventForm.setEventDate(event.getEventDate().toString());
        newEventForm.setEventName(event.getEventName());
        SectorPrice[] sectorPrices = prices.toArray(new SectorPrice[1]);
        String[] sectorPricesStrings = new String[sectorPrices.length];
        for(int i = 0; i < sectorPrices.length; ++i){
            sectorPricesStrings[i] = sectorPrices[i].toString();
        }
        newEventForm.setSectorPrice(sectorPricesStrings);

        return newEventForm;
    }

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
    public void createEvent(NewEventForm eventForm) throws ParseException {
        System.out.println("CREATING EVENT");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = simpleDateFormat.parse(eventForm.getEventDate());
        Timestamp stamp = new Timestamp(date.getTime());
        stamp.setSeconds(0);
        Event event = new Event();

        event.setEventName(eventForm.getEventName());
        event.setEventDate(stamp);
        event.setBookingCanceltime(Integer.parseInt(eventForm.getBookingCanceltime()));
        event.setSectorPriceSet(new ArrayList<SectorPrice>());
        int sectorId=1;
        for (String e : eventForm.getSectorPrice()){
            System.out.println(e);
            SectorPrice sectorPrice = new SectorPrice();
            sectorPrice.setEvent(event);
            Sector sector= sectorDAO.findById(sectorId);
            sectorPrice.setSector(sector);
            sectorPrice.setPrice(Double.parseDouble(e));
            event.getSectorPriceSet().add(sectorPrice);
            sectorId++;
        }
        updateEvent(event);
    }

    @Override
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
    }

}

