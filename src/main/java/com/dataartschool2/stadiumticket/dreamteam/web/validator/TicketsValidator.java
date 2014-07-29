package com.dataartschool2.stadiumticket.dreamteam.web.validator;

import com.dataartschool2.stadiumticket.dreamteam.service.TicketService;
import com.dataartschool2.stadiumticket.dreamteam.web.SeatsForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Component
public class TicketsValidator implements Validator{
	
    @Autowired
    private SpringValidatorAdapter validator;
    
    @Autowired
    private TicketService ticketService;
    
	@Autowired
	private ApplicationContext appContext;
	
    @Override
    public boolean supports(Class<?> aClass) {
        return SeatsForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        validator.validate(o, errors);
        SeatsForm sf = (SeatsForm) o;
       // System.out.println("event="+sf.getEvent().getId());
        validateTicketCount(sf, errors);
     
    }

	private boolean validateTicketCount(SeatsForm sf, Errors errors) {
		if (sf.getChosenSeats()==null){
			 errors.rejectValue("chosenSeats", "error.ticketsAreNotChosen");
			 return false;
		} else 
			if (sf.getChosenSeats().size()!=sf.getChosenSectorsNums().size()){
				 errors.rejectValue("chosenSeats", "error.ticketsAreNotChosen");
				 return false;
			} else
				if (!ticketService.checkExistsTickets(sf)){
					 errors.rejectValue("chosenSeats", "error.ticketExist");
					 return false;
				}
		return true;
	}

	private boolean validateCustomerName(String customerName, Errors errors) {
		if ((customerName == null)||(customerName.trim()=="")||(customerName.isEmpty())){
			 errors.rejectValue("customerName", "error.wrongLength");
		return false;
		}
		return true;
	}

	
}
