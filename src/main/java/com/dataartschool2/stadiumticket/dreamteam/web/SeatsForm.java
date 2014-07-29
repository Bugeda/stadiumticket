package com.dataartschool2.stadiumticket.dreamteam.web;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;

public class SeatsForm {

	@NotBlank(message = "error.notNull")
	@Size(min = 1, max = 50, message = "error.wrongLength")
	private String customerName;
	
	private Integer eventId;
	
    private List<Seat> chosenSeats;
    
    private List<Integer> chosenSectorsNums;
            
    public SeatsForm() {}

    public SeatsForm(boolean isBooking, Integer eventId, String customerName,List<Seat> chosenSeats, List<Integer> chosenSectorsNums) {
		this.customerName = customerName;
		this.eventId = eventId;
		this.chosenSeats = chosenSeats;
		this.chosenSectorsNums = chosenSectorsNums;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public List<Seat> getChosenSeats() {
		return chosenSeats;
	}

	public void setChosenSeats(List<Seat> chosenSeats) {
		this.chosenSeats = chosenSeats;
	}
	
	public List<Integer> getChosenSectorsNums() {
		return chosenSectorsNums;
	}

	public void setChosenSectorsNums(List<Integer> chosenSectorsNums) {
		this.chosenSectorsNums = chosenSectorsNums;
	}

}
