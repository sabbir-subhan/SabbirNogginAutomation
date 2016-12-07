package com.noggin.OCA1Atumation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;


public class AWebDriverOCAFirstFireFoxNotWorking {
 WebDriver driver;

  @Test
  public void hellOCA() throws Exception {
	  
	 //Prints Out the Test Case Name in the console for debugging purpose
	  String TestCaseName = this.getClass().getName();
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	  
	// Optional, if not specified, WebDriver will search your path for FireFOx.
		 System.setProperty("webdriver.gecko.driver","geckodriver.exe");
		  driver = new FirefoxDriver();
	  //Maximize browser window
	  driver.manage().window().maximize();
	  
	  driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
	  Thread.sleep(5000);

	  WebElement DirectLoginButton = driver.findElement(By.id("wgt-7"));
	  DirectLoginButton.click();
	  Thread.sleep(5000);
	  WebElement UserNameTextBox=driver.findElement(By.id("wgt-Username"));
	  WebElement PasswordTextBox=driver.findElement(By.id("wgt-Password"));
	  WebElement SignInButton=driver.findElement(By.id("wgt-Sign In"));
	  
	  //Enter UserName and Password
	  
	  UserNameTextBox.sendKeys("sabbir");
	  PasswordTextBox.sendKeys("1234test");
	  SignInButton.click();
	  //User should land on OCA Home page
	  //We will get the Ttile of page and verify that Title contains words
	  String Title=driver.getTitle();
	  System.out.println(Title);
	  
	  //Now we will check if Title has "Home" and "Noggin OCA" in it
	  Assert.assertEquals("Home (20) - Noggin OCA",Title);
	
	  Thread.sleep(5000);  // Let the user actually see something!
	  
	  driver.quit();
  }
}