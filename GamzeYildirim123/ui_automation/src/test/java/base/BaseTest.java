package base;

import driver.DriverProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

import static driver.Browser.CHROME;

public abstract class BaseTest {
    protected DriverProvider driverProvider;
    protected WebDriver driver;

    @BeforeAll
    public void setUp() {
        driverProvider = new DriverProvider(CHROME);

        driver = driverProvider.getDriver();

        driver.manage().window().maximize();
        driver.get("https://www.trendyol.com/");
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
