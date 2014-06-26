package com.dataartschool2.stadiumticket.dreamteam.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Booking;

@Repository  
@Transactional  
public class BookingDAOImpl extends GenericDAOImpl<Booking, Long> implements BookingDAO {
    

}