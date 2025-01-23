package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class DriverProvider {
    private WebDriver driver;

    public DriverProvider(Browser browser) {
        initializeDriver(browser);
    }

    private void initializeDriver(Browser browser) {
        switch (browser) {
            case CHROME -> {
                System.out.println("Initializing ChromeDriver...");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
            }
            case FIREFOX -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--disable-popup-blocking");
                firefoxOptions.addPreference("network.proxy.allow_hijacking_localhost", true);
                firefoxOptions.addPreference("security.fileuri.strict_origin_policy", false);
                firefoxOptions.addPreference("security.csp.enable", false);
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                firefoxOptions.addPreference("dom.push.enabled", false);
                driver = new FirefoxDriver(firefoxOptions);
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
