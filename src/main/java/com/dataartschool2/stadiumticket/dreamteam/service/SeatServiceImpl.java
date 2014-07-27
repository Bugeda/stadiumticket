package com.dataartschool2.stadiumticket.dreamteam.service;


import com.dataartschool2.stadiumticket.dreamteam.dao.*;
import com.dataartschool2.stadiumticket.dreamteam.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SeatServiceImpl implements SeatService {
	
    @Autowired
    private SeatDAO seatDAO;
    
	@Override
	@Transactional
	public void updateSeat(Seat seat) {
		seatDAO.updateEntity(seat);
		
	}

}
