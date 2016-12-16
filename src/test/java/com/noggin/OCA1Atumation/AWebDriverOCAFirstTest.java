package com.noggin.OCA1Atumation;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.noggin.OCA1Automation.FindOCAElement;

import junit.framework.Assert;


public class AWebDriverOCAFirstTest {
 WebDriver driver;
 String TestCaseName = this.getClass().getName();
 
 @BeforeClass
 
 public void setup() throws InterruptedException{
	  
	 //Prints Out the Test Case Name in the console for debugging purpose
	  
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	 // Optional, if not specified, WebDriver will search your path for chromedriver.
	  System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

	  driver = new ChromeDriver();
	  //Add 10 secs implicit wait for each web elements
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Maximize browser window
	  driver.manage().window().maximize();
	  
	  driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
	  Thread.sleep(5000);
 }

  @Test
  public void hellOCA() throws Exception {
	  

	  FindOCAElement TestOCA=new FindOCAElement(driver);
	  TestOCA.captureScreenShot(driver,TestCaseName);//should take screenshot and put in a PNG file with file name same as Javafilename
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
	  Assert.assertTrue(Title.contains("Home"));
	  TestOCA.captureScreenShot(driver,TestCaseName);
	
	  Thread.sleep(5000);  // Let the user actually see something!
	  
	  driver.quit();
  }
}