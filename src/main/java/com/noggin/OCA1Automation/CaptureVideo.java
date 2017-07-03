package com.noggin.OCA1Automation;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

public class CaptureVideo {
	ATUTestRecorder recorder;
	public void captureVideo(String FileName){
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss"); 
		  Date date = new Date(); 
		  
		 //Create directory to save the video
		  String strDirectoy ="C:\\SeleniumScriptVideos\\";
		  try{
		  					  
			// Create one directory
			  boolean success = (
			  new File(strDirectoy)).mkdir();
			  
			  if (success) {
				  System.out.println("Directory: " + strDirectoy + " created");
				  }  
			  
		  		}
		  catch(Exception e){//Catch exception if any
			  	System.err.println("Error: " + e.getMessage());
		  		}
		//Created object of ATUTestRecorder 
		  //Provide directory path to store videos i first parameter,file name in second parameter and true/false in third parameter-false as third parameter for the constructor the audio recording will be disabled. 
		  try {
			  		recorder = new ATUTestRecorder(strDirectoy,"TestVideo-TestCase-"+FileName+"-DateTime-"+dateFormat.format(date),false);
		  		} 
		  catch (ATUTestRecorderException e) {
			
		  			e.printStackTrace();
		} 
	}
	
	public void startVideo(){
		 try {
		  		recorder.start();
	  		} 
	  catch (ATUTestRecorderException e) {
		
		  		System.out.println("Error staritng the video");
		  		e.printStackTrace();
	  } 
	}
		 
		 public void stopVideo(){
			 try {
			  		recorder.stop();
		  		} 
		  catch (ATUTestRecorderException e) {
			
			  		System.out.println("Error stoping the video");
			  		e.printStackTrace();
		} 	 
	}

}
