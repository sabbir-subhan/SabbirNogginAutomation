package com.noggin.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.noggin.OCA1AutomationSupport.HighlightElement;

public class OCA1HomePage {
	
	WebDriver driver;
	HighlightElement highlight=new HighlightElement();
	
	public OCA1HomePage(WebDriver driver){
		this.driver=driver;
	}
	//Elements in Home Page
	By BusinessAsUsual=By.xpath("//*[@id='wgt-11']/span[@class='oca-coloureddropbox-value']");
	By PersonIcon=By.xpath("//*[@id='wgt-3']/div[@class='dropbutton-icon']");
	By ClockIcon=By.xpath("//*[@id='wgt-7']/div[@class='dropbutton-icon']");
	By BellIcon=By.xpath("//*[@id='wgt-5']/div[@class='dropbutton-icon']");
	By PencilIcon=By.xpath("//*[@id='wgt-2']/div[@class='dropbutton-icon']");
	//Menu Elements
	By HomeMenu=By.xpath("//*[@id='header-nav']//*[text()='Home']");
	By ContactsMenu=By.xpath("//*[@id='header-nav']//*[text()='Contacts']");
	By CommunicateMenu=By.xpath("//*[@id='header-nav']//*[text()='Communicate']");
	
	public void clickBusinessAsUsualOnTop(){
		highlight.highlightElement(driver, driver.findElement(BusinessAsUsual));
		driver.findElement(BusinessAsUsual).click();
	}
	
	public void clickPersonIconOnTop(){
		highlight.highlightElement(driver, driver.findElement(PersonIcon));
		driver.findElement(PersonIcon).click();
	}
	
	public void clickClockIconOnTop(){
		highlight.highlightElement(driver, driver.findElement(ClockIcon));
		driver.findElement(ClockIcon).click();
	}
	
	public void clickBellIconOnTop(){
		highlight.highlightElement(driver, driver.findElement(BellIcon));
		driver.findElement(BellIcon).click();
	}
	
	public void clickPencilIconOnTop(){
		highlight.highlightElement(driver, driver.findElement(PencilIcon));
		driver.findElement(PencilIcon).click();
	}


	public String getOCA1HomePageTitle() {
		return driver.getTitle();
		
	}
	
	private void MouseOverMenu(WebElement element){
		
		
		// Deafult pause when Top Menu Mouseover in 1 sec
		long pause=1000;//in Mili sec
		//Invoke Selenium Action class to perform Mouse over in Settings Menu
	  
		Actions action=new Actions(driver);
	  
		//moveToElement will move the mouse to the middle of Settings Menu, however it does NOT click Settings Menu
	  
		action.moveToElement(element).perform();
		
		 try {
				Thread.sleep(pause);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

}

	public void MouseOverToHomeMenu() {
		
		highlight.highlightElement(driver, driver.findElement(HomeMenu));
		MouseOverMenu(driver.findElement(HomeMenu));
	}

	public void MouseOverToContactMenu() {
		highlight.highlightElement(driver, driver.findElement(ContactsMenu));
		MouseOverMenu(driver.findElement(ContactsMenu));
		
	}

	public void MouseOverToCommunicateMenu() {
		highlight.highlightElement(driver, driver.findElement(CommunicateMenu));
		MouseOverMenu(driver.findElement(CommunicateMenu));
		
	}

}
