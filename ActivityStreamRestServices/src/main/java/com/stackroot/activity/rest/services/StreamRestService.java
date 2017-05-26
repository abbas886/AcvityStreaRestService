package com.stackroot.activity.rest.services;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroot.activity.dao.StreamDAO;
import com.stackroot.activity.model.Stream;



@RestController
public class StreamRestService {

	private static final Logger logger = LoggerFactory.getLogger(StreamRestService.class);

	@Autowired Stream stream;
	
	@Autowired
	StreamDAO streamDAO;
	
	@Autowired HttpSession httpSession;
	
	
		//@RequestMapping(value = "/user/", method = RequestMethod.POST)
	@PostMapping("/send/")
	public Stream sendMessage(@RequestBody Stream stream) {
		logger.debug("->->->->calling method sendMessage");
	String 	loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
	if(loggedInUserID==null)
	{
		stream.setErrorCode("404");
		stream.setErrorCode("You need to login to send message");
		return stream;
	}
	
	stream.setSenderID(loggedInUserID);
	stream.setPostedDate(new Timestamp(System.currentTimeMillis()));
	stream.setId((int)( Math.random()*1000000));
		
		 if (streamDAO.sendMessage( stream))
		 {
			 stream.setErrorCode("200");
			 stream.setErrorMessage("Successfully sent the message");
		 }
		 else
		 {
			 stream.setErrorCode("404");
			 stream.setErrorMessage("Could not send the message");
			 
		 }
		
		 logger.debug("->->->->Ending method sendMessage");
		return stream;
	}

	
	

}
