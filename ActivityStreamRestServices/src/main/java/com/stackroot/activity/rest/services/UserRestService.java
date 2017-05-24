package com.stackroot.activity.rest.services;

import java.util.List;



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
import com.stackroot.activity.dao.UserDAO;
import com.stackroot.activity.dao.UserHomeDAO;
import com.stackroot.activity.model.User;
import com.stackroot.activity.model.UserStream;
import com.stackroot.activity.vo.UserHome;


@RestController
public class UserRestService {

	private static final Logger logger = LoggerFactory.getLogger(UserRestService.class);

	@Autowired
	UserDAO userDAO;

	@Autowired
	User user;
	
	@Autowired UserHome userHome;
	
	@Autowired
	UserHomeDAO userHomeDAO;
	
	@Autowired
	CircleDAO circleDAO;

	
	@GetMapping("/")
	public String server()
	{
		logger.debug("Starting of the method server");
		return "Server is started properly...";
	}
	
	
	
	@GetMapping("/hello/")
	public String sayHello()
	{
		logger.debug("Starting of the method sayHello");
		return "Testing my rest controller";
	}
	
	@PostMapping("/validate/")
	public UserHome validate(@RequestBody User user) 
	{
		
		user = userDAO.validate(user.getId(), user.getPassword());
		if(user==null)
		{
			
			userHome.setErrorCode("404");
			userHome.setErrorMessage("Invalid credentials.  Please try again..");
			
		}
		else
		{
			userHome.setErrorCode("200");
			userHome.setErrorMessage("You successfully logged in.");
			userHome.setMyInBox(userHomeDAO.getMyInbox(user.getId()));
			userHome.setMyCircles(circleDAO.getMyCircles(user.getId()));
			
			
		}
		
		return userHome;
	
	
		
	}
	
	

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> listAllUsers() {

		logger.debug("->->->->calling method listAllUsers");
		List<User> users = userDAO.list();

		// errorCode :200 :404
		// errorMessage :Success :Not found

		if (users.isEmpty()) {
			user.setErrorCode("404");
			user.setErrorMessage("No users are available");
			users.add(user);
		}

		return users;
	}


	//@RequestMapping(value = "/user/", method = RequestMethod.POST)
	@PostMapping("/register/")
	public User createUser(@RequestBody User user) {
		logger.debug("->->->->calling method createUser");
		if (userDAO.get(user.getId()) == null) {
			logger.debug("->->->->User is going to create with id:" + user.getId());
			  if (userDAO.save(user) ==true)
			  {
				  user.setErrorCode("200");
					user.setErrorMessage("Thank you  for registration");
			  }
			  else
			  {
				  user.setErrorCode("404");
					user.setErrorMessage("Could not complete the operatin please contact Admin");
		
				  
			  }
			
			return user;
		}
		logger.debug("->->->->User already exist with id " + user.getId());
		user.setErrorCode("404");
		user.setErrorMessage("User already exist with id : " + user.getId());
		return user;
	}

	
	//@RequestMapping(value = "/user/", method = RequestMethod.PUT)
	@PutMapping("/user/")
	public User updateUser(@RequestBody User user) {
		logger.debug("->->->->calling method updateUser");
		if (userDAO.get(user.getId()) == null) {
			logger.debug("->->->->User does not exist with id " + user.getId());
			user = new User(); // ?
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist with id " + user.getId());
			return user;
		}

		userDAO.update(user);
		logger.debug("->->->->User updated successfully");
		return user;
	}


	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable("id") String id) {
		logger.debug("->->calling method getUser");
		logger.debug("->->id->->" + id);
		User user = userDAO.get(id);
		if (user == null) {
			logger.debug("->->->-> User does not exist wiht id" + id);
			user = new User(); //To avoid NLP - NullPointerException
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist");
			return user;
		}
		logger.debug("->->->-> User exist wiht id" + id);
		logger.debug(user.getName());
		return user;
	}


}
