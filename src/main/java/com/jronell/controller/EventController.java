package com.jronell.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jronell.factory.ServiceFactory;
import com.jronell.service.InterestTypeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EventController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	
	
	@RequestMapping(value="AddEvent", method=RequestMethod.GET)
	public String addEvent(Locale locale,Model model){
		logger.info("Welcome home! Add Event", locale);
		ApplicationContext context =  new ClassPathXmlApplicationContext("WEB-INF/spring/root-context.xml");
		InterestTypeService interestTypeService = (InterestTypeService) context.getBean("InterestTypeServiceImpl");
		model.addAttribute("interestTypeList", interestTypeService.getInterestTypes());
	
		return "AddEvent";
	}

	
}
