package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Customer;
import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorStatus;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;
import com.dataartschool2.stadiumticket.dreamteam.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
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
    @RequestMapping(value = "/tickets/book", method = RequestMethod.POST)
    public String postSellTicketsPage(@Valid @ModelAttribute("newCustomer") Customer customer,
                                      BindingResult seatsBindingResult,
                                      @ModelAttribute("event") Event event,
                                      ModelMap modelMap){
    		System.out.println("dg");
       /* {${newCustomer.bookingSet[i].
         * if(seatsBindingResult.hasErrors())
        	System.out.println("error");
            modelMap.put("result", seatsBindingResult);
            return "/tickets/book_tickets";
        }else{*/
        //	System.out.println(customer.getCustomerName());
        	List<Booking> bookingSet = customer.getBookingSet();
        	for (Booking bk:bookingSet)	{
        		System.out.println(bk.getSeat().getRowNumber());
        		System.out.println(bk.getSeat().getSeatNumber());
        		System.out.println(bk.getSeat().getSector().getName());
          //  ticketService.bookTickets(event, chosenSeats);
     //   }
        }
        return "/tickets/book_tickets";

    }

    @ResponseBody
    @RequestMapping(value = "/tickets/sell", method = RequestMethod.POST)
    public String postBookTicketsPage(@Valid @ModelAttribute("chosenSeats") List<Seat> chosenSeats,
                                      BindingResult seatsBindingResult,
                                      @ModelAttribute("event") Event event,
                                      ModelMap modelMap){
        if(seatsBindingResult.hasErrors()){
            modelMap.put("result", seatsBindingResult);
            return "/tickets/sell_tickets";
        }else{
            ticketService.sellTickets(event, chosenSeats);
        }
        return "/tickets/sell_tickets";

    }
    
    @RequestMapping(value = "/tickets/get_sector_seats", method = RequestMethod.GET)
    public SectorStatus getSectorStatus(@RequestParam("event") Integer eventId,
                                        @RequestParam("sector") Integer sectorId){

        return sectorService.getSectorStatus(eventId, sectorId);
    }
}
