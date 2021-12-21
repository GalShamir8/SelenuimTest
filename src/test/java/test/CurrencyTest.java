package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class CurrencyTest {
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
		driver.manage().window().fullscreen();
		logger = LogManager.getLogger();
	}

	@AfterAll
	static void tearDownAfterClass() throws InterruptedException {
		Thread.sleep(TIME_INTERVAL);
		driver.quit();
	}

	@Test
	void currency_exchangeTest() {
		logger.info("currency test - begin");
		try {
			Map<Character, Double> curencyPriceMap = new HashMap<>();
			String currencyDivXPath = "//*[@id=\"form-currency\"]/div";
			String currencyButton = currencyDivXPath.concat("/button");
			WebElement currencyDiv = driver.findElement(By.xpath(currencyDivXPath));
			driver.findElement(By.xpath(currencyButton)).click();
			int numOfCurrencies = currencyDiv.findElements(By.tagName("li")).size();
			driver.findElement(By.xpath(currencyButton)).click();

			for (int i = 1; i <= numOfCurrencies; i++) {
				currencyDiv = driver.findElement(By.xpath(currencyDivXPath));
				driver.findElement(By.xpath(currencyButton)).click();

				/* clicks the ith button */
				currencyDiv.findElement(By.xpath(String.format("ul/li[%d]", i))).click();

				String price = driver.findElements(By.className("price")).get(0).getText().split("\n")[0];
				// first character is a digit - price character appears at end of price string
				if ('0' <= price.charAt(0) && price.charAt(0) <= '9')
					/* assert null if the currency already appeared */
					assertNull(curencyPriceMap.putIfAbsent(price.charAt(price.length() - 1),
							Double.parseDouble(price.substring(0, price.length() - 1))));

				else /* price character at start of price string */
					assertNull(curencyPriceMap.putIfAbsent(price.charAt(0), Double.parseDouble(price.substring(1))));
			}

			/* ASSUMPTION - prices differ with each currency. no 1:1 exchange rate */
			assertEquals(new HashSet<>(curencyPriceMap.values()).size(), curencyPriceMap.values().size());

			logger.info("\ttest passed");
		} catch (Exception e) {
			logger.error("\ttest failed - " + e.getMessage());
		} finally {
			logger.info("currency test - end");
		}
	}
}
