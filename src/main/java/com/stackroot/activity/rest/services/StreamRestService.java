package com.stackroot.activity.rest.services;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroot.activity.dao.CircleDAO;
import com.stackroot.activity.dao.StreamDAO;
import com.stackroot.activity.model.Stream;
import com.stackroot.activity.util.Util;
import com.stackroot.activity.vo.UserHome;

@RestController
@RequestMapping("/api/stream")

public class StreamRestService {

	private static final Logger logger = LoggerFactory.getLogger(StreamRestService.class);

	@Autowired
	Stream stream;

	@Autowired
	StreamDAO streamDAO;

	@Autowired
	HttpSession httpSession;


	@Autowired
	private UserHome userHome;

	@Autowired
	CircleDAO circleDAO;

	@GetMapping("/refresh/")
	public UserHome refresh() {

		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");

		userHome.setMyInBox(streamDAO.getMyInbox(loggedInUserID));
		userHome.setMyCircles(circleDAO.getMyCircles(loggedInUserID));
		userHome.setCircleSize(circleDAO.getAllCircles().size());

		return userHome;

	}

	@GetMapping("/load/{firstResult}/{maxResult}")
	public UserHome load(@PathVariable("firstResult") int firstResult, @PathVariable("maxResult") int maxResult) {

		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");

		userHome.setMyInBox(streamDAO.getMyInbox(loggedInUserID, firstResult, maxResult));
		userHome.setMyCircles(circleDAO.getMyCircles(loggedInUserID));
		userHome.setCircleSize(circleDAO.getAllCircles().size());

		return userHome;

	}

	// @RequestMapping(value = "/user/", method = RequestMethod.POST)
	@PostMapping("/send/")
	public Stream sendMessage(@RequestBody Stream stream) {
		logger.debug("->->->->calling method sendMessage");
		logger.debug("Sending the message to circle : " + stream.getCircleID());
		logger.debug("Sending the message to User : " + stream.getReceiverID());

		if (stream.getSenderID().isEmpty())

		{
			logger.debug("Sender id not set from front end.  will set from rest services");
			String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
			stream.setSenderID(loggedInUserID);
		}
		logger.debug("Sending the message from User : " + stream.getSenderID());

		stream.setPostedDate(new Timestamp(System.currentTimeMillis()));
		stream.setId((int) (Math.random() * 1000000));

		if (!stream.getReceiverID().isEmpty()) {
			String message = stream.getMessage();
			// one to one message
			// remove @XXX from message
			logger.debug("It is one to one message");
			logger.debug("Message before remove id : " + message);
			message = Util.removeIDFromMessage(message);
			logger.debug("Message after remove id : " + message);
			stream.setMessage(message);

		}

		if (streamDAO.sendMessage(stream)) {
			stream.setStatusCode("200");
			stream.setStatusMessage("Successfully sent the message");
			logger.debug("Successfully sent the message");
		} else {
			stream.setStatusCode("404");
			stream.setStatusMessage("Could not send the message");
			logger.debug("Could not send the message");

		}

		logger.debug("->->->->Ending method sendMessage");
		return stream;
	}

}
