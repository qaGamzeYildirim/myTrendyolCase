package base;

import com.google.common.base.Function;
import com.google.common.base.Stopwatch;
import driver.DriverProvider;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;


    public BasePage(DriverProvider driverProvider) {
        this.driver = driverProvider.getDriver();
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));
    }

    /**
     * Wait Wrapper Method by given locator
     *
     * @param locator locator of the element
     * @return WebElement
     */
    public WebElement waitVisibility(By locator) {
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait Wrapper Method by given wait condition for element
     *
     * @param function wait function
     * @return WebElement
     */
    public WebElement waitForElement(Function<WebDriver, WebElement> function) {
        return this.wait.until(function);
    }

    /**
     * Wait Wrapper Method by given wait condition for elements
     *
     * @param function wait function
     * @return List<WebElement>
     */
    public List<WebElement> waitForElements(Function<WebDriver, List<WebElement>> function) {
        return this.wait.until(function);
    }

    /**
     * Wait for element to be clickable
     *
     * @param locator locator of the element
     * @return WebElement
     */
    public WebElement waitForElementClickable(By locator) {
        return waitForElement(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Checks if element is visible on the page
     *
     * @param locator locator of the element
     * @return boolean
     */
    public boolean isVisibilityOfElementLocated(By locator) {
        return waitForElement(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    /**
     * Wait for element to be visible
     *
     * @param locator locator of the element
     * @return List<WebElement>
     */
    public List<WebElement> waitForVisibilityOfAllElements(By locator) {
        return waitForElements(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Click element by given locator
     *
     * @param locator  locator of the element
     * @param waitType wait type
     */
    public void click(By locator, String... waitType) {
        waitAccordingToType(locator, waitType);
        find(locator).click();
    }

    /**
     * Find element by given locator
     *
     * @param locator locator of the element
     * @return WebElement
     */
    public WebElement find(By locator) {
        if (isElementPresent(locator)) {
            return driver.findElement(locator);
        } else throw new NoSuchElementException(String.format("Cannot find an element using by %s", locator));
    }

    /**
     * Check if given element's locator is present during default wait period or not
     *
     * @param locator locator of the input
     * @return boolean
     */
    public boolean isElementPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.size() != 0;
    }

    /**
     * Delay a certain number of seconds, rather than to respond as soon as possible
     *
     * @param milliseconds time of pause in milliseconds
     */
    public void pause(Integer milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find elements list by given locator
     *
     * @param locator locator of the element
     * @return List<WebElement>
     */
    public List<WebElement> findElements(By locator) {
        final Stopwatch timer = Stopwatch.createStarted();
        try {
            pause(500);
            return driver.findElements(locator);
        } finally {
            timer.stop();
        }
    }

    /**
     * Hover to the given element
     */
    public void hoverTo(By locator) {
        Actions actions = new Actions(driver);
        WebElement element = waitVisibility(locator);
        actions.moveToElement(element).build().perform();
    }

    /**
     * Send keys to the element
     *
     * @param locator    locator of the element
     * @param keysToSend keys to send
     */
    public void sendKeys(By locator, CharSequence... keysToSend) {
        find(locator).sendKeys(keysToSend);
    }

    /**
     * Get the text of the element
     *
     * @param locator locator of the element
     * @return String
     */
    public String getText(By locator) {
        waitVisibility(locator);
        return find(locator).getText();
    }

    /**
     * Get the attribute of the element
     *
     * @param locator   locator of the element
     * @param attribute attribute of the element
     * @param waitType  type of the wait
     * @return String
     */
    public String getAttribute(By locator, String attribute, String... waitType) {
        waitAccordingToType(locator, waitType);
        return find(locator).getAttribute(attribute);
    }

    /**
     * Add wait according to the type
     *
     * @param locator  locator of the element
     * @param waitType type of the wait
     */
    protected void waitAccordingToType(By locator, String... waitType) {
        String waitTypeString = waitType.length > 0 ? waitType[0] : "presence";
        switch (waitTypeString) {
            case "visibility":
                waitVisibility(locator);
                break;
            case "clickable":
                waitForElementClickable(locator);
                break;
            case "visibilityOfAllElements":
                waitForVisibilityOfAllElements(locator);
                break;
        }
    }

    /**
     * Navigates to the previous page in the browser history.
     */
    protected void backPage(){
        driver.navigate().back();
    }

    /**
     * Get the attribute of the element by index in the list
     *
     * @param locator   locator of the elements
     * @param attribute attribute of the element
     * @param waitType  type of the wait
     * @return String
     */
    public List<String> getAttributes(By locator,  String attribute, String... waitType) {
        waitAccordingToType(locator, waitType);
        List<String> attributesValueList = new ArrayList<>();
        for(WebElement element : findElements(locator))
        {
            attributesValueList.add(element.getDomAttribute(attribute));
        }
        return attributesValueList;
    }

    /**
     * Executes a JavaScript script in the context of the current browser session.
     *
     * @param script The JavaScript code to execute.
     * @param args   Optional arguments to pass to the script.
     * @return The result of the script execution, if any.
     */
    public Object executeJavaScript(String script, Object... args) {
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            return jsExecutor.executeScript(script, args);
        } else {
            throw new UnsupportedOperationException("Driver does not support JavaScript execution.");
        }
    }

}
