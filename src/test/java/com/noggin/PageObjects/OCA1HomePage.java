package com.noggin.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.noggin.OCA1Automation.HighlightElement;

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

}
