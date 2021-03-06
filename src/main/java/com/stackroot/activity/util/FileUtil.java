package com.stackroot.activity.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class FileUtil {
	
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);
	
	//private static String path = "resources/imgages/";
	private static String path =System.getProperty("catalina.home");
	private static String imageFolder = "ActivityStream";
	/*private static String path = "D:/Collaboration_images/";*/
	

	public static void upload( MultipartFile file, String fileName) {
		log.debug("Starting of the method upload");
		if (!file.isEmpty()) {
			
			try {
			byte[] bytes = file.getBytes();

			// Creating the directory to store file
			File dir = new File(path+File.separator +imageFolder);
			if (!dir.exists())
				dir.mkdirs(); //Make/create directory
			
			// Create the file on server
			//D:\\ShoppingCart\\Images\\fileName.jpg
			File serverFile = new File(dir.getAbsolutePath()
					+ File.separator + fileName+".jpg");
			
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

			log.info("Server File Location="
					+ serverFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}}
		
		log.debug("Ending of the method upload");

	}
	
	

	
}