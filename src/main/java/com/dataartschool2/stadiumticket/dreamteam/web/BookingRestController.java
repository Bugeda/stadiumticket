package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingRestController {

    @Autowired
    private BookingService bookingService;
   
    @RequestMapping(value = "/booking/delete", method = RequestMethod.GET)
    public boolean deleteBooking(@RequestParam("id") Integer[] ids){
        return bookingService.deleteBookingList(ids);
    }

    @RequestMapping(value = "/booking/sell", method = RequestMethod.GET)
    public boolean sellBooking(@RequestParam("id") Integer[] ids){
        return bookingService.deleteBookingList(ids);
    }
}
