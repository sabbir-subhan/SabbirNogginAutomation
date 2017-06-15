package com.noggin.OCA1Atumation;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.noggin.OCA1Automation.*;


public class AWebDriverOCAFirstwithLibaryTest {
 WebDriver driver;
 
 @BeforeClass
 
 public void setup() throws InterruptedException{
	  
	 //Prints Out the Test Case Name in the console for debugging purpose
	  String TestCaseName = this.getClass().getName();
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	 // Optional, if not specified, WebDriver will search your path for chromedriver.
	  System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

	  driver = new ChromeDriver();
	  //Add 60 secs implicit wait for each web elements
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Maximize browser window
	  driver.manage().window().maximize();
	  
	  driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
	  Thread.sleep(5000);
 }

  @Test
  public void OCATestwithMenuAndSubMenu() throws Exception {
	  
	  FindOCAElement a=new FindOCAElement(driver);
	  HighlightElement highlight=new HighlightElement();
	  //a.FindWebElementAndAction(driver, "wgt-7", "Click");
	  WebElement DirectLoginButton=a.FindandReturnWebElement("button|@|Direct login");
	  //a.isElementPresent(DirectLoginButton);
	  highlight.highlightElement(driver, DirectLoginButton);
	  DirectLoginButton.click();
	  
	 
	  
	  //Find WebElement user name
	  WebElement wgtUsername=a.FindWebElementUsingID("wgt-Username");
	  //Selenium Action on WebElement
	  highlight.highlightElement(driver, wgtUsername);
	  wgtUsername.sendKeys("ssubhan");
	  
	//Find WebElement wgt-Password
	  WebElement wgtPassword=a.FindWebElementUsingID("wgt-Password");
	  //Selenium Action on WebElement
	  highlight.highlightElement(driver, wgtPassword);
	  wgtPassword.sendKeys("123test");
	  
	//Find Signin button
	  WebElement SignInButton=a.FindandReturnWebElement("button|@|Sign In");
	  //Selenium Action on WebElement
	  highlight.highlightElement(driver, SignInButton);
	  SignInButton.click();
	  
	  //We will go Settings->OCA designer->Lists
	  //Start
	  //Mouse over on to Settings Top menu
	  WebElement SettingsTopMenu=a.FindandReturnWebElement("topmenu|@|Settings");
	  highlight.highlightElement(driver, SettingsTopMenu);
	  a.MouseOverMenu(SettingsTopMenu);
	  
	  //Find and Mouse over submenu-OCA designer
	  WebElement OCADesignerSubMenu=a.FindandReturnWebElement("submenu|@|0:OCA designer");
	  highlight.highlightElement(driver, OCADesignerSubMenu);
	  a.MouseOverMenu(OCADesignerSubMenu);
	  
	//Find and Mouse over submenu-Lists
	  WebElement ListsSubMenu=a.FindandReturnWebElement("submenu|@|1:Lists");
	  highlight.highlightElement(driver, ListsSubMenu);
	  a.MouseOverMenu(ListsSubMenu);
	  //End
	  
	//We will go Settings->OCA designer->Incidents->Asset types
	  //Start
	  //Mouse over on to Settings Top menu
	  WebElement SettingsTopMenu1=a.FindandReturnWebElement("topmenu|@|Settings");
	  highlight.highlightElement(driver, SettingsTopMenu1);
	  a.MouseOverMenu(SettingsTopMenu1);
	  
	  //Find and Mouse over submenu-OCA designer
	  WebElement OCADesignerSubMenu1=a.FindandReturnWebElement("submenu|@|0:OCA designer");
	  highlight.highlightElement(driver, OCADesignerSubMenu1);
	  a.MouseOverMenu(OCADesignerSubMenu1);
	  
	//Find and Mouse over submenu-Incidents
	  WebElement IncidentsSubMenu=a.FindandReturnWebElement("submenu|@|1:Incidents");
	  highlight.highlightElement(driver, IncidentsSubMenu);
	  a.MouseOverMenu(IncidentsSubMenu);
	  
	//Find and Mouse over submenu-Incidents
	  WebElement AssetTypeSubMenu=a.FindandReturnWebElement("submenu|@|2:Asset types");
	  highlight.highlightElement(driver, AssetTypeSubMenu);
	  a.MouseOverMenu(AssetTypeSubMenu);
	  
	  //End
	  
	//We will go Incidents->Plans>Plans
	  //start
	  //Incidents top menu
	  WebElement IncidentsTopMenu=a.FindandReturnWebElement("topmenu|@|Incidents");
	  highlight.highlightElement(driver, IncidentsTopMenu);
	  a.MouseOverMenu(IncidentsTopMenu);
	  
	  //Plans submenu
	  WebElement PlanSubMenu=a.FindandReturnWebElement("submenu|@|0:Plans");
	  highlight.highlightElement(driver, PlanSubMenu);
	  a.MouseOverMenu(PlanSubMenu);
	  
	//Plans submenu
	  WebElement PlanSubSubMenu=a.FindandReturnWebElement("submenu|@|1:Plans");
	  highlight.highlightElement(driver, PlanSubSubMenu);
	  a.MouseOverMenu(PlanSubSubMenu);
	 

	  
	  
	
	  /*//Find and Mouse over submenu-OCA designer
	  WebElement OCADesignerSubMenu=a.FindSubMenu("Settings", "OCA designer");
	  a.MouseOverMenu(OCADesignerSubMenu);
	
	  //Find and Mouse over submenu-Incidents
	  WebElement IncidentsSubMenu=a.FindSubMenu("OCA designer", "Incidents");
	  a.MouseOverMenu(IncidentsSubMenu);
	  
		
	  //Find and Mouse over submenu-Event types
	  WebElement EventTypeSubMenu=a.FindSubMenu("Incidents", "Event types");
	  a.MouseOverMenu(EventTypeSubMenu);
	  
	  
	//Mouse over on to Settings Top menu
	  WebElement TasksTopMenu=a.FindandReturnWebElement("topmenu|@|Tasks");
	  a.MouseOverMenu(TasksTopMenu);
	//Mouse over on to Settings Top menu
	  WebElement IncidentsTopMenu=a.FindandReturnWebElement("topmenu|@|Incidents");
	  a.MouseOverMenu(IncidentsTopMenu);
	  */
	  
	//Find and Mouse over submenu
	  
	  
	  
	  
	  
	  //FindOCAElement c=new FindOCAElement();
	  //a.FindWebElementAndAction("wgt-Password", "sendKeys", "1234test");
	  //FindOCAElement d=new FindOCAElement();
	  //a.FindWebElementAndAction("wgt-Sign In", "Click");
	  
	  //WebElement DirectLoginButton = driver.findElement(By.id("wgt-7"));
	 // DirectLoginButton.click();
	  //Thread.sleep(5000);
	  //WebElement UserNameTextBox=driver.findElement(By.id("wgt-Username"));
	 // WebElement PasswordTextBox=driver.findElement(By.id("wgt-Password"));
	  //WebElement SignInButton=driver.findElement(By.id("wgt-Sign In"));
	  
	  //Enter UserName and Password
	  
	 //.sendKeys("sabbir");
	 // PasswordTextBox.sendKeys("1234test");
	 // SignInButton.click();
	  //User should land on OCA Home page
	  //We will get the Ttile of page and verify that Title contains words
	  String Title=driver.getTitle();
	  System.out.println(Title);
	  
	  //Now we will check if Title has "Home" and "Noggin OCA" in it
	  //Assert.assertEquals("Home (20) - Noggin OCA",Title);
	
	  //Thread.sleep(5000);  // Let the user actually see something!
	  
  }
  @AfterClass
  public void close(){
	  driver.quit();
  }

}