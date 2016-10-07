package org.redhat.integration.selenium.tests;

import org.openqa.selenium.WebDriver;
import org.redhat.integration.selenium.PageProvider;
import org.redhat.integration.selenium.WebDriverManager;
import org.redhat.integration.selenium.pages.HomePage;
import org.redhat.integration.selenium.pages.PrivacyPage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.redhat.integration.selenium.listeners.MyTestListener;


/*
 	List of system properties
	-------------------------

	proxyHost		: browser proxy hostname or IP		; default to 'null'
	proxyPort		: browser proxy port				; default to 'null'

	browser			: browser type (firefox chrome...)	; default to 'phantomjs'
	phantomjsPath	: phantomjs executable path	

	version	 	: browser version					; default to '2.1'
	serverUrl	: server URL for remote driver		; default to 'localhost'
	platform	: target platform for remote driver	; default to 'linux'

 */


@Listeners(MyTestListener.class)
public class MyTestIT {

	private PageProvider provider;
	
	@BeforeTest
	public void setUp() {
		provider = new PageProvider(new WebDriverManager().getDriver());
	}
	
	
	@Test(groups="google", suiteName="navigationTest", testName="homeTest")
	public void testCase1() {
		
		System.out.println("Test Case1 :: testing home page");
		HomePage homepage = provider.getHomePage();
		homepage.setSearchData("phantomjs");
		homepage.setSearchResult("phantomjs tutorial");
		homepage.setTimeout(5);
		
		homepage.verifySearch();
	}
	
	@Test
	public void testCase2() {
		
		System.out.println("Test Case2 :: testing privacy page");
		PrivacyPage privacypage = provider.getPrivacyPage();		
		privacypage.checkPolicy();
	}	
	
	@AfterTest
	public void tearDown() {
		System.out.println("Closing the browser");
		provider.closeSite();
	}
}
