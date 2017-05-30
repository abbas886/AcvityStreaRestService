package com.stackroot.activity.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class IndexRestService {

	private static final Logger logger = LoggerFactory.getLogger(IndexRestService.class);


	
	@GetMapping("/")
	public String server()
	{
		logger.debug("Starting of the method server");
		return "Server is started properly...";
	}
	
	
	
	@GetMapping("/server/")
	public String sayHello()
	{
		logger.debug("Starting of the method sayHello");
		return "Testing my rest controller";
	}
	
	


}
