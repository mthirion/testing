package org.redhat.integration.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class HomePage {

	private WebDriver driver;
	private String pageurl = "https://www.google.be/";
	
	private final static String testSearch_key="q";
	private static String testSearch_value="phantomjs";
	private static String testSearch_result="PhantomJS | PhantomJS";
	
	private int timeout=10;
	
	
	public HomePage(WebDriver webdriver) {
		driver=webdriver;
		driver.get(pageurl);
	}
	
	public void setSearchData(String data) {
		testSearch_value = data;
	}
	
	public void setSearchResult(String result) {
		testSearch_result = result;
	}
	
	public void setTimeout(int t){
		timeout=t;
	}
	
	@Test
	public void verifySearch() {
		
		// Define the test
		WebElement element = driver.findElement(By.name(HomePage.testSearch_key));
		element.clear();
		element.sendKeys(HomePage.testSearch_value);
		element.submit();
		
		
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
            	
            	return d.getPageSource().contains(HomePage.testSearch_result);
            }
        };
        
        return condition;
	}
	
	public String getPageSource() {
		 return driver.getPageSource();
	}
}
