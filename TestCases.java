package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Wrapper;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Assert;

public class TestCases {
    ChromeDriver driver;
    By seachTextXpath = By.xpath("//input[@title='Search for Products, Brands and More']");

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }

    @BeforeMethod
    public void navigateToUrl() {
        driver.get("https://www.flipkart.com/");
    }

    @Test(priority = 1)
    public void testCase01() throws InterruptedException {
        System.out.println("Start TstCase01 : Sort by popularity with rating less than or equal to 4 stars.");

        double starRating = 4.0;
        Wrappers.enterTextWrapper(driver, seachTextXpath, "Washing Machine");
        Thread.sleep(3000);
        Wrappers.clickOnElementWrapper(driver, By.xpath("//div[contains(text(),'Popularity')]"));
        Thread.sleep(4000);
        boolean status = Wrappers.searchStarRatingAndPrintCount(driver,
                By.xpath("//span[contains(@id,'productRating')]/div"), starRating, "washing machine");
        org.testng.Assert.assertTrue(status);

        System.out.println("End TstCase01 : Sort by popularity with rating less than or equal to 4 stars.");
    }

    @Test(priority = 2)
    public void testCase02() throws InterruptedException {
        System.out.println("Start TstCase02 : print the Titles and discount % of items with more than 17% discount");
        String textToEnter = "iPhone";
        int discount = 17;
        Wrappers.enterTextWrapper(driver, seachTextXpath, textToEnter);
        Thread.sleep(5000);
        boolean status = Wrappers.printTitleAndDiscountIPhone(driver, By.xpath("//div[contains(@class, 'yKfJKb')]"), discount);
        org.testng.Assert.assertTrue(status);

        System.out.println("End Start TstCase02 : print the Titles and discount % of items with more than 17% discount");
    }
    @Test(priority = 3)
    public void testCase03() throws InterruptedException{
        System.out.println("Beggining : testCase03");
        Wrappers.enterTextWrapper(driver, seachTextXpath, "Coffee Mug");
        Thread.sleep(3000);
        Wrappers.clickOnElementWrapper(driver, By.xpath("//div[contains(text(),'4★ & above')]"));
        Thread.sleep(3000);
        boolean status = Wrappers.printTitleAndImageUrlOfCoffMug(driver, By.xpath("//div[@class='slAVV4']//span[@class='Wphh3N']"));
        org.testng.Assert.assertTrue(status);

        System.out.println("Ending : testCase03");
    }
}