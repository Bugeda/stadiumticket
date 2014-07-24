package com.dataartschool2.stadiumticket.dreamteam.web.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denis on 05.07.2014.
 */
@ControllerAdvice()
public class ExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeExceptionHandler(Exception exception){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("errorMessage", exception.getMessage());
        exception.printStackTrace();

        return new ModelAndView("error", model);
    }
}
