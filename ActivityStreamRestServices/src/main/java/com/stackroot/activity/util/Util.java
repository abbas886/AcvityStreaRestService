package com.stackroot.activity.util;



public class Util {
	
	
	/**
	 * For one to one message,  need to remove @ID from the message
	 * @param message
	 * @return
	 */
	public static String removeIDFromMessage(String message)
	{
		if(message!=null && message.charAt(0)=='@')
		{
			
			message =	message.replaceFirst("[@]\\w+\\s", "");
		
		}
		
				return message;
	}
	
	public static void main(String[] args) {
		System.out.println(Util.removeIDFromMessage("@Abbas hello from abbas"));
		
		System.out.println(Util.removeIDFromMessage("Hello from abbas"));
	}

}
