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
import com.stackroot.activity.util.Util;



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
		logger.debug("Sending the message to circle : " + stream.getCircleID());
		logger.debug("Sending the message to User : " + stream.getReceiverID());
		
	
	if(stream.getSenderID().isEmpty())
		
	{
		logger.debug("Sender id not set from front end.  will set from rest services");
		String 	loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		stream.setSenderID(loggedInUserID);
	}
	logger.debug("Sending the message from User : " + stream.getSenderID());

	stream.setPostedDate(new Timestamp(System.currentTimeMillis()));
	stream.setId((int)( Math.random()*1000000));
	
	if(!stream.getReceiverID().isEmpty())
	{
		String message = stream.getMessage();
		//one to one message
		//remove @XXX from message
		logger.debug("It is one to one message");
		logger.debug("Message before remove id : " + message);
		message = Util.removeIDFromMessage(message);
		logger.debug("Message after remove id : " + message);
		stream.setMessage(	message);
		
	}
		
		 if (streamDAO.sendMessage( stream))
		 {
			 stream.setErrorCode("200");
			 stream.setErrorMessage("Successfully sent the message");
			 logger.debug("Successfully sent the message");
		 }
		 else
		 {
			 stream.setErrorCode("404");
			 stream.setErrorMessage("Could not send the message");
			 logger.debug("Could not send the message");
			 
		 }
		
		 logger.debug("->->->->Ending method sendMessage");
		return stream;
	}

	
	

}
