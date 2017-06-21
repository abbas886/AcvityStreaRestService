package com.stackroot.activity.rest.services;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroot.activity.dao.CircleDAO;
import com.stackroot.activity.dao.StreamDAO;
import com.stackroot.activity.dao.UserDAO;
import com.stackroot.activity.model.User;
import com.stackroot.activity.vo.UserHome;;

@RestController
@RequestMapping("/api/user")

public class UserRestService {

	private static final Logger logger = LoggerFactory.getLogger(UserRestService.class);

	@Autowired
	private UserHome userHome;

	@Autowired
	UserDAO userDAO;

	@Autowired
	User user;

	@Autowired
	StreamDAO streamDAO;

	@Autowired
	CircleDAO circleDAO;

	@Autowired
	HttpSession httpSession;



	@PostMapping("/login/")
	public UserHome validate(@RequestBody User user) {

		user = userDAO.validate(user.getId(), user.getPassword());
		if (user == null) {

			userHome.setStatusCode("404");
			userHome.setStatusMessage("Invalid credentials.  Please try again..");

		} else {
			userHome.setStatusCode("200");
			userHome.setStatusMessage("You successfully logged in.");
			userHome.setMyInBox(streamDAO.getMyInbox(user.getId()));
			userHome.setMyCircles(circleDAO.getMyCircles(user.getId()));
			httpSession.setAttribute("loggedInUserID", user.getId());

		}

		return userHome;

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public User logout(HttpSession session) {
		logger.debug("->->->->calling method logout");

		session.invalidate();
		user.setStatusCode("200");
		user.setStatusMessage("You successfully logged out.");
		
		logger.debug("You successfully logged out.");
		return user;
	};



	// @RequestMapping(value = "/user/", method = RequestMethod.POST)
	@PostMapping("/register/")
	public User createUser(@RequestBody User user) {
		logger.debug("->->->->calling method createUser");
		if (userDAO.get(user.getId()) == null) {
			logger.debug("->->->->User is going to create with id:" + user.getId());
			if (userDAO.save(user) == true) {
				user.setStatusCode("200");
				user.setStatusMessage("Thank you  for registration");
			} else {
				user.setStatusCode("404");
				user.setStatusMessage("Could not complete the operatin please contact Admin");

			}

			return user;
		}
		logger.debug("->->->->User already exist with id " + user.getId());
		user.setStatusCode("404");
		user.setStatusMessage("User already exist with id : " + user.getId());
		return user;
	}

	
	

}
