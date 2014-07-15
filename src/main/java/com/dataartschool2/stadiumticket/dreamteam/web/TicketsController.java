package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import com.dataartschool2.stadiumticket.dreamteam.service.BookingService;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorPriceService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;
import com.dataartschool2.stadiumticket.dreamteam.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;










import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.validation.Valid;


@Controller
public class TicketsController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SectorService sectorService;
    
	@Autowired
	private SectorPriceService sectorPriceService;
	
	@Autowired
	EventsController eventsController;

	@Autowired
	private BookingService bookingService;	
	
    @ModelAttribute("event")
    public Event getEvent(@RequestParam("id") Integer eventId){
        if(eventId != null){
            return eventService.findById(eventId);
        }else{
          	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/root-context.xml");
    		throw new RuntimeException(applicationContext.getMessage("error.noEventChosen", new Object[]{}, null));
        }
    }
    @ModelAttribute("chosenSeats")
    public List<Seat> chosenSeats(){
        return  new ArrayList<Seat>();
    }

    @RequestMapping(value = "/tickets/sell", method = RequestMethod.GET)
    public String getSellTicketsPage(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(getEvent(eventId));
    	modelMap.put("event", getEvent(eventId));
    	modelMap.put("sectorPrices", sectorPrices);
        return "/tickets/sell_tickets";
    }

    @RequestMapping(value = "/tickets/book", method = RequestMethod.GET)
    public String getBookTicketsPage(@RequestParam("id") Integer eventId, ModelMap modelMap){
    	List<SectorPrice> sectorPrices=sectorPriceService.getPricesSectorsOfEvent(getEvent(eventId));
    	modelMap.put("event", getEvent(eventId));
    	modelMap.put("sectorPrices", sectorPrices);
        return "/tickets/book_tickets";
    }

    @ModelAttribute("booking") 
    @RequestMapping(value = "/tickets/submitbook", method = RequestMethod.POST)
    public String submit_Booking(@ModelAttribute("submit") String submit,
                    
                                    @RequestParam("id") Integer eventId,
                                 
                                    ModelMap modelMap){
										return submit;
    	
       
	}
}
