package com.dataartschool2.stadiumticket.dreamteam.web;

import com.dataartschool2.stadiumticket.dreamteam.domain.SectorStatus;
import com.dataartschool2.stadiumticket.dreamteam.service.SectorService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
public class TicketsRestController {

    @Autowired
    private SectorService sectorService;
   
    @RequestMapping(value = "/tickets/get_sector_seats", method = RequestMethod.GET)
    public SectorStatus getSectorStatus(@RequestParam("event") Integer eventId,
                                        @RequestParam("sector") Integer sectorId){
        return sectorService.getSectorStatus(eventId, sectorId);
    }
}
