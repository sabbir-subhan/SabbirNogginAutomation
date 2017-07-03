package com.noggin.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.noggin.OCA1AutomationSupport.FindOCAElement;
import com.noggin.OCA1AutomationSupport.HighlightElement;

public class OCA1LoginPage {
	WebDriver driver;
	HighlightElement highlight=new HighlightElement();
	
	By DirectLoginButton=By.xpath("//*[@id='wgt-8']");
	By UserNameTextBox=By.id("wgt-Username");
	By PasswordTextBox=By.id("wgt-Password");
	By SigninButton=By.id("wgt-Sign In");
	
	public OCA1LoginPage(WebDriver driver){
		this.driver=driver;
	}
	
	public void clickDirectLoginButton(){
		highlight.highlightElement(driver, driver.findElement(DirectLoginButton));
		driver.findElement(DirectLoginButton).click();
		}
	
	public void enterLoginDetail(String UserName,String Password){
		highlight.highlightElement(driver, driver.findElement(UserNameTextBox));
		driver.findElement(UserNameTextBox).sendKeys(UserName);
		highlight.highlightElement(driver, driver.findElement(PasswordTextBox));
		driver.findElement(PasswordTextBox).sendKeys(Password);
	}

	public String getOCA1LoginPageTitle() {
		return driver.getTitle();
	}

	public void clickSigninButton() {
		highlight.highlightElement(driver, driver.findElement(SigninButton));
		driver.findElement(SigninButton).click();
		
	}
	
	public void scrollToViewLoginButton(){
		FindOCAElement scrollLoginButton=new FindOCAElement(driver);
		scrollLoginButton.ScrollElementIntoView(driver.findElement(DirectLoginButton));
		
	}
}
