package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;
import com.dataartschool2.stadiumticket.dreamteam.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 12.07.2014.
 */
@Controller
public class SellTicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @ModelAttribute("event")
    public Event getEvent(@RequestParam("id") Integer id){
        if(id != null){
            return eventService.findById(id);
        }else{
            throw new RuntimeException("No event was chosen.");
        }
    }


    @ModelAttribute("chosenSeats")
    public List<Seat> chosenSeats(){
        return  new ArrayList<Seat>();
    }

    @RequestMapping(value = "/tickets/sell", method = RequestMethod.GET)
    public String getSellTicketsPage(){
        return "sell_tickets";
    }

    @RequestMapping(value = "/tickets/sell", method = RequestMethod.POST)
    public String postSellTicketsPage(@Valid @ModelAttribute("chosenSeats") List<Seat> chosenSeats,
                                      BindingResult seatsBindingResult,
                                      @ModelAttribute("event") Event event,
                                      ModelMap modelMap){
        if(seatsBindingResult.hasErrors()){
            modelMap.put("result", seatsBindingResult);
            return "sell_tickets";
        }else{
            ticketService.sellTickets(event, chosenSeats);
        }
        return "sell_tickets";

    }
}
