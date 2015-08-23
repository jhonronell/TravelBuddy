package com.jronell.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jronell.service.InterestTypeService;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "event/create", method = RequestMethod.GET)
	public String createEvent(Locale locale, Model model) {
		logger.info("Welcome home! Add Event", locale);
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		InterestTypeService interestTypeService = (InterestTypeService) context.getBean("InterestTypeService");
		model.addAttribute("interestTypeList", interestTypeService.getInterestTypes());
		return "addEvent";
	}
}
