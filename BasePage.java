package automation.base;

import org.openqa.selenium.WebDriver;

import static automation.utils.ThreadUtils.sleepXMillis;


public class BasePage {

    protected static WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitXMillis(int timeoutInMillis) {
        long endTime = System.currentTimeMillis() + timeoutInMillis;
        while (System.currentTimeMillis() < endTime) {
            sleepXMillis(Math.min((int) Math.abs(endTime - System.currentTimeMillis()), 5000));
        }
    }

    public void waitXSeconds(int timeoutInSeconds) {
        waitXMillis(timeoutInSeconds * 1000);
    }

//    if it's a locator will use this click method , by if it's a web element ell use the existed click method
//    protected void click(By locator) {
//        retryMethod(locator, this::clickB);
//    }

    // You can add common methods and initialization logic for all page classes here
}
