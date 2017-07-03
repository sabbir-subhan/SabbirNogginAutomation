package com.noggin.OCA1Atumation;
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

import com.noggin.OCA1AutomationSupport.FindOCAElement;

import junit.framework.Assert;


public class AWebDriverOCACreateAssetwithLibraryTest {
 WebDriver driver;
 
 @BeforeClass
 
 public void setup() throws InterruptedException{
	  	 //Prints Out the Test Case Name in the console for debugging purpose
	  String TestCaseName = this.getClass().getName();
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	 // Optional, if not specified, WebDriver will search your path for chromedriver.
	  System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

	  driver = new ChromeDriver();
	  //Add 30 secs implicit wait for each web elements
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  //Maximize browser window
	  driver.manage().window().maximize();
	  
	  driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
	  Thread.sleep(5000);
 }

  @Test
  public void CreateAssetusigLibarary() throws Exception {
	  
	  FindOCAElement TestOCA=new FindOCAElement(driver);
	  //a.FindWebElementAndAction(driver, "wgt-7", "Click");
	  WebElement DirectLoginButton=TestOCA.FindandReturnWebElement("button|@|Direct login");
	  //a.isElementPresent(DirectLoginButton);
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
	  
	  //User should land on OCA Home page
	  //We will get the Ttile of page and verify that Title contains words
	  String Title=driver.getTitle();
	  System.out.println(Title);
	  
	  //Now we will check if Title has "Home" and "Noggin OCA" in it
	  Assert.assertTrue(Title.contains("Home"));;
	  
	  //Find and click TopMenu Assets
	  WebElement AssetTopMenu=TestOCA.FindandReturnWebElement("topmenu|@|Assets");
	  AssetTopMenu.click();
	  //Find and click New Screentab
	  WebElement screentabsbuttonwgtTabsNew=TestOCA.FindandReturnWebElement("screentabsbutton|@|wgt-Tabs=New");
	  //opens Contex Menu so we will click and wait for 5 secs to make context menu visible
	  TestOCA.ClickContextMenu(screentabsbuttonwgtTabsNew);;
	  
	  //Select an Asset Type from Contex Menu
	  WebElement contextMenuwithAssetTypeName=TestOCA.FindandReturnWebElement("contextmenu|@|Automation Asset");
	  contextMenuwithAssetTypeName.click();
	  
	  /*We should be in Asset Creation page
	   * We can do some assertion here to ensure that we are in correct gage
	   * Example of Assertion: check page title, check that correct Asset type label displays in the page
	   */
	  //TO DO- Assertions code for Asset Create page
	  
	  //Now fill up fields in Asset Type
	  // We are using ID to identify fields in Asset Creation form
	  // According to Brett, these IDs are unique for a particular Asset type for a specific OCA instance
	  
	  //Find Name field input box and write something
	  //Also enter a random Name followed by a prefix.
	  WebElement wgtData1=TestOCA.FindWebElementUsingID("wgt-Data-99201");
	  wgtData1.sendKeys("Automantion :"+TestOCA.RandomText());
	  
	  //Click Save button and finish creating asset
	  WebElement ButtonSave=TestOCA.FindandReturnWebElement("button|@|Save");
	  ButtonSave.click();
	  
	  Thread.sleep(5000);  // Let the user actually see something!
	  
  }
  @AfterClass
  public void close(){
	  driver.quit();
  }

}