package com.noggin.OCA1Atumation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import org.testng.annotations.Test;
import com.noggin.OCA1AutomationSupport.CompareImagePixelByPxiel;


public class ImageComparisonWithNativeJavaTest<ComapreImagePixelByPixel> {
 
    
  //Actual, Baseline and difference screenshot directory paths
    public String baselineScreenShotPath="C:\\seleniumScreenshot\\imageMagic\\baseline.png";
    public String actualScreenShotPath="C:\\seleniumScreenshot\\imageMagic\\Actual.png";
    //get current date
  	 DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
  	 Date currentDate=new Date();
  	 String DateTimeStr=dateFormat.format(currentDate);
  	 //difference image should produce as "diffImageMagic_ddMMyyyy_HHmmss.png" e.g diffJavaNative_28082017_113123.png
    public String differenceScreenShotPath="C:\\seleniumScreenshot\\imageMagic\\diffJavaNative_"+DateTimeStr+".png";
    
    
  //Image files
    public File baselineImageFile=new File(baselineScreenShotPath);
    public File actualImageFile=new File(actualScreenShotPath);
    
    public File differenceImageFile=new File(differenceScreenShotPath);
    
  @Test
  public void CompareImageTest() throws Exception {
	  //Copy image files to buffered image
	  BufferedImage auctualBufferedImage = ImageIO.read(actualImageFile);
	  BufferedImage baseLineBufferedImage = ImageIO.read(baselineImageFile);
	 	 
	  //get difference as buffered image
	  BufferedImage diffBufferedImage=CompareImagePixelByPxiel.getDifferenceImage(auctualBufferedImage, baseLineBufferedImage);
	  ImageIO.write(diffBufferedImage, "png",differenceImageFile);
				
	}
  
}