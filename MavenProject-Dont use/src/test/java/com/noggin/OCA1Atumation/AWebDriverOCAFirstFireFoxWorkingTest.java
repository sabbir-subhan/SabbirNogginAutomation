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
  public void hellOCA() throws Exception {
	  
	 //Prints Out the Test Case Name in the console for debugging purpose
	  String TestCaseName = this.getClass().getName();
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	  //System.setProperty("webdriver.gerko.driver","geckodriver.exe");
	  //Need to use FireFox Profile 
	  //Create a Profile in FireFox called "selenium"
	  //Open up FireFox profile manager using  firefox.exe -p run menu 
	  //create a profile called selenium and set uo proxy configuration and accept SSL certificate for interanl OCAs

	  	ProfilesIni profile = new ProfilesIni();
	  	FirefoxProfile myprofile = profile.getProfile("selenium");
	    WebDriver driver = new FirefoxDriver(myprofile);
	   // DesiredCapabilities dc = DesiredCapabilities.firefox();
	    
		driver.manage().window().maximize();
		//Add 10 secs implicit wait for each web elements
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Get URL
		driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
		Thread.sleep(5000);

		WebElement DirectLoginButton = driver.findElement(By.id("wgt-7"));
		DirectLoginButton.click();
	    Thread.sleep(5000);
	    
		WebElement UserNameTextBox=driver.findElement(By.id("wgt-Username"));
		WebElement PasswordTextBox=driver.findElement(By.id("wgt-Password"));
		
	  
	  //Enter UserName and Password
	  
	  UserNameTextBox.sendKeys("sabbir");
	  PasswordTextBox.sendKeys("1234test");
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
	  
	  driver.quit();
  }
}