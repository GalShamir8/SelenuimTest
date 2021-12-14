package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.NewSessionPayload;
import org.testng.log4testng.Logger;

@TestMethodOrder(OrderAnnotation.class)
class SanityTest {
	private static WebDriver driver;
	private Map<String, Object> vars;
	private static JavascriptExecutor js;
	private static Logger logger;
	private static final long TIME_INTERVAL = 2000;
	private static final String url = "http://tutorialsninja.com/demo";


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String originPath = "C:\\Users\\galsh\\Desktop\\YEAR C\\Quality Assurance\\selenuim\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", originPath);
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		driver.get(url);
		driver.manage().window().setSize(new Dimension(1296, 696));
		logger = Logger.getLogger(SanityTest.class);

	}

//	@Test
//	@Order(2)
//	void test_login() {
//
//		logger.info("------Test login starts------");
//		try (FileReader reader = new FileReader("certificate.json")) {
//			JSONObject certificate_data = (JSONObject) new JSONParser().parse(reader);
//			Map<String, String> certificate_map = (Map) certificate_data.get("certificate");
//			String username = certificate_map.get("username");
//			String password = certificate_map.get("password");
//			Thread.sleep(TIME_INTERVAL);
//
//			WebElement myAccount = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[1]"));
//			myAccount.click();
//			Thread.sleep(TIME_INTERVAL);
//
//			WebElement loginPage = myAccount.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[2]/a"));
//			loginPage.click();
//			Thread.sleep(TIME_INTERVAL);
//
//			WebElement emailTextInput = driver.findElement(By.id("input-email"));
//			WebElement passwordTextInput = driver.findElement(By.id("input-password"));
//			emailTextInput.sendKeys(username);
//			passwordTextInput.sendKeys(password);
//
//			WebElement loginBottom = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div/form/input"));
//			loginBottom.click();
//			Thread.sleep(TIME_INTERVAL);
//
//			// if the user has entered incorrect certificates the element below will appear
//			assertNull(driver.findElement(By.xpath("//*[@id=\"account-login\"]/div[1]")));
//
//		} catch (NoSuchElementException e) {
//			logger.info("Test login passed");
//		} catch (Exception e) {
//			logger.error("Test Failed " + e.getMessage());
//		} finally {
//			logger.info("------Test login ends------");
//			driver.get(url);
//		}
//
//	}
	
	@Test
	@Order(1)
	void test_cart_add_product() {
		try {
			WebElement productsList = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]"));
			
			
		}catch (Exception e) {
			logger.error("Test Failed " + e.getMessage());
		} finally {
			logger.info("------Test login ends------");
		}
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Thread.sleep(TIME_INTERVAL);
		driver.quit();
	}

}