package test;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileReader;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@TestMethodOrder(OrderAnnotation.class)
class SanityTest {
	private static WebDriver driver;
	private static Logger logger;
	private static final long TIME_INTERVAL = 2000;
	private static final String url = "http://tutorialsninja.com/demo";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String originPath = "C:\\Users\\galsh\\Desktop\\YEAR C\\Quality Assurance\\selenuim\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", originPath);
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().setSize(new Dimension(1296, 696));
		logger = LogManager.getLogger();

	}

	@Test
	@Order(1)
	void test_login() {

		logger.info("------Test login starts------");
		try (FileReader reader = new FileReader("certificate.json")) {
			JSONObject certificate_data = (JSONObject) new JSONParser().parse(reader);
			@SuppressWarnings("unchecked")
			Map<String, String> certificate_map = (Map<String, String>) certificate_data.get("certificate");
//			String username = certificate_map.get("username");
//			String password = certificate_map.get("password");
			Thread.sleep(TIME_INTERVAL);

			WebElement myAccount = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[1]"));
			myAccount.click();
			Thread.sleep(TIME_INTERVAL);

			WebElement loginPage = myAccount.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[2]/a"));
			loginPage.click();
			Thread.sleep(TIME_INTERVAL);

//			WebElement emailTextInput = driver.findElement(By.id("input-email"));
//			WebElement passwordTextInput = driver.findElement(By.id("input-password"));
//			emailTextInput.sendKeys(username);
//			passwordTextInput.sendKeys(password);
			
			/* functional approach to the problem. could be used for larger scales */
			certificate_map.forEach((key, value) -> {
				driver.findElement(By.id(String.format("input-%s", key))).sendKeys(value);
			});

			WebElement loginBottom = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div/form/input"));
			loginBottom.click();
			Thread.sleep(TIME_INTERVAL);

			// if the user has entered incorrect certificates the element below will appear
			assertNull(driver.findElement(By.xpath("//*[@id=\"account-login\"]/div[1]")));

		} catch (NoSuchElementException e) {
			logger.info("Test login passed");
		} catch (Exception e) {
			logger.error("Test Failed " + e.getMessage());
		} finally {
			logger.info("------Test login ends------");
			driver.get(url);
		}

	}

	@Test
	@Order(2)
	void test_cart_add_product() {
		try {
			logger.info("------Test purchase starts------");
			WebElement product = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div/div[1]/a/img"));
			product.click();
			WebElement buttonAddToCart = driver.findElement(By.xpath("//*[@id=\"button-cart\"]"));
			buttonAddToCart.click();
			Thread.sleep(TIME_INTERVAL);
			WebElement buttonGoToCart = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[4]/a"));
			buttonGoToCart.click();
			Thread.sleep(TIME_INTERVAL);
			WebElement purchaseButton = driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div[2]/a"));
			purchaseButton.click();
			logger.info("------Test purchase passed------");
		} catch (Exception e) {
			logger.error("Test Failed " + e.getMessage());
		} finally {
			logger.info("------Test purchase ends------");
		}

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Thread.sleep(TIME_INTERVAL);
		driver.quit();
	}

}
