# Selenium template

## Description
This is a template for automated Acceptance testing using Selenium

## Application details

### Classes
The implementation follows the Page Object pattern.

#### MyTestIT
The starting point is the MyTestIT.java class, that defines the tests from a functional perspective. <br>
It follows the naming convention in order to be run by the maven failsafe plugin. <br>
This class uses a PageProvider whose role is to deliver one object for each page to be tested. <br>

#### Page objects
Pages are defined in the 'pages' package. <br>
This page object represents one single page that contains all the methods to perfom tests on this page. <br>

#### WebDriverManager
The pageProvider requires a WebDriver to be defined. <br>
The Webdriver object is an Selenium objects that emulates one specific browser of one specific version on one specific platform. <br>

We use a WebDriverManager class to handle the instanciation of such a browser. <br>
Basically the interaction is done through the 'getDriver()' or 'getRemoteDriver()' methods. <br>
The first one creates a local web driver, while the second requires the URL of a remote Selenium standalone server that will be used to run the test on a browser on a remote machine.

##### Configuring a driver
A driver is entirely configured through 'capabilities' that represent all the characteristics of the browser. <br>
We used the Enum 'BrowserType' to represents all the supported browsers with default capabilities. <br>

The 'getDriver()' method delegates the configuration of the browser capabilities to the 'getBrowser()' method <br>
This method looks for command line arguments to use for the configuration <br>
The command line overrides the configuration in the code <br>

The command line arguments are listed both in the MyTestIT class and in the pom.xml

	proxyHost		: browser proxy hostname or IP		; default to 'null'                
	proxyPort		: browser proxy port				; default to 'null'        

	browser			: browser type (firefox chrome...)	; default to 'phantomjs'           
	phantomjsPath	: phantomjs executable path	                                                   

	version	 	: browser version					; default to '2.1'         
	serverUrl	: server URL for remote driver		; default to 'localhost'                   
	platform	: target platform for remote driver	; default to 'linux'

If nothing is configured on the command line, another option is to configure the browser directly with the 'configureBrowser()' method <br>
Finaly, if nothing is defined, it falls back to a defualt browser. The default browser is PhantomJS <br>

A Proxy can be defined for the browser, either through the command line (configureProxyFromCLI) or from the code (configureProxy).


## Selelnium tests

### Writing tests
The execution of the tests from the main MyTestIT.java class is a 3 steps process: <br>
- define a driver
- get a page
- perform tests on the page

### Tests execution

Here for the demo we simply test 2 Google pages. <br>
The first page (HomePage) perform a google search and checks the existence of a particular string in the search result. <br>
The second page (PrivacyPage) opens the google privacy page and checks the existence of a particular string in the page. <br>

#### Test pages
The pages expose test methods and contain the logic to perform the test in interactaction with the driver.

#### PhantomJS
When a driver begins, it actually launches a browser, which requires a graphical interface.
PhantomJS is an headless browser that is very useful to test browser-based applications from a machine that doesn't have a GUI.


### Maven

#### Failsafe plugin
We'll use the maven failsafe plugin, which is more about integration testing, rather than the surefire one, more about unit testing. <br>
Though we're targetting Acceptance tests and both plugin would work, we think that failsafe is a better option to keeps everyting clear. <br>

This plugin integrates with the 'integration-test' and 'verify' phase. <br>
We therefore used 'mvn verify' to run the test. <br>

The failsafe plugin looks for tests classes named after "<name>IT.java", so it will run MyTestIT.java <br>
This class has methods annotated with @Test, and support a TestListener that can be used to reacts on the events raised by each run test. <br>

#### Selenium downloader plugin
Selenium has a downloader plugin that can be used to automatically download the drivers binaries at runtime. <br>
The plugin is based on the 'test/resources/repositoryMap.xml', which defines the location from where to download the emulator binaries for each browser/platform <br>
The plugin is configure to download the zip in 'test/resources/drivers_zips' and to expand the binaries to 'resources/drivers_bin'. <br>

The downloader plugin will set some system variables to retain the location of the downloaded drivers.

### TestNG
The tests leverage the testNG framework rather than the JUnit one.


