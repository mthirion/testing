package org.redhat.integration.selenium;

import org.openqa.selenium.WebDriver;
import org.redhat.integration.selenium.pages.HomePage;
import org.redhat.integration.selenium.pages.PrivacyPage;

public class PageProvider {

	private WebDriver driver;
	
	public PageProvider(WebDriver webdriver){
		driver=webdriver;
	}
	
	public HomePage getHomePage(){
		return new HomePage(driver);
	}
	
	public PrivacyPage getPrivacyPage(){
		return new PrivacyPage(driver);
	}	
	
	public void closeSite() {
		driver.close();
	}
	
}
