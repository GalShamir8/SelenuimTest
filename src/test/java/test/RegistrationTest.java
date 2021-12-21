package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@TestMethodOrder(OrderAnnotation.class)
class RegistrationTest {
	private static WebDriver driver;
	private static Logger logger;
	private static final long TIME_INTERVAL = 2000;
	private static final String homePageURL = "http://tutorialsninja.com/demo";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String originPath = "C:/Program Files/ChromeDriver/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", originPath);
		driver = new ChromeDriver();
		driver.get(homePageURL);
		driver.manage().window().setSize(new Dimension(1296, 696));
		logger = LogManager.getLogger();

		/* ALL TESTS ARE BEING CONDUCTED ON REGISTRATION PAGE */
		WebElement myAccount = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a"));
		myAccount.click();
		Thread.sleep(TIME_INTERVAL);

		WebElement registrationPage = myAccount.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[1]/a"));
		registrationPage.click();
		Thread.sleep(TIME_INTERVAL);

	}

	@AfterAll
	static void tearDownAfterClass() throws InterruptedException {
		Thread.sleep(TIME_INTERVAL);
		driver.quit();
	}

	@BeforeEach
	void setUpBeforeTest() throws InterruptedException {
		driver.navigate().refresh(); // refresh the the page to clear everything
		Thread.sleep(TIME_INTERVAL);

	}

	@Test
	@Order(1)
	void registration_edgeCaseTest() {
		logger.info("------Test Registration Edge Case starts------");
		try {
			driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

			driver.findElement(By.cssSelector(".alert"));
			List<WebElement> textFieldAlerts = driver.findElements(By.cssSelector(".text-danger"));
			assertTrue(textFieldAlerts.size() > 0);

		} catch (NoSuchElementException e) {
			logger.error("Test Failed " + e.getMessage());
		} catch (Exception e) {
			logger.error("Test Failed " + e.getMessage());
		} finally {
			logger.info("------Test Registration Edge Case ends------");
		}
	}

	@Test
	@Order(2)
	void registration_existingUserTest() {
		logger.info("------Test Registration With Existing User starts------");
		try (FileReader reader = new FileReader("certificate.json")) {
			JSONObject certificate_data = (JSONObject) new JSONParser().parse(reader);
			@SuppressWarnings("unchecked")
			Map<String, String> certificate_map = (Map<String, String>) certificate_data.get("certificate");

			certificate_map.forEach((key, value) -> {
				driver.findElement(By.id(String.format("input-%s", key))).sendKeys(value);
			});
			driver.findElement(By.name("agree")).click();

			driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
			Thread.sleep(TIME_INTERVAL);

			// if the user has entered incorrect certificates the element below will appear
			assertEquals(driver.findElement(By.cssSelector(".alert")).getText(),
					"Warning: E-Mail Address is already registered!");

		} catch (Exception e) {
			logger.error("Test Failed " + e.getMessage());
			fail();
		} finally {
			logger.info("------Test Registration With Existing User ends------");

		}
	}
}
