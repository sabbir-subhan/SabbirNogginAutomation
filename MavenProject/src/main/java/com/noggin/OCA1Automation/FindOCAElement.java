package com.noggin.OCA1Automation;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
//import org.testng.Reporter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindOCAElement {
	
	WebDriver driver;
	
	public FindOCAElement(WebDriver driver){
		this.driver=driver;
		//test
	}

	
	public WebElement FindandReturnWebElement(String IDEElementString){
			
		//Done - target needs to be set to the "body" element in the root window
			WebElement target = driver.findElement(By.tagName("body"));
			System.out.println(target.getAttribute("onload"));
			System.out.println(driver.getCurrentUrl());
			System.out.println(driver.getTitle());
		/*Code to convert IDE string to WebElement
		 * Use Brett Js script as guide
		 */
		
			String[] parts=IDEElementString.split(Pattern.quote("!@!"));
		
			for (int i=0;i<parts.length;i++){
			
				String[] targetrule = parts[i].split(Pattern.quote("|@|"));
			
				String rule = targetrule[0];
				String search = targetrule[1];
			
				System.out.println(rule);
				System.out.println(search);
			
				switch(rule){
					case "dashid":
					{
						//target = target.findElement(By.id(search));
					}
					
					case "button":
					{
						target = this.locateButton(target,search);
						break;							
					}
					
					case "modal":
					{
						//TODO - use 'driver' directly to get the iframe with name _ngbasis_modalwin_ and set target to the "body" element inside that iframe
						//First find the element with \
						System.out.println("Lets find iframe!!");
						target=this.locateIframeBodayTag(target);
						break;
					}
					
					case "topmenu":
					{
						//long pause=3000;//Default Pause when mouseover is set to 3000 mil sec aka 3 sec
						target=this.locateTopMenu(target,search);
						break;
					}
					
					case "submenu":
					{
				
						//Find all elements when submenu is visible
						target=this.locateSubMenu(target,search);
						
						break;
					}
					
					case "screentabsbutton":
					{
						System.out.println("We are in screetabsbutton");
						target = this.locateScreenTabsButton(target,search);
						break;
					}
					
					case "contextmenu":
					{
						System.out.println("We are in CONTEXTMENU");
						target = this.locateContextMenuItem(target,search);
						break;
					}
					case "tree":
					case "treeexp":
					{
						System.out.println("We are in tree or treeExpand");
						target=this.locateNodeofAExapandingTree(target,search);
						break;
					}
					
					case "treeexpEvent":
					{
						System.out.println("We are in Event Tree Expan");
						target=this.locateNodeofExpandingEventTree(target,search);
					}
										
				}
				
				if (target == null) {
					return null;
				}
			
			
		}
		
		return target;
	
	}
	
private WebElement locateNodeofExpandingEventTree(WebElement target, String search) {
	
		String[] parts=search.split(Pattern.quote("&amp;@&amp;"));
		System.out.println(parts[0]);
		System.out.println(parts[1]);
		
		String wgtId=parts[0];//first part will id
		String TreeTopNode=parts[parts.length-1];
		
		//Find out the plus sign of next Root Event
		String xpathExpressionPlusSignforRootLevelEventTRElement=("//*[@id='"+wgtId+"']//*[text()='"+TreeTopNode+"']/..");
		
		System.out.println(xpathExpressionPlusSignforRootLevelEventTRElement);	
		WebElement TopTreeElementTR=target.findElement(By.xpath(xpathExpressionPlusSignforRootLevelEventTRElement));
		
		System.out.println(TopTreeElementTR.getAttribute("class"));
		if(TopTreeElementTR.getAttribute("class").contains("collapsed")){
			System.out.println(xpathExpressionPlusSignforRootLevelEventTRElement+ "is NO exapanded, ready expand");
			WebElement TopTreeElementTRPlusSign=target.findElement(By.xpath(xpathExpressionPlusSignforRootLevelEventTRElement+"/td[1]/div/span"));
			return TopTreeElementTRPlusSign;
		}
		else{
			if(TopTreeElementTR.getAttribute("class").contains("expanded")){
			System.out.println(xpathExpressionPlusSignforRootLevelEventTRElement+ "is allready exapanded");
			WebElement TopTreeElementTRPlusSign=target.findElement(By.xpath(xpathExpressionPlusSignforRootLevelEventTRElement+"/td[1]/div/span"));
			//collapse top level tree
			this.collapseTree(TopTreeElementTRPlusSign);
			System.out.println(TopTreeElementTRPlusSign+ "is now collapsed");
			return TopTreeElementTRPlusSign;
			}
			else{
				
				System.out.println(xpathExpressionPlusSignforRootLevelEventTRElement+ "does not have child element. So let selenium click");
				//Selenium need to click Label
				String XpathExpressionLabel=("//*[@id='"+wgtId+"']//*[text()='"+TreeTopNode+"']");
				WebElement TopTreeLabel=target.findElement(By.xpath(XpathExpressionLabel));
				return TopTreeLabel;
			}
		}
		
	
	}


private WebElement locateNodeofAExapandingTree(WebElement target, String search) {
			//Auto-generated method stub
			String[] parts=search.split(Pattern.quote("&amp;@&amp;"));
			System.out.println(parts[0]);
			System.out.println(parts[1]);
			
			//First part is the wgt id, the rest is the hierarchy of labels, note this means that duplicate names are not supported, at least
			//not selecting any but the first
			/*
			if(parts.length!=2){
				return null;
			}
			*/
			
			String wgtId=parts[0];//first part will id
			String TreeTopNode=parts[parts.length-1];
			
			
						//Now we deriver xpath expression for div class of(3 level up) TreeNode and check value of calss attribute of this element
					String XpathExpressionTopTreeElementDiv=("//*[@id='"+wgtId+"']/ul/li//*[text()='"+TreeTopNode+"']/../../..");
					System.out.println(XpathExpressionTopTreeElementDiv);	
					WebElement TopTreeElementDiv=target.findElement(By.xpath(XpathExpressionTopTreeElementDiv));
					System.out.println(TopTreeElementDiv.getAttribute("class"));
						if(TopTreeElementDiv.getAttribute("class").contains("collapsed")){
							System.out.println(XpathExpressionTopTreeElementDiv+ "is NO exapanded, ready expand");
							return TopTreeElementDiv;
						}
						else{
							if(TopTreeElementDiv.getAttribute("class").contains("expanded")){
								System.out.println(XpathExpressionTopTreeElementDiv+ "is allready exapanded");
								//collapse top level tree
								this.collapseTree(TopTreeElementDiv);
								System.out.println(XpathExpressionTopTreeElementDiv+ "is now collapsed");
								return TopTreeElementDiv;
							}
							else{
								System.out.println(XpathExpressionTopTreeElementDiv+ "does not have child element. So let selenium click");
								//Selenium need to click Label
								String XpathExpressionLabel=("//*[@id='"+wgtId+"']/ul/li//*[text()='"+TreeTopNode+"']");
								WebElement TopTreeLabel=target.findElement(By.xpath(XpathExpressionLabel));
								return TopTreeLabel;
				}
				
			}
		
	}


private void collapseTree(WebElement topTreeElementDiv) {
			WebElement TopTreeElementDiv1=topTreeElementDiv;
			TopTreeElementDiv1.click();
			// Deafult pause when Top Menu after Collapsing in 3 sec
			long pause=3000;//in Mili sec
			
			 try {
					Thread.sleep(pause);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
}


private WebElement locateIframeBodayTag(WebElement target) {
		//  Auto-generated method stub
		this.switchToiframe("_ngbasis_modalwin_");//iframe name is static accross OCA as per Brett
		WebElement IframeBodyElemet = driver.findElement(By.tagName("body"));
		System.out.println(IframeBodyElemet.getAttribute("onunload"));
		target=IframeBodyElemet;
		return target;
		
	//return null;
		
	}


private WebElement locateContextMenuItem(WebElement target, String search) {
		
		//String[] parts=search.split()
		List <WebElement> AllContextMenuItems=target.findElements(By.cssSelector("div.context-menu-item"));
		if(AllContextMenuItems.size()>0){
			for(int i=0;i<AllContextMenuItems.size();i++){
				if(AllContextMenuItems.get(i).getText().contentEquals(search)){
					target=AllContextMenuItems.get(i);
					System.out.println(target.getText());
					return target;
				}
			}
		}
		else{
			//if there is only one  in OCA
			target=AllContextMenuItems.get(0);
			System.out.println(target.getText());
			return target;
		}
		return null;
	}


private WebElement locateScreenTabsButton(WebElement target, String search) {
		//Auto-generated method stub
		String wgtID=search.substring(0, search.indexOf("="));
		String buttonLabel=search.substring(search.indexOf("=")+1);
		System.out.println(wgtID);
		System.out.println(buttonLabel);
		WebElement ScreenTabsButton=target.findElement(By.id(wgtID));
		if(ScreenTabsButton!=null){
			List <WebElement> Buttons=ScreenTabsButton.findElements(By.cssSelector("div.tools > div > a"));
			ScreenTabsButton=null;
			for(int i=0;i<Buttons.size();i++){
				if(Buttons.get(i).getText().contentEquals(buttonLabel)){
					ScreenTabsButton=Buttons.get(i);
					System.out.println(ScreenTabsButton.getText());
					return ScreenTabsButton;
				}
			}
		}
		return null;
	}


private WebElement locateSubMenu(WebElement target,String search) {
		
		String[] IdSplited=search.split(Pattern.quote(":"));
		String depth=IdSplited[0];
		String label=IdSplited[1];
		
		
		//Find all elements using Xpath expression
		switch(depth){
				case "0":
				{
					String XpathExpression="//div[contains(@class,'layer0')]//*[text()='"+label+"']";
					WebElement SubMenu=target.findElement(By.xpath(XpathExpression));
					System.out.println(SubMenu.getText());
					return SubMenu;
				}
				case "1":
				{
					String XpathExpression="//div[contains(@class,'layer0 focused expanded')]/../div[@class='children']//div[contains(@class,'layer1') and text()='"+label+"']";
					WebElement SubMenu=driver.findElement(By.xpath(XpathExpression));
					System.out.println(SubMenu.getText());
					return SubMenu;
				}
				case "2":
				{
					String XpathExpression="//div[contains(@class,'layer0 expanded')]/../div[@class='children']//div[contains(@class,'layer1') and text()='"+label+"']";
					WebElement SubMenu=driver.findElement(By.xpath(XpathExpression));
					System.out.println(SubMenu.getText());
					return SubMenu;
				}

		}
		
		return null;
	}


private List<WebElement> subMenuChildren(WebElement webElement) {
	// TODO Auto-generated method stub
	WebElement Children=null;
	//using xpath go to parent node
	List <WebElement> CN=webElement.findElements(By.xpath(".."));
	return null;
}


private WebElement locateButton(WebElement el,String label) {
		
		List <WebElement> ElementWithDivButtonCssSelector=el.findElements(By.cssSelector("div.button"));
		
		System.out.println(ElementWithDivButtonCssSelector.size());
		
		for (int j=0;j<ElementWithDivButtonCssSelector.size();j++){
			System.out.println(ElementWithDivButtonCssSelector.get(j).getAttribute("class"));
			
			if(this.hasClass(ElementWithDivButtonCssSelector.get(j),"capped-inline"))
			{
				WebElement ElementwithClasscontainsCappedInline=ElementWithDivButtonCssSelector.get(j).findElement(By.tagName("span"));
				
				System.out.println(ElementwithClasscontainsCappedInline.getText());
				if(ElementwithClasscontainsCappedInline.getText().contentEquals(label)){
								
					return ElementwithClasscontainsCappedInline;
									
				}
				
			}
		}
		
		return null;	
		
	}
	
	
     private WebElement locateTopMenu(WebElement target, String search) {
		//  Auto-generated method stub
    	//Need add a pause default pause as well
    	 //Locate to menu
    	 //
    	 List <WebElement> ElementWithdivlayer1CssSelector=target.findElements(By.cssSelector("#header-nav > div > div > div.layer0 > div.layer1"));
 		
 		 System.out.println(ElementWithdivlayer1CssSelector.size());
 		 
 		 for (int i=0;i<ElementWithdivlayer1CssSelector.size();i++){
 			System.out.println(ElementWithdivlayer1CssSelector.get(i).getText());
 			 if(ElementWithdivlayer1CssSelector.get(i).getText().contentEquals(search)){
 				 WebElement TopMenuFound=ElementWithdivlayer1CssSelector.get(i);
 				 /*
 				 //we are also calling a pause here when top menu found
 				 System.out.println("We are pausing for :" + pause);
 				 try {
					Thread.sleep(pause);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}**/
 				 return TopMenuFound; 
 			 }
 		 }
 		
		return null;
	}


	public void FindWebElementAndAction(String IDEString,String Action) {
		
		WebElement elementFound = driver.findElement(By.id(IDEString));
		//if intraction is do something
		if(Action=="Click")
		{
		elementFound.click();
		}
		
		
		System.out.println(IDEString); 
		System.out.println(Action); 
	
	}
	
	public WebElement FindWebElementUsingID(String IDEString) {
		
		WebElement elementFound = driver.findElement(By.id(IDEString));
		//if intraction is do something

		System.out.println(IDEString); 
		return elementFound;
	
	}
	
	private boolean hasClass(WebElement el, String search) {
		
			//use the whitespace regex
		
			String[] classNameSplited = el.getAttribute("class").split("\\s+");
				
				for (int i = 0; i < classNameSplited.length; i++) {
					System.out.println(classNameSplited[i]);
					if (classNameSplited[i].contentEquals(search)) {
						return true;
					}
				}
		
		return false;
	
		}
	
	
	public void isElementPresent(WebElement element) {
		if(element.isDisplayed()){			
			//Reporter.log("Element: " + element.getText() + ", is available on a page - ");
			System.out.println("Element: " + element.getText() + ", is available on a page - ");
		}
		else{
            // Using the TestNG API for logging
          // Reporter.log("Element: " + element.getLocation().toString() + ", is not available on a page - ");
            System.out.println("Element: " + element.getText() + ", is not available on a page - ");
                           
    		}
		}
	
	public void MouseOverMenu(WebElement element){
		
		
			// Deafult pause when Top Menu Mouseover in 3 sec
			long pause=3000;//in Mili sec
			//Invoke Selenium Action class to perform Mouse over in Settings Menu
		  
			Actions action=new Actions(driver);
		  
			//moveToElement will move the mouse to the middle of Settings Menu, however it does NOT click Settings Menu
		  
			action.moveToElement(element).build().perform();
			
			 try {
					Thread.sleep(pause);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	

	}
  
	public WebElement FindSubMenu(String ParentMenu,String SubMenu){
		String XpathSubMenuExpression="//div[text()='"+ParentMenu+"']/../following-sibling::div[@class='children']//div[text()='"+SubMenu+"']";
		WebElement SubMenuWebElement=driver.findElement(By.xpath(XpathSubMenuExpression));
		return SubMenuWebElement;
	}
	
	private void switchToiframe(String FrameName){
						long pause=3000;
						//switch to iframe by name
						try{
						driver.switchTo().frame(FrameName);
						}
						catch(NoSuchFrameException e){
							System.out.println("Modal is not visible");
							e.printStackTrace();
						}
						try{
							
							Thread.sleep(pause);
							}
						catch (InterruptedException e) {				
							e.printStackTrace();
							}
						
					}
	
	public void ClickContextMenu(WebElement ContextMenuItem){
					long pause=3000;
					try{
					ContextMenuItem.click();
					}
					catch (NoSuchElementException e){
						System.out.println("WebElement is not found in page: "+driver.getCurrentUrl()+" with Title: "+driver.getTitle());
						e.printStackTrace();
					}
						
					
					try{
						
						Thread.sleep(pause);
						}
					catch (InterruptedException e) {				
						e.printStackTrace();
						}
					
			}
	public String RandomText(){
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		  StringBuilder sb = new StringBuilder();
		  Random random = new Random();
		  for (int i = 0; i < 20; i++) {
		      char c = chars[random.nextInt(chars.length)];
		      sb.append(c);
		      
		  }
		  return sb.toString();
	}
	
	public void waitForOCAElement(String XpathExpression,int waitInSecs){
			WebDriverWait waitforElementTobePresent = new WebDriverWait(driver,waitInSecs);
			try{
			waitforElementTobePresent.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.xpath(XpathExpression))));
				if (driver.findElements(By.xpath(XpathExpression)).size()>1){
					System.out.println("Total "+driver.findElements(By.xpath(XpathExpression)).size()+" Number of Element with Xpath :"+XpathExpression+" present in page :"+driver.getCurrentUrl()+" with Title: "+driver.getTitle());
				}
				else{
					System.out.println("Total "+driver.findElements(By.xpath(XpathExpression)).size()+" Number of Element with Xpath :"+XpathExpression+" present in page :"+driver.getCurrentUrl()+" with Title: "+driver.getTitle());
				}
				
			}
			catch (NoSuchElementException e)
			{
				System.out.println("WebElement is not found in page: "+driver.getCurrentUrl()+" with Title: "+driver.getTitle()+ " with xpath expression :"+XpathExpression+" after waifing for :"+waitInSecs+" secs");
				e.printStackTrace();
			}
			catch (TimeoutException e)
			{
				System.out.println("WebElement is not found in page: "+driver.getCurrentUrl()+" with Title: "+driver.getTitle()+ " with xpath expression :"+XpathExpression+" after waifing for :"+waitInSecs+" secs");
				e.printStackTrace();
			}
		  
	}
	
	/*
	public void IntractionWithElement(WebDriver driver,WebElement Element) {
		this.driver=driver;
		Element.click();
		//System.out.println(IDEIntraction); 
	
	}*/
}
