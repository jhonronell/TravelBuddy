package com.jronell.controller;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jronell.factory.ServiceFactory;
import com.jronell.model.User;
import com.jronell.service.BucketListService;
import com.jronell.service.EventService;
import com.jronell.service.InterestService;
import com.jronell.service.InterestTypeService;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "user/display", method = RequestMethod.GET)
	public String displayUserProfile(HttpServletRequest request) {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
	
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		int userId = user.getUserId();
	    user.setUserId(userId);
		
		
		EventService eventService = (EventService) context.getBean("EventService");
		InterestService interestService =(InterestService) context.getBean("InterestService");
		BucketListService bucketListService = (BucketListService) context.getBean("BucketListService");
		
		bucketListService.getUserBucketList(userId);
		user.setEvents(  eventService.getEventByUserId(userId) );
		user.setInterestList( interestService.getUserInterestList(userId)   );
		user.setBucketList(bucketListService.getUserBucketList(userId));
		request.setAttribute("user",user );
		
		RequestDispatcher requestDispatcher=request.getRequestDispatcher("userprofile.jsp");  
		  
		
		return "profile";
	}
}
