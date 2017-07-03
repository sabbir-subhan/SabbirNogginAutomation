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

import com.noggin.OCA1AutomationSupport.*;

import junit.framework.Assert;


public class WebDriverOCAFirstTreeExanndDocumentTest {
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
	  //git??
 }

  @Test
  public void OCAFirstTreeExpandDocumentTest() throws Exception {
	  
	  HighlightElement highLight=new HighlightElement();
	  FindOCAElement TestOCA=new FindOCAElement(driver);
	  //a.FindWebElementAndAction(driver, "wgt-7", "Click");
	  WebElement DirectLoginButton=TestOCA.FindandReturnWebElement("button|@|Direct login");
	  //a.isElementPresent(DirectLoginButton);
	  
	  highLight.highlightElement(driver, DirectLoginButton);
	  DirectLoginButton.click();
	  
	 
	  
	  //Find WebElement user name
	  WebElement wgtUsername=TestOCA.FindWebElementUsingID("wgt-Username");
	  //Selenium Action on WebElement
	  wgtUsername.sendKeys("ssubhan");
	  
	//Find WebElement wgt-Password
	  WebElement wgtPassword=TestOCA.FindWebElementUsingID("wgt-Password");
	  //Selenium Action on WebElement
	  wgtPassword.sendKeys("123test");
	  
	//Find Signin button
	  WebElement SignInButton=TestOCA.FindandReturnWebElement("button|@|Sign In");
	  //Selenium Action on WebElement
	  SignInButton.click();
	  
	  //We will go 
	  //Start with Mouseover on Icidents
	  WebElement LevelOneToPmenu=TestOCA.FindandReturnWebElement("topmenu|@|Incidents");
	  TestOCA.MouseOverMenu(LevelOneToPmenu);
	  //Start with Click on Events
	  WebElement LevelOneSubmenu=TestOCA.FindandReturnWebElement("submenu|@|0:Events");
	  LevelOneSubmenu.click();
	
	  
	  //Wait for 10 secs to Event Index Index to expand
	  
	  TestOCA.waitForOCAElement("//div[@id='wgt-summary-EMES']",10);
	 
	  
	  //Find and Click on Top node of tree
	  WebElement TopNodeofTree=TestOCA.FindandReturnWebElement("treeexpEvent|@|wgt-summary-EMES&amp;@&amp;Primary Event");
	  TopNodeofTree.click();
	  
	  WebElement Child1NodeofTree=TestOCA.FindandReturnWebElement("treeexpEvent|@|wgt-summary-EMES&amp;@&amp;Primary Event&amp;@&amp;Child Event1");
	  Child1NodeofTree.click();
	  
	  WebElement Child2NodeofTree=TestOCA.FindandReturnWebElement("treeexpEvent|@|wgt-summary-EMES&amp;@&amp;Primary Event&amp;@&amp;Child Event1&amp;@&amp;Child3");
	  Child2NodeofTree.click();
	  
	  //Find and Click on Second node of tree
	 // WebElement Child1NodeofTree=TestOCA.FindandReturnWebElement("tree|@|wgt-summary-Assets&amp;@&amp;NOGGIN Parent Asset&amp;@&amp;AR Parent Asset");
	  //Child1NodeofTree.click();
	  
	  //Find and Click on Third node of tree
	 // WebElement Child2NodeofTree=TestOCA.FindandReturnWebElement("tree|@|wgt-summary-Assets&amp;@&amp;NOGGIN Parent Asset&amp;@&amp;AR Parent Asset&amp;@&amp;Kings Cross LAC");
	 // Child2NodeofTree.click();
	  

	  
	  //End test
	   
	  
	  
	Thread.sleep(5000);  // Let the user actually see something!
	  
  }
  @AfterClass
  public void close(){
	  driver.quit();
  }

}