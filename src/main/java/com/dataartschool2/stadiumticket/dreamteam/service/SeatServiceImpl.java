package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.dao.SeatDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
	
    @Autowired
    private SeatDAO seatDAO;
    
	@Override
	public List<Seat> modifySeatSet(int size, List<Seat> seatSet, List<Sector> sectorSet, List<Ticket> ticketSet){	
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
	public void updateSeat(Seat seat) {
		seatDAO.updateEntity(seat);
		
	}

}
