package com.noggin.OCA1Atumation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.im4java.process.StandardStream;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.io.Files;
import com.noggin.OCA1AutomationSupport.FindOCAElement;

import junit.framework.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import com.noggin.OCA1AutomationSupport.HighlightElement;


public class OCAAnalyticsImageComparisonPOC {
 
	public WebDriver driver;
	
	/*
	 * Global data section
	 */
	String userAccountTypeName="System administrator";
    String DashBoard="Automation Dashborad";
    String DashBoardTab1="Auto1";
    String TestCaseName;
    String strDirectoy;
    
  //Test Screenshot directory
    public String testScreenShotDirectory;
    //Main Directory of the test code
    public String currentDir = System.getProperty("user.dir");
 
    //Main screenshot directory
    public String parentScreenShotsLocation = currentDir + "\\ScreenShots\\";
    
  //Main differences directory
    public String parentDifferencesLocation = currentDir + "\\Differences\\";
    

    //Image files
    public File baselineImageFile;
    public File actualImageFile;
    public File differenceImageFile;
    public File differenceFileForParent;
    
  //Element screenshot paths
    public String baselineScreenShotPath="C:\\seleniumScreenshot\\imageMagic\\baseline.png";
    public String actualScreenShotPath="C:\\seleniumScreenshot\\imageMagic\\Actual.png";
    public String differenceScreenShotPath="C:\\seleniumScreenshot\\imageMagic\\diff.png";;
	
  /* we are going to OCA landing page and maximize browser window
   * */
  //methods
    
  public void captureScreenFullPageShot(){
		 
		 //pause for 3 secs so page loads up
		 try {
			 Thread.sleep(3000);
		 	} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 	}
		 //get current date
		 DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss.SSS");
		 Date currentDate=new Date();
		 String DateTimeStr=dateFormat.format(currentDate);

		  // Take screenshot and store as a file format
		  File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		  
		  //Make a new folder in C:/seleniumScreenshot
		  strDirectoy ="C:\\seleniumScreenshot\\"+TestCaseName+DateTimeStr;
		  try{
		  					  
			// Create one directory
			  boolean success = (
			  new File(strDirectoy)).mkdir();
			  
			  if (success) {
				  //System.out.println("Directory: " + strDirectoy + " created");
				  }  
			  
		  }
		  catch(Exception e){//Catch exception if any
			  	System.err.println("Error: " + e.getMessage());
		  }
		  
		  //create a new file under newly created directory and save the file
		  	try {
		  		// now copy the  screenshot to desired location using copyFile method

		  		FileUtils.copyFile(src, new File(strDirectoy+"\\"+TestCaseName+"DateTime"+DateTimeStr+".png"));
		       }

		  	catch (IOException e)

		  	{

		  		//System.out.println(e.getMessage());

		    }

		}

    
    public void captureElementScreenshot(WebElement element) throws IOException{
    	  //Capture entire page screenshot as buffer.
    	  //Used TakesScreenshot, OutputType Interface of selenium and File class of java to capture screenshot of entire page.
    	  File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    	  
    	  //Used selenium getSize() method to get height and width of element.
    	  //Retrieve width of element.
    	  int ImageWidthEle = element.getSize().getWidth();
    	  //int ImageWidth = 700;
    	  //Retrieve height of element.
    	  int ImageHeightEle = element.getSize().getHeight();  
    	  //int ImageHeight = 400;  
    	  
    	  //Used selenium Point class to get x y coordinates of Image element.
    	  //get location(x y coordinates) of the element.
    	  Point point = element.getLocation();
    	  int xcordEle = point.getX();
    	  int ycordEle = point.getY();
    	  //int xcord = 770;
    	  //int ycord = 170;

    	  //Reading full image screenshot.
    	  BufferedImage img = ImageIO.read(screen);
    	  
    	  //cut Image using height, width and x y coordinates parameters.
    	  System.out.println("Elemet ImageWidth:"+ImageWidthEle+",ImageHeight:"+ImageHeightEle+",xcord:"+xcordEle+",ycord:"+ycordEle);
    	  //System.out.println("ImageWidth:"+ImageWidth+",ImageHeight:"+ImageHeight+",xcord:"+xcord+",ycord:"+ycord);
    	  BufferedImage dest = img.getSubimage(xcordEle, ycordEle, ImageWidthEle, ImageHeightEle);
    	  ImageIO.write(dest, "png", screen);
    	  
    	  //Used FileUtils class of apache.commons.io.
    	  //save Image screenshot In D: drive.
    	  FileUtils.copyFile(screen, new File(strDirectoy+"\\screenshotCrop.png"));
    	 }
    	
    
     //Create Folder Method
    public void createFolder (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
    }
    
    //Take Screenshot with AShot
    public Screenshot takeScreenshot (WebElement element) {
        //Take screenshot with Ashot
        Screenshot elementScreenShot = new AShot().takeScreenshot(driver, element);
      
 
        //Print element size
        String size = "Height: " + elementScreenShot.getImage().getHeight() + "\n" +
                "Width: " + elementScreenShot.getImage().getWidth() + "\n";
        System.out.print("Size: " + size);
 
        return elementScreenShot;
    }
    
    //Write WebElement screenshot to folder
    public void writeScreenshotToFolder (Screenshot screenshot, File imageFile) throws IOException {
        ImageIO.write(screenshot.getImage(), "PNG", imageFile);
    }
    


    
  //end methods  
    
  @BeforeClass
  
  public void setup() throws InterruptedException{
	  
	 //Prints Out the Test Case Name in the console for debugging purpose
	   TestCaseName = this.getClass().getName();
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	  
	//Create screenshot and differences folders if they are not exist
      createFolder(parentScreenShotsLocation);
      createFolder(parentDifferencesLocation);
	  
	// Optional, if not specified, WebDriver will search your path for chromedriver.
      
	  System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	  
	  //Start Chrome in Full screen
	  
	  	ChromeOptions options = new ChromeOptions();
	    options.addArguments("--start-fullscreen");
	    driver = new ChromeDriver(options);


	  //driver = new ChromeDriver();
	  //Add 10 secs implicit wait for each web elements
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  //Add 60 secs for all page load in OCA
	  driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	  //Maximize browser window
	  //driver.manage().window().maximize();
	  
	  driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
	  //Thread.sleep(5000);
  }
 
  /* In this test, we will test the following: 
   1. Login to OCA and land on OCA Home page
   2. Navigate to Analytics
   3. Take an image on Results->Number of messages
   4. Then do a image comparison
    
   */
   
  @Test
  public void CreateEventTest() throws Exception {

	  //1. Login to OCA and land on OCA Home page
	  		
	  	//Creating a Highlight  object
	  		HighlightElement highlight=new HighlightElement();
	  
			  WebElement DirectLoginButton = driver.findElement(By.id("wgt-8"));
			  DirectLoginButton.click();
			  //Thread.sleep(3000);
			  WebElement UserNameTextBox=driver.findElement(By.id("wgt-Username"));
			  WebElement PasswordTextBox=driver.findElement(By.id("wgt-Password"));
			  WebElement SignInButton=driver.findElement(By.id("wgt-Sign In"));
			  
			  //Enter UserName and Password and click signin
			  
			  UserNameTextBox.sendKeys("sabbirlow");
			  PasswordTextBox.sendKeys("123test");
			  SignInButton.click();
			  
			  //User should land on OCA Home page
			  //Selemium waits for 15 sec (max) for Home page to load
			  WebDriverWait waitforHomePage = new WebDriverWait(driver,15);
			  waitforHomePage.until(ExpectedConditions.titleContains("Home"));
			  
			 
			  //We will get the Title of page and verify that Title contains words
			  String Title=driver.getTitle();
			  System.out.println("Print Title of OCA Home Page: "+Title);
			  
			  
	  
	  //2.Navigate to Analytics
	  
			  //First find Settings Menu and mouse over on Settings Menu
			  WebElement AnalyticsMenu=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='Analytics']"));
			  
			  //Invoke Selenium Action class to perform Mouse over in Settings Menu
			  
			  Actions action=new Actions(driver);
			  
			  //moveToElement will move the mouse to the middle of Settings Menu, however it does NOT click Settings Menu
			  
			  action.moveToElement(AnalyticsMenu).click().perform();
			  Thread.sleep(3000);
			  //takke full page screenshot
			  this.captureScreenFullPageShot();
			  
			  //take screen shot of div element which contains chart
			  
			  //WebElement ChartDivContainerForNumberofMessages=driver.findElement(By.xpath("//*[@id='highcharts-2']//*[local-name() = 'svg']"));
			  WebElement OCAChartIcons=driver.findElement(By.xpath("//*[@id='wgt-simplequery']/div"));
			  highlight.highlightElement(driver, OCAChartIcons);
			  //System.out.println("Rectangle:Height"+ChartDivContainerForNumberofMessages.getRect().getHeight());
			 // System.out.println("Rectangle:Width"+ChartDivContainerForNumberofMessages.getRect().getWidth());
			  //System.out.println("Rectangle:x coodridanate"+ChartDivContainerForNumberofMessages.getRect().getX());
			 // System.out.println("Rectangle:y coodridanate"+ChartDivContainerForNumberofMessages.getRect().getY());
			  System.out.println("Location:"+OCAChartIcons.getLocation());
			  System.out.println("Size/Dimension:"+OCAChartIcons.getSize());
			  System.out.println("Width:"+OCAChartIcons.getAttribute("width"));
			  System.out.println("Height:"+OCAChartIcons.getAttribute("height"));
			  Thread.sleep(3000);

			//Call captureElementScreenshot function to capture screenshot of element.
		        this.captureElementScreenshot(OCAChartIcons);
		     
		       
			  			  		  
				
	}
  
  @AfterClass
  public void close(){
	  driver.quit();
  }

  
}