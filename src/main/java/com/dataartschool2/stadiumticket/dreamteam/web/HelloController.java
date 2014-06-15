package com.dataartschool2.stadiumticket.dreamteam.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController{

	@RequestMapping("/index")
	public String home() {
		return "redirect:/index";
	}
	

	
}
