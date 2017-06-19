package com.noggin.OCA1Atumation;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import junit.framework.Assert;


public class AWebDriverOCAFirstFireFoxWorkingTest {
 //WebDriver driver;

  @Test
  public void OCATestUsingFireFox() throws Exception {
	  
	 //Prints Out the Test Case Name in the console for debugging purpose
	  String TestCaseName = this.getClass().getName();
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	  //need latest gecko driver
	  System.setProperty("webdriver.gecko.driver","geckodriver.exe");
	  //creating porfile
	  ProfilesIni profile = new ProfilesIni();
	  
	  FirefoxProfile QAProfile = profile.getProfile("selenium");
	   
	  WebDriver driver = new FirefoxDriver(QAProfile);
	  //WebDriver driver=new FirefoxDriver();
	    
		driver.manage().window().maximize();
		//Add 10 secs implicit wait for each web elements
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Get URL
		driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
		Thread.sleep(5000);

		WebElement DirectLoginButton = driver.findElement(By.id("wgt-8"));
		DirectLoginButton.click();
	    Thread.sleep(2000);
	    
		WebElement UserNameTextBox=driver.findElement(By.id("wgt-Username"));
		WebElement PasswordTextBox=driver.findElement(By.id("wgt-Password"));
		
	  
	  //Enter UserName and Password
	  
	  UserNameTextBox.sendKeys("ssubhan");
	  PasswordTextBox.sendKeys("123test");
	  //seems like FireFox does not like space in search, so using xpath 
	  WebElement SignInButton=driver.findElement(By.xpath("//*[@id='wgt-Sign In']"));
	  SignInButton.click();
	 //User should land on OCA Home page
	  //We will get the Ttile of page and verify that Title contains words
	  Thread.sleep(5000);
	  String Title=driver.getTitle();
	  System.out.println(Title);
	  
	  //Now we will check if Title has "Home" and "Noggin OCA" in it
	 // Assert.assertEquals("Home (20) - Noggin OCA",Title);
	
   // Let the user actually see something! x
	  
	  driver.close();
  }
}