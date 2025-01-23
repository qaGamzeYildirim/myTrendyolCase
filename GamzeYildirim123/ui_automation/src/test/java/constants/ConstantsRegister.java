package constants;

import org.openqa.selenium.By;

public class ConstantsRegister {
    public static final By REGISTER_E_MAIL = By.id("register-email");
    public static final By REGISTER_PASSWORD = By.id("register-password-input");
    public static final By PASSWORD_INFO = By.className("password-info-text");
    public static final By GENDER_TITLE = By.cssSelector(".gender label");
    public static final By GENDER_FEMALE = By.className("female");
    public static final By GENDER_MALE = By.className("male");
    public static final By CHECKBOX_PRIVACY = By.className("new-co-privacy-statement-for-ty-checkbox");
    public static final By CHECKBOX_MARKETING = By.className("marketing-checkbox");
    public static final By CHECKBOX_PERSONAL = By.className("personal-checkbox");
    public static final By CAPTCHA = By.id("captcha-box-1");
    public static final By SIGN_UP_CONTRACT = By.className("contract");
    public static final By SIGN_UP_CONTRACT_LINK = By.cssSelector(".contract b");
    public static final By CONTRACT_POP_UP = By.id("contracts");
    public static final By CONTRACT_POP_UP_CLOSE_BUTTON = By.className("i-close");
}
