package org.redhat.integration.selenium;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

public enum BrowserType {


    FIREFOX {
    	private DesiredCapabilities capabilities;
        
    	@Override
    	protected void setDefaultCapabilities() {
            capabilities = DesiredCapabilities.firefox();
            FirefoxProfile profile = new FirefoxProfile();
            capabilities.setCapability(FirefoxDriver.PROFILE, profile);
            capabilities.setCapability("marionette", true);
            
        }

        @Override
        public WebDriver getDriver(Proxy proxy) {
            if (null != proxy) {
                capabilities.setCapability(PROXY, proxy);
            }
            return new FirefoxDriver(capabilities);
        }
        
        @Override
        public DesiredCapabilities getDefaultCapabilities(){
        	return capabilities;
        }
        
        
    },
    CHROME {
    	private DesiredCapabilities capabilities;
    	
    	@Override
		protected void setDefaultCapabilities() {
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
            HashMap<String, String> chromePreferences = new HashMap<String, String>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            capabilities.setCapability("chrome.prefs", chromePreferences);

        }

        @Override
        public WebDriver getDriver(Proxy proxy) {
            if (null != proxy) {
                capabilities.setCapability(PROXY, proxy);
            }
            return new ChromeDriver(capabilities);
        }
        
        @Override
        public DesiredCapabilities getDefaultCapabilities(){
        	return capabilities;
        }

    },
    IE {
    	private DesiredCapabilities capabilities;
    	
    	@Override
        protected void setDefaultCapabilities() {
            capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            capabilities.setCapability("requireWindowFocus", true);
   
        }

        @Override
        public WebDriver getDriver(Proxy proxy) {
            if (null != proxy) {
                capabilities.setCapability(PROXY, proxy);
            }
            return new InternetExplorerDriver(capabilities);
        }
        @Override
        public DesiredCapabilities getDefaultCapabilities(){
        	return capabilities;
        }
        
    },
    EDGE {
    	private DesiredCapabilities capabilities;
    	
        @Override
        protected void setDefaultCapabilities() {
            capabilities = DesiredCapabilities.edge();
        }

        @Override
        public WebDriver getDriver(Proxy proxy) {
            
            if (null != proxy) {
                capabilities.setCapability(PROXY, proxy);
            }
            return new EdgeDriver(capabilities);
        }
        @Override
        public DesiredCapabilities getDefaultCapabilities(){
        	return capabilities;
        }

    },
    SAFARI {
    	private DesiredCapabilities capabilities;
    	
        @Override
        protected void setDefaultCapabilities() {
            capabilities = DesiredCapabilities.safari();
            capabilities.setCapability("safari.cleanSession", true);
           
        }

        @Override
        public WebDriver getDriver(Proxy proxy) {
            if (null != proxy) {
                capabilities.setCapability(PROXY, proxy);
            }
            return new SafariDriver(capabilities);
        }
        @Override
        public DesiredCapabilities getDefaultCapabilities(){
        	return capabilities;
        }

    },
    OPERA {
    	private DesiredCapabilities capabilities;
    	
        @Override
        protected void setDefaultCapabilities() {
            capabilities = DesiredCapabilities.operaBlink();
        }

        @Override
        public WebDriver getDriver(Proxy proxy) {
            if (null != proxy) {
                capabilities.setCapability(PROXY, proxy);
            }
            return new OperaDriver(capabilities );
        }
        @Override
        public DesiredCapabilities getDefaultCapabilities(){
        	return capabilities;
        }

    },
    PHANTOMJS {
    	private DesiredCapabilities capabilities;
    	
        @Override
        protected void setDefaultCapabilities() {
            capabilities = DesiredCapabilities.phantomjs();
            
            List<String> cmd = new ArrayList<String>();
            cmd.add("--web-security=false");
            cmd.add("--ssl-protocol=any");
            cmd.add("--ignore-ssl-errors=true");
         
            capabilities.setCapability("phantomjs.cli.args",cmd);
            capabilities.setCapability("takesScreenshot", true);

        }

        @Override
        public WebDriver getDriver(Proxy proxy) {
        	List<String> cmd = (List<String>) (capabilities.getCapability("phantomjs.cli.args"));
            if (null == proxy) {
                cmd.add("--proxy-type=none");
            } else {
                cmd.add("--proxy-type=http");
                cmd.add("--proxy=" + proxy.getHttpProxy());
            }
            capabilities.setCapability("phantomjs.cli.args",cmd);
            return new PhantomJSDriver(capabilities);
        }
        @Override
        public DesiredCapabilities getDefaultCapabilities(){
        	return capabilities;
        }
        
    };

	
    public abstract WebDriver getDriver(Proxy proxy);
    public abstract DesiredCapabilities getDefaultCapabilities();
    protected abstract void setDefaultCapabilities();
    
    private BrowserType() {
    	setDefaultCapabilities();
    }
    
    
}