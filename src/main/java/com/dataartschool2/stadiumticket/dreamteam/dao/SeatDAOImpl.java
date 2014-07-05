package com.dataartschool2.stadiumticket.dreamteam.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;

@Repository  
@Transactional  
public class SeatDAOImpl extends GenericDAOImpl<Seat> implements SeatDAO {
    

}