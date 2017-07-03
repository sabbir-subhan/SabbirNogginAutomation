package com.noggin.OCA1Atumation;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.noggin.OCA1AutomationSupport.FindOCAElement;

import junit.framework.Assert;


public class WebDriverOCACreateEventTest {
 
	public WebDriver driver;
	
	/*
	 * Global data section
	 */
	String userAccountTypeName="System administrator";
    String DashBoard="Automation Dashborad";
    String DashBoardTab1="Auto1";
	
  /* we are going to OCA landing page and maximize browser window
   * */
  
  @BeforeClass
  
  public void setup() throws InterruptedException{
	  
	 //Prints Out the Test Case Name in the console for debugging purpose
	  String TestCaseName = this.getClass().getName();
	  System.out.println("TEST CASE RUNNING :"+ TestCaseName);
	  
	// Optional, if not specified, WebDriver will search your path for chromedriver.
	  System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

	  driver = new ChromeDriver();
	  //Add 10 secs implicit wait for each web elements
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  //Add 60 secs for all page load in OCA
	  driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	  //Maximize browser window
	  driver.manage().window().maximize();
	  
	  driver.get("https://im1.oca-test-beta-el7sec.lan.noggin.com.au/directlogin.html");
	  //Thread.sleep(5000);
  }
 
  /* In this test, we will test the following: 
   1. Login to OCA and land on OCA Home page
   2. Navigate to Settings->OCA Designer->Incidents->Event types
   3. Create an Event type with some random name and field (what are the basic)
   4. After, event type is created, navigate to Incident->Events
   5. Click New.
   6.Select Event type which is created in step3 and fill up details with random text (??)
   7. Save that event and validate that event is successfully created in OCA.
    
   */
   
  @Test
  public void CreateEventTest() throws Exception {

	  //1. Login to OCA and land on OCA Home page
	  
			  WebElement DirectLoginButton = driver.findElement(By.id("wgt-7"));
			  DirectLoginButton.click();
			  //Thread.sleep(3000);
			  WebElement UserNameTextBox=driver.findElement(By.id("wgt-Username"));
			  WebElement PasswordTextBox=driver.findElement(By.id("wgt-Password"));
			  WebElement SignInButton=driver.findElement(By.id("wgt-Sign In"));
			  
			  //Enter UserName and Password and click signin
			  
			  UserNameTextBox.sendKeys("sabbir");
			  PasswordTextBox.sendKeys("1234test");
			  SignInButton.click();
			  
			  //User should land on OCA Home page
			  //Selemium waits for 15 sec (max) for Home page to load
			  WebDriverWait waitforHomePage = new WebDriverWait(driver,15);
			  waitforHomePage.until(ExpectedConditions.titleContains("Home"));
			  
			 
			  //We will get the Title of page and verify that Title contains words
			  String Title=driver.getTitle();
			  System.out.println("Print Title of OCA Home Page: "+Title);
			  
			  //Now we will validate if Title has "Home" and "Noggin OCA" in it
			  Assert.assertTrue(Title.contains("Home"));
			  Thread.sleep(3000);
			  //Before going to click Menu links, selenimum will wait 10 sec(max) to see Setting Menu is clickable or not
			  WebDriverWait waitforSettingsMenueToBeClickable = new WebDriverWait(driver,10);
			  waitforSettingsMenueToBeClickable.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='header-nav']//*[text()='Settings']")));
	  
	  //2.Navigate to Settings->OCA Designer->Incidents->Event types
	  
			  //First find Settings Menu and mouse over on Settings Menu
			  WebElement SettingMenu=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='Settings']"));
			  
			  //Invoke Selenium Action class to perform Mouse over in Settings Menu
			  
			  Actions action=new Actions(driver);
			  
			  //moveToElement will move the mouse to the middle of Settings Menu, however it does NOT click Settings Menu
			  
			  action.moveToElement(SettingMenu).perform();
			  Thread.sleep(5000);
			  //Now wait and find OCA designer under Setting Menu
			 // WebDriverWait waitforOCAdesignerMenuToBeClickable = new WebDriverWait(driver,10);
			  //waitforOCAdesignerMenuToBeClickable.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='header-nav']//*[text()='OCA designer']")));
	  		  WebElement OCAdesignerMenu=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='OCA designer']"));
			  
			  //moveToElement will move the mouse to the middle of OCA designer Menu, however it does NOT click Settings Menu
			  
			  action.moveToElement(OCAdesignerMenu).perform();
			  
			 //Now find Incidents under OCA designer Sub Menu
			  WebDriverWait waitforIncidentsMenuToBeClickable = new WebDriverWait(driver,10);
			  waitforIncidentsMenuToBeClickable.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='header-nav']//*[text()='OCA designer']/../..//*[text()='Incidents']")));
			  WebElement IncidentsMenu=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='OCA designer']/../..//*[text()='Incidents']"));//need to update
			  
		     //moveToElement will move the mouse to the middle of Incident Menu, however it does NOT click Settings Menu
			  
			  action.moveToElement(IncidentsMenu).perform();
			  
			 //Now find Event type under Incident Sub Menu
			  WebDriverWait waitforEventTypeMenuToBeClickable = new WebDriverWait(driver,10);
			  waitforEventTypeMenuToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='header-nav']//*[text()='Event types']")));
			  WebElement EventTypeMenu=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='Event types']"));
			  
			  //Now click on EventType Menu
			  EventTypeMenu.click();
			  //Thread.sleep(5000);
	  
			  //Now we will wait and validate if Title has "Event type designer" in it
			  WebDriverWait waitforEventTypeDesignerPage = new WebDriverWait(driver,10);
			  waitforEventTypeDesignerPage.until(ExpectedConditions.titleContains("Event type designer"));
			  Title=driver.getTitle();
			  System.out.println("Print Title of Event type designer page: "+Title);
			  Assert.assertTrue(Title.contains("Event type designer"));
	 //3. Create an Event type with some random name and field (what are the basic)
			  //Find New Event Type Menu
			  WebDriverWait waitforNewEventTypeMenuToBeClickable = new WebDriverWait(driver,10);
			  waitforNewEventTypeMenuToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='button-link' and text()='New']")));
			  WebElement NewEventTypeMenu=driver.findElement(By.xpath("//*[@class='button-link' and text()='New']"));
			  //Click New Event Type Menu
			  NewEventTypeMenu.click();
			  Thread.sleep(5000);
			  //Now New Event type window pops up. We need to switch webdriver to pop up window and work on the pop up window
			  String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
				  //System.out.println(parentWindowHandler);
				  String subWindowHandler = null;
	
				  Set<String> handles = driver.getWindowHandles(); // get all window handles
				  Iterator<String> iterator = handles.iterator();
				  while (iterator.hasNext()){
				  		subWindowHandler = iterator.next();
				      //System.out.println(subWindowHandler);
				  		driver.switchTo().window(subWindowHandler);//switch to popup window
				  		}
			  //Validate title of New event type
			  WebDriverWait waitforNewEventTypePage = new WebDriverWait(driver,10);
			  waitforNewEventTypePage.until(ExpectedConditions.titleContains("New event type"));
			  Title=driver.getTitle();
			  System.out.println("Print Title of New event type(pop-up): "+Title);
			  Assert.assertTrue(Title.contains("New event type"));
			  
			  //Creating a new Event type(Bare Minimum)
			  //Find Name Text Box and enter an unique text (Random)
			  WebDriverWait waitforEventTypeNameTextBoxToBeClickable = new WebDriverWait(driver,10);
			  waitforEventTypeNameTextBoxToBeClickable.until(ExpectedConditions.presenceOfElementLocated(By.id("wgt-Name")));
			  WebElement EventTypeNameTextBox=driver.findElement(By.id("wgt-Name"));
			  //Find Save button
			  WebDriverWait waitforSaveButtonClickable = new WebDriverWait(driver,10);
			  waitforSaveButtonClickable.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[text()='Save']")));
			  WebElement SaveButton=driver.findElement(By.xpath(".//*[text()='Save']"));
			  
			  //Define a ramdon name for EventType
					  char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
					  StringBuilder sb = new StringBuilder();
					  Random random = new Random();
					  for (int i = 0; i < 20; i++) {
					      char c = chars[random.nextInt(chars.length)];
					      sb.append(c);
					  }
					  String EventTypeName = "Automation "+sb.toString();
					  System.out.println("Event Type Name: "+EventTypeName);
				//Write New Event type name to Name Text Box
					  EventTypeNameTextBox.sendKeys(EventTypeName);
				//Add a Single Line Text box context menu (right click to add a field to a event)
					  WebDriverWait waitforCommencedLabelToBeClickable = new WebDriverWait(driver,10);
					  waitforCommencedLabelToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='wgt-Fields']//label[text()='Commenced']")));
					  WebElement CommencedLabel=driver.findElement(By.xpath("//*[@id='wgt-Fields']//label[text()='Commenced']"));
					  Actions action1 = new Actions(driver).contextClick(CommencedLabel);
					  action1.build().perform();
						 //Thread.sleep(5000);
						 WebDriverWait waitforAddNewFieldToBeVisible = new WebDriverWait(driver,10);
						 waitforAddNewFieldToBeVisible.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//div[text()='Add new field']"))));
					     WebElement AddNewField=driver.findElement(By.xpath("//div[text()='Add new field']"));
						 Actions action2 = new Actions(driver).moveToElement(AddNewField);
						 action2.build().perform();
						 //Thread.sleep(5000);
						 WebDriverWait waitforSingleLineTextBoxToBeClickable = new WebDriverWait(driver,10);
						 waitforSingleLineTextBoxToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='context-menu-item' and text()='Single-line text']")));
						 WebElement SingleLineTextBox=driver.findElement(By.xpath("//div[@class='context-menu-item' and text()='Single-line text']"));
						 Actions action3 = new Actions(driver).click(SingleLineTextBox);
						 action3.build().perform();
						 //Thread.sleep(5000);
				//Rename newly added field
						 	 WebDriverWait waitforNewSingleLineTextBoxLabelToBeClickable = new WebDriverWait(driver,10);
						 	 waitforNewSingleLineTextBoxLabelToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='New single-line text']")));
							 WebElement NewSingleLineTextBoxLabel=driver.findElement(By.xpath("//label[text()='New single-line text']"));
							 //Right click on label
							 Actions action4 = new Actions(driver).contextClick(NewSingleLineTextBoxLabel);
							 action4.build().perform();
						 //Thread.sleep(5000);
						 //click on Rename field
							 WebDriverWait waitforRenameFieldToBeClickable = new WebDriverWait(driver,10);
							 waitforRenameFieldToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='New single-line text']")));
							 WebElement RenameField=driver.findElement(By.xpath("//div[text()='Rename field']"));
							 Actions action5 = new Actions(driver).click(RenameField);
							 action5.build().perform();	
						 Thread.sleep(5000);
						 //Clear the Label of New Line Single Text
						 	 WebDriverWait waitforNewSingleLineTextBoxLabelInputToBeVisible = new WebDriverWait(driver,10);
						 	 waitforNewSingleLineTextBoxLabelInputToBeVisible.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//label[text()='New single-line text']/following-sibling::div/following-sibling::span/input"))));
							 WebElement NewSingleLineTextBoxLabelInput=driver.findElement(By.xpath("//label[text()='New single-line text']/following-sibling::div/following-sibling::span/input"));
							 NewSingleLineTextBoxLabelInput.clear();
							 NewSingleLineTextBoxLabelInput.sendKeys("AutomationSingleLineTextBox");
							 //After Remaining the label we need click to out side to change the focus to window
							 //So, we click on Event type Name label at Top of the page
							 WebElement EventTypeNameLabel=driver.findElement(By.xpath("//*[@class='popup-header-text' and text()='New event type']"));
							 EventTypeNameLabel.click();
							 String AutomantionSingleLineTextBoxLabelText="AutomationSingleLineTextBox";
						//Now we will select and Manipulate the "Fields Properties"
							 //Right click on Field Input Box 
							 //Wait and Find out WebElement for AutomationSingleLineTextBox
							 WebDriverWait WaitForNewCreatedSingleLineTextLabelTobeClickable = new WebDriverWait(driver,10);
							 WaitForNewCreatedSingleLineTextLabelTobeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='"+AutomantionSingleLineTextBoxLabelText+"']")));
							 WebElement NewCreatedSingleLineTextLabel=driver.findElement(By.xpath("//label[text()='"+AutomantionSingleLineTextBoxLabelText+"']"));
							 Actions NewCreatedSingleLineTextLabelRightClickAction=new Actions(driver).contextClick(NewCreatedSingleLineTextLabel);
							 NewCreatedSingleLineTextLabelRightClickAction.build().perform();
							 //Context Menu to access "Field properties" displays
							 //Now wait for wait for "Properties" button and click
								 WebDriverWait waitforPropertiesContexMenuToBeClickable = new WebDriverWait(driver,10);
								 waitforPropertiesContexMenuToBeClickable.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='context-menu-item' and text()='Properties']")));
								 WebElement PropertiesContexMenu=driver.findElement(By.xpath("//*[@class='context-menu-item' and text()='Properties']"));
								 Actions PropertiesContexMenuClick = new Actions(driver).click(PropertiesContexMenu);
								 PropertiesContexMenuClick.build().perform();
								 Thread.sleep(3000);
								 	//Now iframe for "Field Properties" should popup
								 	//Select the iframe for Single Line Text box
									/*List<WebElement> iframeElements = driver.findElements(By.tagName("iframe"));
									System.out.println("The total number of iframes are " + iframeElements.size());*/
								 
									//we are switching to iframe index 1 (0 means first iframe, 1 means scenod iframe) not using name not sure if iframe id will be unique of not
								        driver.switchTo().frame(1);//Switching to Field Properties Iframe
								    	//Find and Click on Deafult Value tab of iframe
								        WebElement DefaultValueTab=driver.findElement(By.xpath("//*[text()='Default value']"));
								        DefaultValueTab.click();
								        
								        //More iFrames to access Default value text box (??)							
										//Switch to innerIframe
										    driver.switchTo().frame(0);
								          //Finding and Entering some text in default value text box
								            WebElement DefaultValueTextBox=driver.findElement(By.xpath("//body"));
								            DefaultValueTextBox.clear();
								            DefaultValueTextBox.sendKeys("Who designed this form??");
								        //switch back to Main window (Event Type Designer)
								        driver.switchTo().defaultContent();
								        //Switch to "Field Properties" iframe again
											driver.switchTo().frame(1);
											//Click Instructions tab
											WebElement InstructionsTabFieldsPropertiesIframe=driver.findElement(By.xpath("//div[text()='Instructions']"));
											InstructionsTabFieldsPropertiesIframe.click();
											//Wait, find and write some in Instructions text area
											WebDriverWait waitforInstructionsTextAreaToBeVisible = new WebDriverWait(driver,10);
											waitforInstructionsTextAreaToBeVisible.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='wgt-Instructions']")));
											 WebElement InstructionsTextArea=driver.findElement(By.xpath("//*[@id='wgt-Instructions']"));
											 InstructionsTextArea.clear();
											 InstructionsTextArea.sendKeys("This is your automation Instructions");
											//Click ok
											WebElement OKButtoninFieldsPropertiesIframe=driver.findElement(By.xpath("//span[text()='Ok']"));
											OKButtoninFieldsPropertiesIframe.click();
				//switch back to Main window (Event Type Designer)
					 driver.switchTo().defaultContent();//
										
						//Thread.sleep(5000);
				//Click Save button and pause 5 secs and switch to Main OCA page
					  SaveButton.click();
					  Thread.sleep(5000);
					  driver.switchTo().window(parentWindowHandler);
				      System.out.println("Back to :" +driver.getTitle());
		       
			//Starting from OCA Main window
						//wait for 3 seconds to view the contex menu
						//Thread.sleep(3000);
						/*We will go Settings -> Security-> User Accounts 
						 * Double Click on User Account Type of the user --defined in Global section of the class
						 * Access to System user account properties
						 * Click on Events dashboards tab
						 * Find out Event type just created 
						 * Select a pre-define Dashboard --defined in Global section of the class
						 * Click OK
						 * Come back to main window
						 */
						//Find Settings Menu and mouse over
						WebDriverWait waitforSettingsMenuTopToBeVisible = new WebDriverWait(driver,10);
						waitforSettingsMenuTopToBeVisible.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//*[@id='header-nav']//*[text()='Settings']"))));
						WebElement SettingsMenuTop=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='Settings']"));
						Actions SettingsMenuMouseOverAction=new Actions(driver).moveToElement(SettingsMenuTop);
						SettingsMenuMouseOverAction.build().perform();;	
						Thread.sleep(3000);
							//Find Security Menu and mouse over
							WebDriverWait waitforSecurityMenuTopToBeVisible = new WebDriverWait(driver,10);
							waitforSecurityMenuTopToBeVisible.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//*[@id='header-nav']//*[text()='Security']"))));
							WebElement SecurityMenu=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='Security']"));
							Actions SecurityMenuMouseOverAction=new Actions(driver).moveToElement(SecurityMenu);
							SecurityMenuMouseOverAction.build().perform();;
							Thread.sleep(3000);
								//Find User account types Menu and click over
								WebDriverWait waitforUserAcctTypeMenuToBeVisible = new WebDriverWait(driver,10);
								waitforUserAcctTypeMenuToBeVisible.until(ExpectedConditions.elementToBeClickable((By.xpath("//*[@id='header-nav']//*[text()='User account types']"))));
								WebElement UserAcctTypeMenu=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='User account types']"));
								Actions UserAcctTypeMenuMouseOverAction=new Actions(driver).click(UserAcctTypeMenu);
								UserAcctTypeMenuMouseOverAction.build().perform();
								/*We will land User Account Type page
								 * We will verify that we are in User Account Type page
								 */
								WebDriverWait waitforUserAccountTypePage = new WebDriverWait(driver,10);
								waitforUserAccountTypePage.until(ExpectedConditions.titleContains("User account types"));
								Title=driver.getTitle();
								System.out.println("Print Title of User account types: "+Title);
								Assert.assertTrue(Title.contains("User account types"));
								//Thread.sleep(5000);
								/*Check pagination of page
								 * Selenium will search for User Account Type in page until found 
								 * to be developed
								 */
								//Find and double click on User Account Type
								WebElement UserAccTypeName=driver.findElement(By.xpath("//*[text()='"+userAccountTypeName+"']"));
								Actions UserAccTypeNameDblClick=new Actions(driver).doubleClick(UserAccTypeName);
								UserAccTypeNameDblClick.build().perform();
								
										/*
										 * System user account type properties will come as pop up window
										 * Selenium will switch focus on pop window, validate that page title
										 * Then click Event Dash boards tab
										 */
								//wait till two windows are not opened
								Thread.sleep(5000);
								
								String parentWindowHandle_UserAcctTypeWindow = driver.getWindowHandle(); // Store your parent window
								  //System.out.println(parentWindowHandler);
								  String subWindowHandler__SystemUserAccounTypePropertiesWindow = null;
					
								  Set<String> UserAccthandles = driver.getWindowHandles(); // get all window handles
								  Iterator<String> UserAcctiterator = UserAccthandles.iterator();
								  while (UserAcctiterator.hasNext()){
									  subWindowHandler__SystemUserAccounTypePropertiesWindow = UserAcctiterator.next();
								      //System.out.println(subWindowHandler);
								  		driver.switchTo().window(subWindowHandler__SystemUserAccounTypePropertiesWindow);//switch to popup window
								  		}
							  //Validate title of New event type
							  WebDriverWait waitforSystemUserAccounTypePropertiesPage = new WebDriverWait(driver,30);
							  waitforSystemUserAccounTypePropertiesPage.until(ExpectedConditions.titleContains("System user account type properties"));
							  Title=driver.getTitle();
							  System.out.println("Print Title of System user account type propertiese(pop-up): "+Title);
							  Assert.assertTrue(Title.contains("System user account type properties"));
							  //Click on Event Dashboard tab
							  WebElement EventDashBoardTab=driver.findElement(By.xpath("//*[text()='Event dashboards']"));
							  EventDashBoardTab.click();
							  Thread.sleep(3000);
							  		//Find the option list next label of the Event Type
							  		Select EventLabelDashBoardOptionList=new Select(driver.findElement(By.xpath("//*[text()='"+EventTypeName+"']/../following-sibling::td//select")));
							  		EventLabelDashBoardOptionList.selectByVisibleText(DashBoard);
							  		//Now Click OK
							  		WebElement OKButtonEventTabinSystemUserAccountPage=driver.findElement(By.xpath("//*[text()='Ok']"));
							  		OKButtonEventTabinSystemUserAccountPage.click();
							  Thread.sleep(3000);
							  //need switch to parent window (UserAcctTypeWindow) when we are done with System user account
							  driver.switchTo().window(parentWindowHandle_UserAcctTypeWindow);
							WebDriverWait waitforUserAccountTypePageBack = new WebDriverWait(driver,10);
							waitforUserAccountTypePageBack.until(ExpectedConditions.titleContains("User account types"));
							Title=driver.getTitle();
							System.out.println("Back to :Print Title of User account types: "+Title);
							Assert.assertTrue(Title.contains("User account types"));
							  				    
					  
			// 4. After, event type is created and Event Dash board is set up, navigate to Incident->Events

				//Find Incident Menu and mouse over
				WebDriverWait waitforIncidentsMenuTopToBeVisible = new WebDriverWait(driver,10);
				waitforIncidentsMenuTopToBeVisible.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//*[@id='header-nav']//*[text()='Incidents']"))));
				WebElement IncidentsMenuTop=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='Incidents']"));
				Actions incidentMenuMouseOverAction=new Actions(driver).moveToElement(IncidentsMenuTop);
				incidentMenuMouseOverAction.build().perform();;
				Thread.sleep(3000);
				//Click Events under Incidents sub menu
				//WebDriverWait waitforEventsSubMenuToBeVisible = new WebDriverWait(driver,10);
				//waitforEventsSubMenuToBeVisible.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//*[@id='header-nav']//*[text()='Events']"))));
				WebElement EventsMenu=driver.findElement(By.xpath("//*[@id='header-nav']//*[text()='Events']"));
				EventsMenu.click();
				//Verify that we land on Event Index page
				//Thread.sleep(5000);
				WebDriverWait waitforEventsIndexPage = new WebDriverWait(driver,10);
				waitforEventsIndexPage.until(ExpectedConditions.titleContains("Events"));
				Title=driver.getTitle();
				System.out.println("Print Title of Events Index Page: "+Title);
				Assert.assertTrue(Title.contains("Events"));
				
				//Now find and click New button
				//WebDriverWait waitforNewEventLinkToBeVisible = new WebDriverWait(driver,10);
				//waitforNewEventLinkToBeVisible.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//*[@id='wgt-summary-31']//*[text()='New']"))));
				//WebElement NewEventLink=driver.findElement(By.xpath("//*[@id='wgt-summary-31']//*[text()='New']"));
				FindOCAElement TestOCA=new FindOCAElement(driver);
				WebElement NewEventLink=TestOCA.FindandReturnWebElement("screentabsbutton|@|wgt-Tabs=New");
				NewEventLink.click();

				
				
				//Now, we will find and click Event type just created
				String NewEventTypeXpath="//*[@class='context-menu-item' and text()='"+EventTypeName+"']";
				WebDriverWait waitforNewEnventTypeLinkVisible = new WebDriverWait(driver,10);
				waitforNewEnventTypeLinkVisible.until(ExpectedConditions.presenceOfElementLocated((By.xpath(NewEventTypeXpath))));
				WebElement NewEnventTypeLink=driver.findElement(By.xpath(NewEventTypeXpath));
					//Now do a mouseOver and click on EventType
					Actions EnventTypeLinkMouseOverAndClickAction=new Actions(driver).moveToElement(NewEnventTypeLink).click(NewEnventTypeLink);
					EnventTypeLinkMouseOverAndClickAction.perform();
				Thread.sleep(3000);
				//Event Creation window pops up. We need to switch webdriver to pop up window and work on the pop up window
				String parentWindowHandlerEventIndexPage = driver.getWindowHandle(); // Store your parent window
				  //System.out.println(parentWindowHandler);
				  String subWindowHandlerEventCreatePage = null;

				  Set<String> handles1 = driver.getWindowHandles(); // get all window handles
				  Iterator<String> iterator1 = handles1.iterator();
				  while (iterator1.hasNext()){
					  subWindowHandlerEventCreatePage = iterator1.next();
				      //System.out.println(subWindowHandler);
				  		driver.switchTo().window(subWindowHandlerEventCreatePage);//switch to popup window
				  		}
				  //Validate title of New event type
				  //Thread.sleep(3000);
				  WebDriverWait waitforEventCreationPage = new WebDriverWait(driver,10);
				  waitforEventCreationPage.until(ExpectedConditions.titleContains(EventTypeName));
				  Title=driver.getTitle();
				  System.out.println("Print Title of Event:"+EventTypeName+ "Creation page (pop-up): "+Title);
				  Assert.assertTrue(Title.contains(EventTypeName));
				  //Now Fill Up details in Event create page
				  double randomNumber=Math.random();
				  String EventName="Automation Event Name"+randomNumber;
				  
				  //Find the Name Input box
				  WebElement NameInputBox=driver.findElement(By.xpath("//*[text()='Name']/../following-sibling::td//input[@type='text']"));
				  NameInputBox.sendKeys(EventName);
				  //Fill Other fileds
				  //Find OK button
				  WebElement OKButton=driver.findElement(By.xpath("//*[@class='button capped-inline']//*[text()='Ok']"));
				  OKButton.click();
				  
				  //switch to parent window-Event Index page
				  //Thread.sleep(2000);
						driver.switchTo().window(parentWindowHandlerEventIndexPage);
						WebDriverWait waitforEventIndexPage = new WebDriverWait(driver,10);
						waitforEventIndexPage.until(ExpectedConditions.titleContains("Events"));
						Title=driver.getTitle();
						System.out.println("Back to: Print Title of Event Index page:"+Title);
						Assert.assertTrue(Title.contains("Events"));
						System.out.println(driver.getTitle());
						
				 /*Now Selenium will verify that newly created Event shows in First page of Event Index page
				  * Double click the events
				  * OPen up pre-configured Events Dash board
				  */
					WebDriverWait waitforNewEnventNameinIdexPageToBeVisible = new WebDriverWait(driver,10);
					waitforNewEnventNameinIdexPageToBeVisible.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//*[text()='"+EventName+"']"))));
					WebElement EventNameinEventIndexPage=driver.findElement(By.xpath("//*[text()='"+EventName+"']"));
					Actions EventNameinEventIndexPageDblClickAction=new Actions(driver).doubleClick(EventNameinEventIndexPage);
					EventNameinEventIndexPageDblClickAction.build().perform();
				//Selenium should land on Event dash board
				//Verify Title of the page
				//And Event in the header
				//Event Details widgets (for current event)
					
					WebDriverWait waitforEventDashBoardPage = new WebDriverWait(driver,15);
					waitforEventDashBoardPage.until(ExpectedConditions.titleContains(EventName));
					Title=driver.getTitle();
					System.out.println(" Print Title of Event Dash board:"+Title);
					Assert.assertTrue(Title.contains(EventName));
					//System.out.println(driver.getTitle());
					//Asserting if Dash boar header contains event name
					WebElement DashBoardHeader=driver.findElement(By.xpath("//h1[text()='"+EventName+"']"));
					Assert.assertTrue(DashBoardHeader.getText().contains(EventName));
					//Asserting Event Name in widget box
					WebElement EventNameinWidgetBox=driver.findElement(By.xpath("//*[@class='item ocaemergencyemeventdetails oca-dashtheme-comm']//*[text()='Name']/../../following-sibling::tr//*[text()='"+EventName+"']"));
					Assert.assertTrue(EventNameinWidgetBox.getText().contains(EventName));
					//End of test case				
				
				  Thread.sleep(5000);
				 
				
	}
  
  @AfterClass
  public void close(){
	  driver.quit();
  }

  
}