package com.noggin.OCA1Atumation;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.noggin.OCA1Automation.*;


public class AWebDriverOCAFirstwithLibaryTestwithVideoRecording {
 WebDriver driver;
//We will start capturing video of Selenium Test Execution which will run on the browser
 //First we instantiate an object of CaptureVidep class
 	CaptureVideo captureTestExecution=new CaptureVideo();
 
 @BeforeClass
 
 public void setup() throws InterruptedException{
	  
	 
	 //Prints Out the Test Case Name in the console for debugging purpose
	  String TestCaseName = this.getClass().getName();
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	  	  
	  //Now we pass TestCaseName as parameter. Please, note that video filed will be created 
	  //under C:\\SeleniumScriptVideos\\ folder with File name like "TestVideo-TestCase-<TestCaseName>-DateTime-<currentDateTime>" 
	  captureTestExecution.captureVideo(TestCaseName);
	  // Start the capture of the video
	  captureTestExecution.startVideo();
	  
	  // Optional, if not specified, WebDriver will search your path for chromedriver.
	  System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

	  driver = new ChromeDriver();
	  //Add 30 secs implicit wait for each web elements
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  //Maximize browser window
	  driver.manage().window().maximize();
	  
	  driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
	  Thread.sleep(2000);
 }

  @Test
  public void OCATestwithMenuAndSubMenu() throws Exception {
	  
	  FindOCAElement a=new FindOCAElement(driver);
	  HighlightElement highlight=new HighlightElement();
	  //a.FindWebElementAndAction(driver, "wgt-7", "Click");
	  WebElement DirectLoginButton=a.FindandReturnWebElement("button|@|Direct login");
	  //a.isElementPresent(DirectLoginButton);
	  a.ScrollElementIntoView(DirectLoginButton);
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
	  
  }
  @AfterClass
  public void close(){
	  driver.quit();
	  //Now we stop capturing the test video
	  captureTestExecution.stopVideo();;
	
  }

}