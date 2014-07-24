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
	public List<Seat> modifySeatSet(int size, List<Seat> seatSet, List<Sector> sectorSet){	

		if (seatSet.isEmpty()){
			for (int i=0;i<size;i++){
				seatSet.add(new Seat());
			}
		}		
		if (!sectorSet.isEmpty()){
			for (int index=0; index<size; index++){
			seatSet.get(index).setSector(sectorSet.get(index));
			}
		}
		return seatSet;
		
	}

	@Override
	@Transactional
	public void updateSeat(Seat seat) {
		seatDAO.updateEntity(seat);
		
	}

}
