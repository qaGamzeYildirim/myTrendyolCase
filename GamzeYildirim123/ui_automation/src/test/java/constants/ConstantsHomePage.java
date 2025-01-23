package constants;

import org.openqa.selenium.By;

public class ConstantsHomePage {
    public static final By LOGO = By.id("logo");
    public static final By LOGIN = By.cssSelector(".user-login-container");
    public static final By LOGIN_BUTTON = By.cssSelector(".login-button");
    public static final By SIGN_UP_BUTTON = By.cssSelector(".signup-button");
    public static final By CLOSE_POPUP = By.className("modal-close");
    public static final By MY_ACCOUNT = By.cssSelector(".account-user .link-text");
    public static final By HOME_PAGE = By.className("homepage-wrapper");
    public static final By SEARCH_TEXTBOX = By.cssSelector("#autoCompleteAppWrapper div div div input");
    public static final By FAVORITES = By.className("account-favorites");
    public static final By CATEGORIES = By.xpath("//a[contains(@class,'category-header')]");
}
