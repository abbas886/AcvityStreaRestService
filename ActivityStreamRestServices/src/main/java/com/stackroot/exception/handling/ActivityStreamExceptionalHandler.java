package com.stackroot.exception.handling;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.NoHandlerFoundException;
public class ActivityStreamExceptionalHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityStreamExceptionalHandler.class);

	
	@ExceptionHandler(NoHandlerFoundException.class)
    public void noHandlerException(HttpServletRequest request, HttpServletResponse response,Exception e) throws IOException   {
		  logger.debug("Starting of the method noHandlerException ");  
		  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	     
		   PrintWriter writer = response.getWriter();
		   writer.println("HTTP Status 404 : No Handler for the specified request -> " + e.getMessage());
            logger.debug("Ending of the method noHandlerException ");  
          
    }
	
	@ExceptionHandler(IOException.class)
    public void handleIOException(HttpServletRequest request, HttpServletResponse response,Exception e) throws IOException   {
		  logger.debug("Starting of the method handleIOException ");  
		   PrintWriter writer = response.getWriter();
		   writer.println("HTTP Status 404 : IO Exception -> " + e.getMessage());
            logger.debug("Ending of the method handleIOException ");  
          
    }
	
	
	

}
