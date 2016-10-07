package org.redhat.integration.selenium.listeners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;



public class MyTestListener extends TestListenerAdapter {

	@Override
	public void onTestFailure(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestFailure(tr);
	}

	@Override
	public void onTestStart(ITestResult result) {
		
		System.out.println("executing test method ' " + result.getName() + " '");
		super.onTestStart(result);
	}

}
