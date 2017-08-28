package com.noggin.OCA1Atumation;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

import org.testng.annotations.Test;


public class ImageComparisonwithImageMagicTest {
 
  

    //Image files
    public File baselineImageFile;
    public File actualImageFile;
    public File differenceImageFile;
    public File differenceFileForParent;
    
  //Image file path
    public String baselineScreenShotPath="C:\\seleniumScreenshot\\imageMagic\\baseline.png";
    public String actualScreenShotPath="C:\\seleniumScreenshot\\imageMagic\\Actual.png";
    
  //get current date
	 DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
	 Date currentDate=new Date();
	 String DateTimeStr=dateFormat.format(currentDate);
	 //difference image should produce as "diffImageMagic_ddMMyyyy_HHmmss.png" e.g diffImageMagic_28082017_113123.png
    public String differenceScreenShotPath="C:\\seleniumScreenshot\\imageMagic\\diffImageMagic_"+DateTimeStr+".png";
	

  //methods
    
  //ImageMagick Compare Method
    public void compareImagesWithImageMagick (String expected, String actual, String difference) throws Exception {
        // This class implements the processing of os-commands using a ProcessBuilder.
        // This is the core class of the im4java-library where all the magic takes place.
        //ProcessStarter.setGlobalSearchPath("C:\\Program Files\\ImageMagick-7.0.6-Q16");
 
        // This instance wraps the compare command
        CompareCmd compare = new CompareCmd();
 
        // Set the ErrorConsumer for the stderr of the ProcessStarter.
        compare.setErrorConsumer(StandardStream.STDERR);
 
        // Create ImageMagick Operation Object
        IMOperation cmpOp = new IMOperation();
 
        //Add option -fuzz to the ImageMagick commandline
        //With Fuzz we can ignore small changes
        cmpOp.fuzz(10.0);
 
        //The special "-metric" setting of 'AE' (short for "Absolute Error" count), will report (to standard error),
        //a count of the actual number of pixels that were masked, at the current fuzz factor.
        cmpOp.metric("AE");
 
        // Add the expected image
        cmpOp.addImage(expected);
 
        // Add the actual image
        cmpOp.addImage(actual);
 
        // This stores the difference
        cmpOp.addImage(difference);
 
        try {
            //Do the compare
            System.out.println ("!----Comparison Started!");
            compare.run(cmpOp);
            System.out.println ("!---Comparison Finished AND SUCCESSFUL");
        }
        catch (Exception ex) {
            System.out.print(ex);
            System.out.println (System.lineSeparator()+"!----Comparison Failed!. Please check :"+differenceScreenShotPath+" file");
            //Put the difference image to the global differences folder
            //Files.copy(differenceImageFile,differenceFileForParent);
            //throw ex;
        }
    }
 
    //Compare Operation
    public void doComparison () throws Exception {
        
    	   this.compareImagesWithImageMagick(baselineScreenShotPath, actualScreenShotPath, differenceScreenShotPath);
           
    }
    

   
  @Test
  public void compareImagewithImageMagic() throws Exception {

	          this.doComparison();      
			  			  		  
				
	}
  
}