package com.stackroot.activity.vo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.stackroot.activity.model.BaseDomain;
import com.stackroot.activity.model.Stream;

@Component
public class UserHome extends BaseDomain{
	
	private int circleSize;
	private List<Stream> myInBox;

	private List<String> myCircles;


	
	public int getCircleSize() {
		return circleSize;
	}


	public void setCircleSize(int circleSize) {
		this.circleSize = circleSize;
	}



	public List<Stream> getMyInBox() {
		return myInBox;
	}


	public void setMyInBox(List<Stream> myInBox) {
		this.myInBox = myInBox;
	}



	public List<String> getMyCircles() {
		return myCircles;
	}


	public void setMyCircles(List<String> myCircles) {
		this.myCircles = myCircles;
	}


	

}
