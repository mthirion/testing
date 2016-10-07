package org.redhat.integration.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class PrivacyPage {

	private WebDriver driver;
	private String pageurl = "https://www.google.be/intl/en/policies/privacy/?fg=1";
	
	private static String testSearch_result="Privacy Policy";
	
	private int timeout=10;
	
	
	public PrivacyPage(WebDriver webdriver) {
		driver=webdriver;
		driver.get(pageurl);
	}

	public void setSearchResult(String result) {
		testSearch_result = result;
	}
	
	public void setTimeout(int t){
		timeout=t;
	}
	
	@Test
	public void checkPolicy() {
		
		// Define the test
		
        // Wait until the page is rendered
        // Timeout after 10 seconds
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        
        // set a condition on the wait
        // either the condition is true or we will timeout
        wait.until(getTestSearchCondition());

	}
	
	private ExpectedCondition<Boolean> getTestSearchCondition() {
        
		// define a condition
        ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
            	
            	return d.getPageSource().contains(PrivacyPage.testSearch_result);
            }
        };
        
        return condition;
	}
	
	public String getPageSource() {
		 return driver.getPageSource();
	}
}
