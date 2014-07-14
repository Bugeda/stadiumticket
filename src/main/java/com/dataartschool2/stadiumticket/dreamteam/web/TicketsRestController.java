package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorStatus;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;
import com.dataartschool2.stadiumticket.dreamteam.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Denis on 13.07.2014.
 */

@RestController
public class TicketsRestController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SectorService sectorService;

    @ResponseBody
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

    @RequestMapping(value = "/tickets/get_sector_seats", method = RequestMethod.GET)
    public SectorStatus getSectorStatus(@RequestParam("eventId") Integer eventId,
                                        @RequestParam("sectorId") Integer sectorId){

        return sectorService.getSectorStatus(eventId, sectorId);
    }
}
