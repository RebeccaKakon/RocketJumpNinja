package automation.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected WebDriver driver;


    @BeforeSuite
    public void setUpSuite() {
        // Set up any suite-level configurations if needed
    }

    @BeforeMethod
    public void setUp() throws InterruptedException {
        // Set up the WebDriver instance (e.g., ChromeDriver)
        System.setProperty("webdriver.chrome.driver", "C:\\webDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//        driver.wait(3000);

        openBrowser("https://www.rocketjumpninja.com/mouse-search");

    }

    @Step("Open browser and navigate to URL")
    protected void openBrowser(String url) {
        driver.get(url);
    }


    @AfterMethod
    public void tearDown() {
        // Close the WebDriver instance after each test
        if (driver != null) {
            driver.quit();
        }
    }
    // we can put her login logout methods as well ,  cleaning up the environment after a test, and setting up shared resources.
}
