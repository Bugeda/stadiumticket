package com.dataartschool2.stadiumticket.dreamteam.web.validator;

import com.dataartschool2.stadiumticket.dreamteam.domain.Event;
import com.dataartschool2.stadiumticket.dreamteam.domain.SectorPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;

/**
 * Created by Denis on 07.07.2014.
 */
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
            validatePrice(sectorPrice, errors);
        }
    }

    private void validatePrice(SectorPrice sectorPrice, Errors errors) {
        Double price = sectorPrice.getPrice();
        if(price == null){
            errors.rejectValue("sectorPriceSet", "pricesMustBeFilled", "All prices must be filled");
        }
        if(Double.compare(price, 0) <= 0){
            errors.rejectValue("sectorPriceSet", "notPositivePrices", "Prices should be positive");
        }
    }
}
