package connection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectWebsite {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;
	
	  public void setUp() {
		String originPath = "C:\\Users\\galsh\\Desktop\\YEAR C\\Quality Assurance\\selenuim\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver",originPath);
	    driver = new ChromeDriver();
	    js = (JavascriptExecutor) driver;
	    String url = "http://tutorialsninja.com/demo";
	    driver.get(url);
	  }
	  
	  public void tearDown() {
	    //driver.quit();
	  }
	  
}
