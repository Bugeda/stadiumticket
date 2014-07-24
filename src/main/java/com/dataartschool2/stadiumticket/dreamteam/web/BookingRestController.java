package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingRestController {

    @Autowired
    private BookingService bookingService;
    
    @RequestMapping(value = "/booking/cancel", method = RequestMethod.GET)
    public  Boolean[] cancelBooking(@RequestParam("id") Integer[] ids){
     	Boolean[] results=bookingService.cancelBookingSet(ids);
     	Boolean all=true;
     	for (Boolean res:results){
     		all=all&&res;
     	}
     	//for message
        return results;
    }

    @RequestMapping(value = "/booking/sell", method = RequestMethod.GET)
    public  Boolean[] sellBooking(@RequestParam("id") Integer[] ids){
     	Boolean[] results=bookingService.sellBookingSet(ids);
     	Boolean all=true;
     	for (Boolean res:results){
     		all=all&&res;
     	}
     	//for message
        return results;
    }
}
