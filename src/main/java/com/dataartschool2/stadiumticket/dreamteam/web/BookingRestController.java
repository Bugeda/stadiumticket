package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingRestController {


	@Autowired
	private ApplicationContext appContext;
	
    @Autowired
    private BookingService bookingService;
    
    @RequestMapping(value = "/booking/cancel", method = RequestMethod.GET)
    public  Boolean[] cancelBooking(@RequestParam("id") Integer[] ids){
     	Boolean[] results=bookingService.cancelBookingSet(ids);
        return results;
    }

    @RequestMapping(value = "/booking/sell", method = RequestMethod.GET)
    public  Boolean[] sellBooking(@RequestParam("id") Integer[] ids){
     	Boolean[] results=bookingService.sellBookingSet(ids);      
        return results;
    }
}
