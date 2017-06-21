package com.stackroot.activity.rest.services;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroot.activity.dao.CircleDAO;
import com.stackroot.activity.dao.StreamDAO;
import com.stackroot.activity.dao.UserDAO;
import com.stackroot.activity.model.Circle;
import com.stackroot.activity.model.Stream;
import com.stackroot.activity.model.User;
import com.stackroot.activity.model.UserCircle;
import com.stackroot.activity.vo.UserHome;



@RestController
@RequestMapping("/api/circle")
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
	
	@Autowired UserCircle userCircle;
	
	@Autowired
	Circle circle;
	
	@Autowired
	Stream stream;

	@Autowired HttpSession httpSession;
	
		
	@RequestMapping(value = "/circle_messages/{id}", method = RequestMethod.GET)
	public UserHome getMessagesFromCircle(@PathVariable("id") String circleID) {
		logger.debug("->->calling method getMessagesFromCircle" + circleID);
		
		

		
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		logger.debug("loggedInUserID: " + loggedInUserID);
		
		List<Stream> stream = streamDAO.getMyCircleMessages(circleID);
		userHome.setMyCircles(circleDAO.getMyCircles(loggedInUserID));
		userHome.setMyInBox(stream);
		
		return userHome;
	}
	
	@GetMapping(value = "/circles")
	public List<Circle> getCircles() {
		logger.debug("->->calling method getCircles");
		
		return circleDAO.getAllCircles();
		
	}
	
	@GetMapping(value = "/circleUsers/{id}")
	public List<UserCircle> getCircleUsers(@PathVariable("id") String circleName) {
		logger.debug("->->calling method getCircleUsers");
		logger.debug("getting user of the circle Name " + circleName);
		circle = circleDAO.getCircleByName(circleName);
		logger.debug("getting user of the circle ID " + circleName);
		return circleDAO.getCircleUsers(circle.getId());
		
	}
	
	@GetMapping(value = "/adduser/{circleID}/{userID}")
	public List<UserCircle> addUserToCircle(@PathVariable("circleID") String circleID,
			@PathVariable("userID") String userID) {
		logger.debug("->->calling method addUserToCircle");
		logger.debug("Adding " +userID + " to circle " + circleID);
		 circleDAO.addUser(userID, circleID);
		
		return circleDAO.getCircleUsers(circle.getId());
		
	}
	
	@GetMapping(value = "/removeuser/{circleID}/{userID}")
	public List<UserCircle> removeUserFromCircle(@PathVariable("circleID") String circleID,
			@PathVariable("userID") String userID) {
		logger.debug("->->calling method removeUserFromCircle");
		logger.debug("removing " +userID + " to circle " + circleID);
		circleDAO.removeUser(userID, circleID);
		
		return circleDAO.getCircleUsers(circle.getId());
		
	}
	
	@PostMapping(value = "/create/")
	public Circle createCircle(@RequestBody Circle circle) {
		logger.debug("->->calling method createCircle");
		String name = circle.getName();
		if(circleDAO.getCircleByName(name)!=null)
		{
			circle.setStatusCode("404");
			circle.setStatusMessage("Circle already exist with name  " + name );
			logger.debug("Circle already exist with name  " + name );
			return circle;
		}
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		circle.setAdminID(loggedInUserID);
		circle.setId(name);
		circle.setStatus("A");
		circle.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		if( circleDAO.save(circle))
		{
			userCircle.setCircleID(circle.getId());
			userCircle.setId((int) (Math.random()*1000000));
			userCircle.setUserID(loggedInUserID);
			
			circle.setStatusCode("200");
			circle.setStatusMessage("Circle " + name + "successfully created");
			logger.debug("Circle " + name + "successfully created");
		}
		else
		{
			circle.setStatusCode("404");
			circle.setStatusMessage("Could not create circle");
			logger.debug("->->Could not create circle");
		}
		return circle;
		
	}
	
	
	


}
