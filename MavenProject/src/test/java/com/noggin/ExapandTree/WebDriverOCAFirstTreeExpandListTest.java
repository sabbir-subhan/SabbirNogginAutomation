package com.noggin.ExapandTree;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.noggin.OCA1Automation.FindOCAElement;

import junit.framework.Assert;


public class WebDriverOCAFirstTreeExpandListTest {
 WebDriver driver;
 
 @BeforeClass
 
 public void setup() throws InterruptedException{
	  
	 //Prints Out the Test Case Name in the console for debugging purpose
	  String TestCaseName = this.getClass().getName();
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	 // Optional, if not specified, WebDriver will search your path for chromedriver.
	  System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

	  driver = new ChromeDriver();
	  //Add 60 secs for all page load in OCA
	  driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	 
	  //Add 30 secs implicit wait for each web elements
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  //Maximize browser window
	  driver.manage().window().maximize();
	  
	  driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
	  Thread.sleep(2000);
	  //git?? try
 }

  @Test
  public void hellOCA() throws Exception {
	  
	  FindOCAElement TestOCA=new FindOCAElement(driver);
	  //a.FindWebElementAndAction(driver, "wgt-7", "Click");
	  WebElement DirectLoginButton=TestOCA.FindandReturnWebElement("button|@|Direct login");
	  //a.isElementPresent(DirectLoginButton);
	  DirectLoginButton.click();
	  
	 
	  
	  //Find WebElement user name
	  WebElement wgtUsername=TestOCA.FindWebElementUsingID("wgt-Username");
	  //Selenium Action on WebElement
	  wgtUsername.sendKeys("sabbir");
	  
	//Find WebElement wgt-Password
	  WebElement wgtPassword=TestOCA.FindWebElementUsingID("wgt-Password");
	  //Selenium Action on WebElement
	  wgtPassword.sendKeys("1234test");
	  
	//Find Signin button
	  WebElement SignInButton=TestOCA.FindandReturnWebElement("button|@|Sign In");
	  //Selenium Action on WebElement
	  SignInButton.click();
	  
	  //We will go Settings->OCA designer->Lists
	  //Start
	  WebElement LevelOneToPmenu=TestOCA.FindandReturnWebElement("topmenu|@|Documents");
	  TestOCA.MouseOverMenu(LevelOneToPmenu);
	  LevelOneToPmenu.click();  
	  
	  
	  //Wait for 10 secs to Asset Index to expand
	  
	  TestOCA.waitForOCAElement("//div[@id='wgt-DocumentFolders']/ul/li",10);
	  
	  //Find and Click on Top node of tree
	  WebElement TopNodeofTree=TestOCA.FindandReturnWebElement("treeexp|@|wgt-DocumentFolders&amp;@&amp;Asset documents");
	  TopNodeofTree.click();
	  
	  //Find and Click on Second node of tree
	  WebElement Child1NodeofTree=TestOCA.FindandReturnWebElement("tree|@|wgt-DocumentFolders&amp;@&amp;Asset documents&amp;@&amp;NOGGIN Parent Asset");
	  Child1NodeofTree.click();
	  
	  
	  
	  //Find and Click on Second node of tree
	  WebElement Child2NodeofTree=TestOCA.FindandReturnWebElement("tree|@|wgt-DocumentFolders&amp;@&amp;Asset documents&amp;@&amp;NOGGIN Parent Asset&amp;@&amp;AR Parent Asset");
	  Child2NodeofTree.click();
	  
	  //Find and Click on Third node of tree
	  WebElement Child3NodeofTree=TestOCA.FindandReturnWebElement("tree|@|wgt-DocumentFolders&amp;@&amp;Asset documents&amp;@&amp;NOGGIN Parent Asset&amp;@&amp;AR Parent Asset&amp;@&amp;Easter Beaches Local Area Command");
	  Child3NodeofTree.click();
	  
	  
	  //Test
	  //End
	  
	Thread.sleep(10000);  // Let the user actually see something!
	  
  }
  @AfterClass
  public void close(){
	  driver.quit();
  }

}