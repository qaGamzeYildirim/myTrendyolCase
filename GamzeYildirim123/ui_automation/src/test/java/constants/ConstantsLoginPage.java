package constants;

import org.openqa.selenium.By;

public class ConstantsLoginPage {
    public static final By LOGIN_PAGE = By.id("login-register");
    public static final By LOGIN_PAGE_TITLE = By.cssSelector(".lr-title h1");
    public static final By LOGIN_PAGE_DESCRIPTION = By.cssSelector(".lr-title h3");
    public static final By LOGIN_BUTTON_HEADER = By.cssSelector(".header-buttons .left");
    public static final By SIGN_UP_HEADER = By.cssSelector(".header-buttons .right");
    public static final By E_MAIL_LABEL = By.cssSelector(".email-input .q-label");
    public static final By E_MAIL_TEXTBOX = By.id("login-email");
    public static final By PASSWORD_LABEL = By.cssSelector(".password .q-input-wrapper .q-label");
    public static final By PASSWORD_TEXTBOX = By.id("login-password-input");
    public static final By FORGOT_PASSWORD_BUTTON = By.className("forgot-password");
    public static final By SUBMIT = By.className("submit");
    public static final By INFO = By.className("guest-user-track-orders");
    public static final By SOCIAL_ACCOUNT_LOGIN = By.className("social-account-login-buttons");
    public static final By LOGIN_ERROR = By.cssSelector("#error-box-wrapper .message");
    public static final By FORGOT_PASSWORD_PAGE = By.id("change-password");
    public static final By INFO_CLICKABLE_TEXT = By.cssSelector(".guest-user-track-orders .clickable-text");
    public static final By SUPPORT_CONTAINER = By.className("support-container");
    public static final By FORGOT_PASSWORD_BUTTON_LINK = By.xpath("//span[text()='Şifremi Unuttum']");
}
