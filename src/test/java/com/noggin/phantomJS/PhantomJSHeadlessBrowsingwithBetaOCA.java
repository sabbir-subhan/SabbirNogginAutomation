package com.noggin.phantomJS;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.noggin.OCA1Automation.FindOCAElement;

public class PhantomJSHeadlessBrowsingwithBetaOCA {
	WebDriver driver;
	String TestCaseName = this.getClass().getName();
	
	@BeforeClass	 
	 public void setup() throws InterruptedException{
		  
		 //Prints Out the Test Case Name in the console for debugging purpose
		  
		  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
		  //Location of phanJS.exe file
	        File file = new File("phantomjs.exe");	//taking phantomJS.exe from project resource			
	        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
	        //Setting for noggins UAT OCA
	        ArrayList<String> cliArgsCap = new ArrayList<String>();
	        cliArgsCap.add("--web-security=no");
	        cliArgsCap.add("--ignore-ssl-errors=yes");
	       // cliArgsCap.add("--proxy-type=https");
	        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
	        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
	        driver = new PhantomJSDriver(capabilities);
	      //Add 10 secs implicit wait for each web elements
	  	  	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  	  	//Maximize browser window
	  	  	driver.manage().window().maximize();
	        //driver = new PhantomJSDriver();	
	        driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
	        Thread.sleep(5000);
	        System.out.println("Page title is: " + driver.getTitle());		
	        Thread.sleep(5000);
	 }
	 
	@Test
	  public void OCATestWithHeadLessBrowser() throws InterruptedException{
		  FindOCAElement a=new FindOCAElement(driver);
		  //a.FindWebElementAndAction(driver, "wgt-7", "Click");
		  WebElement DirectLoginButton=a.FindandReturnWebElement("button|@|Direct login");
		  //a.isElementPresent(DirectLoginButton);
		  DirectLoginButton.click();
		  
		 
		  System.out.println("Page title is: " + driver.getTitle());	
		  //Find WebElement user name
		  WebElement wgtUsername=a.FindWebElementUsingID("wgt-Username");
		  //Selenium Action on WebElement
		  wgtUsername.sendKeys("sabbir");
		  
		//Find WebElement wgt-Password
		  WebElement wgtPassword=a.FindWebElementUsingID("wgt-Password");
		  //Selenium Action on WebElement
		  wgtPassword.sendKeys("1234test");
		  a.captureScreenShot(driver,TestCaseName);//should take screenshot and put in a PNG file with file name same as Javafilename
		//Find Signin button
		  WebElement SignInButton=a.FindandReturnWebElement("button|@|Sign In");
		  //Selenium Action on WebElement
		  SignInButton.click();
		  a.captureScreenShot(driver,TestCaseName);//should take screenshot and put in a PNG file with file name same as Javafilename
		  Thread.sleep(5000);
		  System.out.println("Page title is: " + driver.getTitle());
		  
	}
	 
	  @AfterClass
	  public void close(){
		  driver.quit();
	  }

}
