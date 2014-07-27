package com.dataartschool2.stadiumticket.dreamteam.web.validator;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import com.dataartschool2.stadiumticket.dreamteam.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;


@Component
public class EventValidator implements Validator{
	
    @Autowired
    private SpringValidatorAdapter validator;
    
    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        validator.validate(o, errors);

        Event event = (Event) o;
        List<SectorPrice> prices = event.getSectorPriceSet();
        for(SectorPrice sectorPrice : prices){
            boolean result = validatePrice(sectorPrice, errors);
            if(!result){
                break;
            }
        }
    }

    private boolean validatePrice(SectorPrice sectorPrice, Errors errors) {
        Double price = sectorPrice.getPrice();
        if(price == null){
            errors.rejectValue("sectorPriceSet", "error.pricesMustBeSpecified");
            price=0.0;
            return false;
           
        }else {
            if (Double.compare(price, 0) <= 0) {
                errors.rejectValue("sectorPriceSet", "error.notPositivePrice");
               
                return false;
            }
        }
        return true;
    }
}
