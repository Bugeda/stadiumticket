package com.dataartschool2.stadiumticket.dreamteam.dao;

import java.util.List;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;
import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;

public interface BookingDAO extends GenericDAO<Booking>{

	Boolean findBySeat(Seat seat);

}
