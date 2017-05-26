package com.stackroot.activity.vo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.stackroot.activity.model.BaseDomain;
import com.stackroot.activity.model.UserStream;
@Component
public class UserHome{
	
	private String errorCode;
	
	private String errorMessage;
	
	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	private List<UserStream> myInBox;


	private List<String> myCircles;


	public List<UserStream> getMyInBox() {
		return myInBox;
	}


	public void setMyInBox(List<UserStream> myInBox) {
		this.myInBox = myInBox;
	}


	public List<String> getMyCircles() {
		return myCircles;
	}


	public void setMyCircles(List<String> myCircles) {
		this.myCircles = myCircles;
	}


	

}