package org.redhat.integration.selenium;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverManager {

	private final BrowserType default_browser = BrowserType.PHANTOMJS;
	private final String default_version = "2.1";
	private BrowserType browser = null;

	private final Platform default_platform = Platform.LINUX;
	private final String default_server_url = "http://localhost:4545";

	private WebDriver webdriver = null;
	private WebDriver remote_webdriver = null;
	private Proxy proxy = null;

	/*
	 * --------------------- SYSTEM PROPERTIES ---------------------
	 */
	private final String sys_os = System.getProperty("os.name");
	private final String sys_arch = System.getProperty("os.arch");

	final String sys_proxyHost = System.getProperty("proxyHost");
	final Integer sys_proxyPort = Integer.getInteger("proxyPort");

	final String sys_browser = System.getProperty("browser");
	final String sys_phantomjs = System.getProperty("phantomjsPath");
	final static String PHANTOMSJS_DEFAULT_BINARY_PATH = "/redhat/projects/workspace/selenium/src/test/resources/drivers_bin/linux/phantomjs/64bit/phantomjs";

	final String sys_url = System.getProperty("serverUrl");
	final String sys_platform = System.getProperty("platform");
	final String sys_version = System.getProperty("version");

	/*
	 * ------------------- PROXY CONFIGURATION -------------------
	 */
	public void configureProxy(String host, String port) {

		if (host != null && port != null) {
			String proxy_setting = String.format("%s:%d", host, port);

			if (proxy == null)
				proxy = new Proxy();
			proxy.setProxyType(MANUAL);
			proxy.setHttpProxy(proxy_setting);
			proxy.setSslProxy(proxy_setting);
		}
	}

	protected void configureProxyFromCLI() {

		if (sys_proxyHost != null && sys_proxyPort != null) {
			final String proxy_settings = String.format("%s:%d", sys_proxyHost, sys_proxyPort);

			proxy = new Proxy();
			proxy.setProxyType(MANUAL);
			proxy.setHttpProxy(proxy_settings);
			proxy.setSslProxy(proxy_settings);
		}
	}

	public Proxy getProxy() {
		return proxy;
	}

	/*
	 * --------------------- BROWSER CONFIGURATION ---------------------
	 */
	public BrowserType getDefaultBrowser() {
		return default_browser;
	}

	public void configureBrowser(String browserName) {

		BrowserType bt = null;
		try {
			bt = BrowserType.valueOf(browserName.toUpperCase());

		} catch (IllegalArgumentException ignored) {
			System.err.println("WARNING :: Unknown browser [ " + browserName + " ] ");
		} catch (NullPointerException ignored) {
			System.err.println("WARNING :: browser unset");
		}
		browser = bt;
	}
	
	public void configureBrowser(String browserName, String binary_path) {

		configureBrowser(browserName);

		if (browser != null) {
			if (browser.toString().toUpperCase().equals("PHANTOMJS")) {
				if (binary_path != null)
					System.setProperty("phantomjs.binary.path", binary_path);
			}
		}
	}

	protected void configureBrowserFromCLI() {

		BrowserType bt = browser; // do not change the value if there is nothing
									// defined on the CLI
		try {
			bt = BrowserType.valueOf(sys_browser.toUpperCase());

		} catch (IllegalArgumentException ignored) {
			System.err.println("WARNING :: Unknown browser [ " + sys_browser + " ]");
		} catch (NullPointerException ignored) {
			System.err.println("WARNING :: 'browser' system variable unset");
		}
		browser = bt;

		if (browser != null) {
			if (browser.toString().toUpperCase().equals("PHANTOMJS")) {
				if (sys_phantomjs != null)
					System.setProperty("phantomjs.binary.path", sys_phantomjs);
			}
		}
	}

	public BrowserType getBrowser() {
		configureBrowserFromCLI(); // CLI override the code
		if (browser == null) // nothing on CLI, and the browser wasn't set
								// either in the code
			return default_browser;

		return browser; // either from the code, or overriden on the CLI
	}

	/*
	 * -------------------- DRIVER CONFIGURATION --------------------
	 */
	public void configureRemoteDriver(String url, String platform, String version) {

		if (url != null && platform != null && version != null) {
			browser = this.getBrowser();
			DesiredCapabilities capabilities = browser.getDefaultCapabilities();

			URL target_server = null;
			try {
				target_server = new URL(url);
			} catch (MalformedURLException e) {
				System.err.println("WARNING :: Malformed URL [ " + url + " ] ");
			}

			Platform p = null;
			try {
				p = Platform.valueOf(platform);

			} catch (IllegalArgumentException ignored) {
				System.err.println("WARNING :: Unknown platform [ " + platform + " ] ");
			} catch (NullPointerException ignored) {
				System.err.println("WARNING :: platform unset");
			}

			if (target_server != null && platform != null) { // otherwise
																// remote_webdriver
																// stays null
				capabilities.setPlatform(p);
				capabilities.setVersion(version);

				remote_webdriver = new RemoteWebDriver(target_server, capabilities);
			}

		}

	}

	protected void configureRemoteDriverFromCLI() {

		if (sys_url != null && sys_platform != null && sys_version != null) {
			browser = this.getBrowser();
			DesiredCapabilities capabilities = browser.getDefaultCapabilities();

			URL target_server = null;
			try {
				target_server = new URL(sys_url);
			} catch (MalformedURLException e) {
				System.err.println("WARNING :: Malformed URL [ " + sys_url + " ] ");
			}

			Platform p = null;
			try {
				p = Platform.valueOf(sys_platform.toUpperCase());

			} catch (IllegalArgumentException ignored) {
				System.err.println("WARNING :: Unknown platform [ " + sys_platform + " ] ");
			} catch (NullPointerException ignored) {
				System.err.println("WARNING :: platform unset");
			}

			if (target_server != null && sys_platform != null) { // otherwise
																	// remote_webdriver
				capabilities.setPlatform(p);
				capabilities.setVersion(sys_version);
				remote_webdriver = new RemoteWebDriver(target_server, capabilities);
			}
		}

	}

	public WebDriver getRemoteDriver() throws MalformedURLException {
		configureRemoteDriverFromCLI(); // CLI override the code
		if (remote_webdriver == null) // nothing on CLI, and the driver wasn't
										// set either in the code
		{
			URL url = new URL(default_server_url);
			DesiredCapabilities default_capabilities = getDefaultBrowser().getDefaultCapabilities();
			default_capabilities.setPlatform(default_platform);
			default_capabilities.setVersion(default_version);
			return new RemoteWebDriver(url, default_capabilities);
		}
		return remote_webdriver; // either from the code, or overriden on the
									// CLI
	}

	public WebDriver getDriver() {
		configureProxyFromCLI();
		BrowserType b = getBrowser();

		return b.getDriver(proxy);

	}
}
