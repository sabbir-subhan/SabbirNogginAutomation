package com.noggin.OCA1Atumation;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class POCIDEToWebDriver {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	//setting up firefox driver
	 System.setProperty("webdriver.firefox.marionette","geckodriver.exe");
    driver = new FirefoxDriver();
    baseUrl = "https://im1.oca-test-beta-el7sec.lan.noggin.com.au";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testPOCIDEToWebDriver() throws Exception {
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
	 driver.get("http://im1.oca-test-beta-el7sec.lan.noggin.com.au/");
	driver.findElement(By.id("wgt-7")).click();
    driver.findElement(By.id("wgt-Username")).clear();
    driver.findElement(By.id("wgt-Username")).sendKeys("sabbir");
    driver.findElement(By.id("wgt-Password")).clear();
    driver.findElement(By.id("wgt-Password")).sendKeys("1234test");
    driver.findElement(By.xpath("//div[@id='wgt-Sign In']/span")).click();
    
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}