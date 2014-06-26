package com.dataartschool2.stadiumticket.dreamteam.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dataartschool2.stadiumticket.dreamteam.domain.Ticket;

@Repository  
@Transactional  
public class TicketDAOImpl extends GenericDAOImpl<Ticket, Long> implements TicketDAO {
    

}