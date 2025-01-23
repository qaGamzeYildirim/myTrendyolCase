package page;

import base.PageBase;
import driver.DriverProvider;
import static constants.ConstantsLoginPage.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginPage extends PageBase {

    private static final String HELLO_TEXT = "Merhaba,";
    private static final String LOGIN_DESCRIPTION_TEXT = "Trendyol’a giriş yap veya hesap oluştur, indirimleri kaçırma!";
    private static final String E_MAIL_TEXT = "E-Posta";
    private static final String PASSWORD_TEXT = "Şifre";
    private static final String ORDER_ENTRY_INFO_TEXT = "Üye olmadan verilen siparişlerin takibi için tıklayınız .";

    public LoginPage(DriverProvider driverProvider) {
        super(driverProvider);
    }

    @Override
    protected void check() {
        assertTrue(isVisibilityOfElementLocated(LOGIN_PAGE));
        assertEquals(HELLO_TEXT, getText(LOGIN_PAGE_TITLE));
        assertEquals(LOGIN_DESCRIPTION_TEXT,
                getText(LOGIN_PAGE_DESCRIPTION));
        assertEquals("true", getAttribute(LOGIN_BUTTON_HEADER, "aria-pressed"));
    }

    public LoginPage setEMail(String eMail) {
        assertEquals(E_MAIL_TEXT, getText(E_MAIL_LABEL));
        click(E_MAIL_TEXTBOX);
        sendKeys(E_MAIL_TEXTBOX, eMail);
        return this;

    }

    public LoginPage setPassword(String password) {
        assertEquals(PASSWORD_TEXT, getText(PASSWORD_LABEL));
        click(PASSWORD_TEXTBOX);
        sendKeys(PASSWORD_TEXTBOX, password);
        return this;
    }

    public LoginPage checkForgotPassword() {
        waitForVisibilityOfAllElements(FORGOT_PASSWORD_BUTTON);
        return this;
    }

    public LoginPage clickForgotPassword() {
        click(FORGOT_PASSWORD_BUTTON_LINK);
        return this;
    }

    public LoginPage checkForgotPasswordPage() {
        waitVisibility(FORGOT_PASSWORD_PAGE);
        return this;
    }

    public LoginPage checkInfoPage() {
        waitVisibility(SUPPORT_CONTAINER);
        return this;
    }

    public LoginPage checkSocialMedia() {
        waitForVisibilityOfAllElements(SOCIAL_ACCOUNT_LOGIN);
        return this;
    }

    public LoginPage clickLoginButton() {
        click(SUBMIT);
        return this;

    }

    public LoginPage clickInfo() {
        click(INFO_CLICKABLE_TEXT);
        return this;

    }

    public LoginPage checkInfo() {
        assertEquals(getText(INFO).replaceAll("\\s+", " "), ORDER_ENTRY_INFO_TEXT);
        return this;
    }

    public LoginPage checkError(String errorText) {
        assertEquals(getText(LOGIN_ERROR), errorText);
        return this;
    }

    public void backToPreviousPage() {
        backPage();
    }

}
