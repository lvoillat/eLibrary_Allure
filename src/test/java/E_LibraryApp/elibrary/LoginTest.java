package E_LibraryApp.elibrary;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

import org.testng.annotations.*;


/**
 * LoginTest always fails, it is executed if you select "All Tests" build definition when
 * creating a new build using Polarion
 */

public class LoginTest extends TestCase {

	public static WebDriver driver;

	 /**
	  @wi.implements SD-1731 Test User Login
	  */

	@Test
    public void testLogin() throws MalformedURLException, InterruptedException {

	    String pathSelenium		= "C:\\LCS\\Selenium";   // double backslash between every folder
	    String rmWebDrvURL		= "http://localhost:4444/wd/hub";		// Selenium Remote Web Driver URL

	    String polarionURL		= "http://pl1chzrh0114nb/polarion/";	// Polarion URL you want to test (Login)
	    String polarionUser		= "lVoillat";							// Polarion Username
	    String polarionPassword	= "1";									// Polarion Password

	    String successMsg		= "Welcome to Polarion. You logged in successfully";


    	// Chrome
		System.setProperty("webdriver.chrome.driver", pathSelenium + "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
    	
 		// Firefox
//		System.setProperty("webdriver.firefox.driver", pathSelenium + "geckodriver.exe");
//		FirefoxOptions options = new FirefoxOptions();
		
        options.addArguments("test-type");
//        options.addArguments("start-maximized");
//        options.addArguments("--js-flags=--expose-gc");  
		
        driver = new RemoteWebDriver(new URL(rmWebDrvURL), options);
      //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);        
        driver.get(polarionURL);
		System.out.println("Test - Title is: "+driver.getTitle());
		
	    // Perform actions on HTML elements, entering text and submitting the form
	    WebElement usernameElement     = driver.findElement(By.name("j_username"));
	    WebElement passwordElement     = driver.findElement(By.name("j_password"));
	    WebElement StayLoggedInElement = driver.findElement(By.name("rememberme"));
	    WebElement formElement         = driver.findElement(By.id("submitButton"));
	 
	    usernameElement.clear();
	    passwordElement.clear();
	    usernameElement.sendKeys(polarionUser);
	    passwordElement.sendKeys(polarionPassword);
	    StayLoggedInElement.click();
	    
	    formElement.submit();        // submit by form element	    
	    
	    try {
		    // Anticipate web browser response, with an explicit wait
	    	WebDriverWait wait = new WebDriverWait(driver, 5);
	    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("DOM_13")));   // Check for an existing element on the 1st page after login
	        System.out.println("Message: "+successMsg);
	    	assertTrue(successMsg,true);
	    }
	    catch(Exception e) {
	    	WebElement errorMessage = driver.findElement(By.id("errorMessage"));
	    	String failedMsg = errorMessage.getText();
	    	System.out.println("Message: "+failedMsg);
	    	driver.quit();
	    	assertTrue(failedMsg,false);
	    }
	    
	    
	    // Polarion Logout
		TimeUnit.SECONDS.sleep(8);	    
		WebDriverWait wait4 = new WebDriverWait(driver, 3);
		wait4.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@class=\"gwt-Image\"]")));		
		driver.findElement(By.xpath("//img[@class=\"gwt-Image\"]")).click();

		WebDriverWait wait5 = new WebDriverWait(driver, 3);
		wait5.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()=\"Logout\"]")));	
		driver.findElement(By.xpath("//div[text()=\"Logout\"]")).click();
	    	    
	    
	    // Conclude test
	    TimeUnit.SECONDS.sleep(5);   // Wait till Login operation is done
	    driver.close();
	    driver.quit();

    }

    
	 /**
	  @wi.implements SD-1454 New Test User Logout function
	  */
    
/**
    public void testLogout() {
       throw new RuntimeException("Cannot logout, no logged in user");
    }
*/
}