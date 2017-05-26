package com.stackroot.activity.rest.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroot.activity.dao.CircleDAO;
import com.stackroot.activity.dao.StreamDAO;
import com.stackroot.activity.dao.UserDAO;
import com.stackroot.activity.dao.UserHomeDAO;
import com.stackroot.activity.model.Stream;
import com.stackroot.activity.model.User;
import com.stackroot.activity.vo.UserHome;



@RestController
public class CircleRestService {

	private static final Logger logger = LoggerFactory.getLogger(CircleRestService.class);

	@Autowired
	UserDAO userDAO;

	@Autowired
	User user;
	
	@Autowired UserHome userHome;
	
	@Autowired
	StreamDAO streamDAO;
	

	@Autowired
	CircleDAO circleDAO;
	
	@Autowired
	Stream stream;

	@Autowired HttpSession httpSession;
	
		
	@RequestMapping(value = "/circle_messages/{id}", method = RequestMethod.GET)
	public UserHome getMessagesFromCircle(@PathVariable("id") String id) {
		logger.debug("->->calling method getMessagesFromCircle" + id);
		
		

		
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		logger.debug("loggedInUserID: " + loggedInUserID);
		
		List<Stream> stream = streamDAO.getMessagesFromCircle(id);
		userHome.setMyCircles(circleDAO.getMyCircles(loggedInUserID));
		userHome.setMyInBox(stream);
		
		return userHome;
	}
	
	
	


}
