package com.dataartschool2.stadiumticket.dreamteam.service;

import com.dataartschool2.stadiumticket.dreamteam.dao.SectorDAO;
import com.dataartschool2.stadiumticket.dreamteam.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectorServiceImpl implements SectorService {

    public static final int SEATS_IN_VIP = 20;
    public static final int FIRST_VIP_SECTOR_ID = 26;
    public static final int SEATS_IN_NON_VIP = 50;
    public static final int ROWS_IN_NON_VIP = 20;
    public static final int ROWS_IN_VIP = 10;
    
    @Autowired
    private SectorDAO sectorDAO;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketService ticketService;

    @Override
    @Transactional
    public Sector findById(Integer id) {
        return sectorDAO.findById(id);
    }

    @Override
    @Transactional
    public List<Sector> findAll() {
        return sectorDAO.findAll();
    }
    
    @Override
    public SectorStatus getSectorStatus(Integer eventId, Integer sectorId) {

        SectorStatus sectorStatus = new SectorStatus();

        String sectorName = getSectorName(sectorId);
        sectorStatus.setName(sectorName);

        List<List<SeatStatus>> seatsStatuses = getSeatsStatus(eventId, sectorId);
        sectorStatus.setRows(seatsStatuses);
        return sectorStatus;
    }

    @Override
    @Transactional
    public void updateSector(Sector sector) {
        sectorDAO.updateEntity(sector);
    }

    @Override
    public List<Sector> createSectorsListFromNums(List<Integer> listSectoriD){
    	List<Sector> sectorSet=new ArrayList<Sector>();
    	for (int id:listSectoriD){
    		sectorSet.add(findById(id));  

    	}

		return sectorSet;
    }
    
    private void addBookedTickets(Integer eventId, Integer sectorId, List<List<SeatStatus>> seatsStatuses) {

        List<Booking> bookedTickets = bookingService.getBookingsForEventInSector(eventId, sectorId);

        for (Booking booking : bookedTickets) {
            BookingStatus bookingStatus = booking.getBookingStatus();

            Ticket ticket = booking.getTicket();
            Seat seat = ticket.getSeat();
            int rowsNumber = seat.getRowNumber();
            int seatNumber = seat.getSeatNumber();
            List<SeatStatus> rowStatus = seatsStatuses.get(rowsNumber - 1);

            switch (bookingStatus) {
                case Sold:
                case BookingRedeemed:
                    rowStatus.set(seatNumber, SeatStatus.occupied);
                    break;
                case Booked:
                    rowStatus.set(seatNumber, SeatStatus.booked);
                    break;
            }
        }
    }

    private void addSoldTickets(Integer eventId, Integer sectorId, List<List<SeatStatus>> seatsStatuses) {

        List<Ticket> soldTickets = ticketService.getSoldTicketsBySector(eventId, sectorId);
        for (Ticket ticket : soldTickets) {
            Seat seat = ticket.getSeat();
            int rowsNumber = seat.getRowNumber();
            int seatNumber = seat.getSeatNumber();

            List<SeatStatus> rowStatus = seatsStatuses.get(rowsNumber - 1);
            rowStatus.set(seatNumber, SeatStatus.occupied);
        }
    }

    private int getRowsCount(Integer sectorId) {
        if (sectorId < FIRST_VIP_SECTOR_ID) {
            return ROWS_IN_NON_VIP;
        } else {
            return ROWS_IN_VIP;
        }
    }

    private int getSeatsCount(Integer sectorId) {
        if (sectorId < FIRST_VIP_SECTOR_ID) {
            return SEATS_IN_NON_VIP;
        } else {
            return SEATS_IN_VIP;
        }
    }

    private List<List<SeatStatus>> getSeatsStatus(Integer eventId, Integer sectorId) {

        List<List<SeatStatus>> seatsStatuses = initWithAllFreeSeats(sectorId);
        addSoldTickets(eventId, sectorId, seatsStatuses);
        addBookedTickets(eventId, sectorId, seatsStatuses);
        return seatsStatuses;
    }

    private String getSectorName(Integer sectorId) {

        Sector sector = sectorDAO.findById(sectorId);
        return sector.getName();
    }

    private void initRow(int seatsInRowCount, List<List<SeatStatus>> seatsStatuses) {
        List<SeatStatus> rowStatus = new ArrayList<SeatStatus>(seatsInRowCount);
        for (int j = 0; j < seatsInRowCount; ++j) {
            rowStatus.add(SeatStatus.vacant);
        }
        seatsStatuses.add(rowStatus);
    }

    private List<List<SeatStatus>> initWithAllFreeSeats(int sectorId) {
        int rowsCount = getRowsCount(sectorId);
        int seatsInRowCount = getSeatsCount(sectorId);
        List<List<SeatStatus>> seatsStatuses = new ArrayList<List<SeatStatus>>(rowsCount);
        for (int i = 0; i < rowsCount; ++i) {
            initRow(seatsInRowCount, seatsStatuses);
        }
        return seatsStatuses;
    }
    

}
