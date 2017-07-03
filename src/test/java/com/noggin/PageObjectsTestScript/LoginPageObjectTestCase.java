package com.noggin.PageObjectsTestScript;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.noggin.OCA1Automation.*;
import com.noggin.PageObjects.OCA1HomePage;
import com.noggin.PageObjects.OCA1LoginPage;


public class LoginPageObjectTestCase {
 WebDriver driver;
 OCA1LoginPage oc1Loginpage;
 OCA1HomePage oca1HomePage;
 
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
	  Thread.sleep(2000);
 }

  @Test
  public void test_Log_in_OCA_using_valid_credentials() throws InterruptedException{
	  
	//Create Login Page object
	  oc1Loginpage=new OCA1LoginPage(driver);
	//Verify login page title
	  String loginPageTitle = oc1Loginpage.getOCA1LoginPageTitle();
	  System.out.println(loginPageTitle);
	  
	//Click DirectLogin button
	  oc1Loginpage.clickDirectLoginButton();
	  
	//login to OCA1
	//enter valid credentials
	  oc1Loginpage.enterLoginDetail("ssubhan", "123test");
	//click Sign in button
	  oc1Loginpage.clickSigninButton();

	//Create Home Page object
	  oca1HomePage=new OCA1HomePage(driver);
	  //Verify OCA Home page title
	  String homePageTitle=oca1HomePage.getOCA1HomePageTitle();
	  System.out.println(homePageTitle);
	 //Click PersonIcon on OCA Home Page
	  oca1HomePage.clickBusinessAsUsualOnTop();
	  oca1HomePage.clickClockIconOnTop();
	  oca1HomePage.clickBellIconOnTop();
	  oca1HomePage.clickPersonIconOnTop();
	  oca1HomePage.clickPencilIconOnTop();
	  Thread.sleep(2000);	 
  }
	  
  @AfterClass
  public void close(){
	  driver.quit();
  }

}