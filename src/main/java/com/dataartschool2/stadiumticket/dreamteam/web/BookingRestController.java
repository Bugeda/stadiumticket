package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingRestController {


	@Autowired
	private ApplicationContext appContext;
	
    @Autowired
    private BookingService bookingService;
    
    @RequestMapping(value = "/booking/cancel", method = RequestMethod.GET)
    public  Boolean[] cancelBooking(@RequestParam("id") Integer[] ids, ModelMap modelMap){
     	Boolean[] results=bookingService.cancelBookingSet(ids);
     	Boolean all=true;
     	int i=0;
     	
     	for (Boolean res:results){
     		all=all&&res;
     	}
        if (all){
        	modelMap.put("message",  appContext.getMessage("message.bookingSearchCancel", new Object[]{}, null));       	
        }
        else{
        	modelMap.put("warning",  appContext.getMessage("error.bookingSearchNotCancel", new Object[]{}, null)); 
        }
     	
        return results;
    }

    @RequestMapping(value = "/booking/sell", method = RequestMethod.GET)
    public  Boolean[] sellBooking(@RequestParam("id") Integer[] ids, ModelMap modelMap){
     	Boolean[] results=bookingService.sellBookingSet(ids);
     	Boolean all=true;
     	for (Boolean res:results){
     		all=all&&res;
     	}
        if (all){
        	modelMap.put("message",  appContext.getMessage("message.bookingSearchSold", new Object[]{}, null));       	
        }
        else{
        	modelMap.put("warning",  appContext.getMessage("error.bookingSearchNotSold", new Object[]{}, null)); 
        }
        return results;
    }
}
