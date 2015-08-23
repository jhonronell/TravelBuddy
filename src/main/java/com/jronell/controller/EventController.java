package com.jronell.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jronell.model.Event;
import com.jronell.model.EventAddress;
import com.jronell.model.EventType;
import com.jronell.model.Status;
import com.jronell.service.EventService;
import com.jronell.service.InterestTypeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EventController {

	private static final Logger logger = LoggerFactory.getLogger(EventController.class);

	@RequestMapping(value = "event/create", method = RequestMethod.GET)
	public String createEvent(Locale locale, Model model) {
		logger.info("Welcome home! Add Event", locale);
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		InterestTypeService interestTypeService = (InterestTypeService) context.getBean("InterestTypeService");
		model.addAttribute("interestTypeList", interestTypeService.getInterestTypes());
		return "addEvent";
	}

	@RequestMapping(value = "event/save", method = RequestMethod.POST)
	public String saveEvent(Locale locale, Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {

		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

		/*
		 * HttpSession session = request.getSession(); User user = (User)
		 * session.getAttribute("user"); int organizerId = user.getUserId();
		 * System.out.println(organizerId);
		 */
		EventType eventType = EventType.validate(request.getParameter("eventType"));

		String name = request.getParameter("name");
		String eventDateStart = request.getParameter("eventDateStart");
		String eventDateEnd = request.getParameter("eventDateEnd");
		String eventDatePosted = request.getParameter("eventDatePosted");
		Status eventStatus = Status.validate(request.getParameter("status"));
		String description = request.getParameter("description");
		
		Event event = new Event(eventType, name, eventDateStart, eventDateEnd, eventDatePosted, eventStatus,
				description);

		String category = request.getParameter("category");
		String street = request.getParameter("street");
		String brgy = request.getParameter("brgy");
		String city = request.getParameter("city");
		String region = request.getParameter("region");
		String province = request.getParameter("province");
		EventAddress address = new EventAddress(street, brgy, city, region, province);

		event.setAddress(address);
		//event.setOrganizerId(organizerId);

		//EventService eventService = ServiceFactory.createEventService();
		EventService eventService = (EventService) context.getBean("EventService");
		int eventId = eventService.addEvent(event);

		//response.sendRedirect("userprofile?);
		redirectAttributes.addFlashAttribute("eventId", eventId);
		return "redirect:/user/display";
		
	}

}
