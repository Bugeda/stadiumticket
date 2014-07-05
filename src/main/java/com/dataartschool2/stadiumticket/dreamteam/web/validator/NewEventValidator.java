package com.dataartschool2.stadiumticket.dreamteam.web.validator;

import com.dataartschool2.stadiumticket.dreamteam.domain.NewEventForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Denis on 05.07.2014.
 */
@Component
public class NewEventValidator implements Validator {
    @Autowired
    private SpringValidatorAdapter validator;

    @Override
    public boolean supports(Class<?> aClass) {
        return NewEventForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        validator.validate(o, errors);

        NewEventForm newEventForm = (NewEventForm) o;
        validateDate(newEventForm, errors);
        validateCancelTime(newEventForm, errors);
    }

    private void validateCancelTime(NewEventForm newEventForm, Errors errors) {
        String cancelTime = newEventForm.getBookingCanceltime();
        if(cancelTime == null){
            errors.rejectValue("bookingCanceltime", "emptyCancelTime", "Wrong cancel time. Should be number of minutes.");
        }

        try{
            int cancelTimeInt = Integer.parseInt(cancelTime);
            if(cancelTimeInt < 0){
                errors.rejectValue("bookingCanceltime", "wrongCanceltime", "Wrong cancel time. Cannot be negative.");
            }
        }catch (Exception e){
            errors.rejectValue("bookingCanceltime", "wrongValue", "Wrong cancel time. Should be number of minutes.");
        }
    }

    private void validateDate(NewEventForm newEventForm, Errors errors) {

        try {
            String eventDate = newEventForm.getEventDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = simpleDateFormat.parse(eventDate);
            System.out.println("Parsed");
            Date now = new Date();
            if(date.before(now)){
                errors.rejectValue("eventDate", "shouldBeInThePast", "Wrong date. Should be in the past.");
            }
        }catch (ParseException e){
            errors.rejectValue("eventDate", "wrongDateFormat", "Wrong date. Should be dd-MM-yyyy HH-mm.");
        }
    }
}
