package com.dataartschool2.stadiumticket.dreamteam.web;

import java.util.List;

import javax.validation.constraints.Size;

import com.dataartschool2.stadiumticket.dreamteam.domain.Seat;
import com.dataartschool2.stadiumticket.dreamteam.domain.Sector;

public class SeatsForm {

	@Size(min = 1, max = 50, message = "error.wrongLength")
	private String customerName;
	
    private List<Seat> chosenSeats;
    
    private List<Integer> chosenSectorsNums;
        
    
    public SeatsForm() {}
	


    public SeatsForm(boolean isBooking, String customerName,List<Seat> chosenSeats, List<Integer> chosenSectorsNums,Integer eventId) {
		this.customerName = customerName;
		this.chosenSeats = chosenSeats;
		this.chosenSectorsNums = chosenSectorsNums;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
