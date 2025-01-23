package page;

import base.PageBase;
import driver.DriverProvider;

import static constants.ConstantsLoginPage.*;
import static constants.ConstantsRegister.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterPage extends PageBase {

    private static final String HELLO_TEXT = "Merhaba,";
    private static final String LOGIN_DESCRIPTION_TEXT = "Trendyol’a giriş yap veya hesap oluştur, indirimleri kaçırma!";
    private static final String E_MAIL_TEXT = "E-Posta";
    private static final String PASSWORD_TEXT = "Şifre";
    private static final String PASSWORD_INFO_TEXT = "Şifreniz en az 10 karakter olmalı. 1 büyük harf, 1 küçük harf ve rakam içermelidir.";
    private static final String GENDER_TITLE_TEXT = "Cinsiyet (Opsiyonel)";
    private static final String PRIVACY_TEXT = "Tarafıma avantajlı tekliflerin sunulabilmesi amacıyla kişisel verilerimin işlenmesine ve paylaşılmasınaaçık rıza veriyorum.";
    private static final String MARKETING_TEXT = "Tarafıma elektronik ileti gönderilmesini kabul ediyorum.";
    private static final String PERSONAL_TEXT = "Kişisel verilerimin işlenmesine yönelik aydınlatma metnini okudum ve anladım.";
    private static final String CONTRACT_TEXT = "Üye Ol’a basarakÜyelik Koşulları'nı kabul ediyorum.";
    private static final String ORDER_ENTRY_INFO_TEXT = "Üye olmadan verilen siparişlerin takibi için tıklayınız .";


    public RegisterPage(DriverProvider driverProvider) {
        super(driverProvider);
    }

    @Override
    protected void check() {
        assertTrue(isVisibilityOfElementLocated(LOGIN_PAGE));
        assertEquals(HELLO_TEXT, getText(LOGIN_PAGE_TITLE));
        assertEquals(LOGIN_DESCRIPTION_TEXT,
                getText(LOGIN_PAGE_DESCRIPTION));
        assertEquals(getAttribute(SIGN_UP_HEADER, "aria-pressed"), "true");
    }

    public RegisterPage checkPasswordInfoText() {
        assertEquals(PASSWORD_INFO_TEXT, getText(PASSWORD_INFO));
        return this;
    }

    public RegisterPage setEMail(String eMail) {
        assertEquals(E_MAIL_TEXT, getText(E_MAIL_LABEL));
        click(REGISTER_E_MAIL);
        sendKeys(REGISTER_E_MAIL, eMail);
        return this;
    }

    public RegisterPage setPassword(String password) {
        assertEquals(PASSWORD_TEXT, getText(PASSWORD_LABEL));
        click(REGISTER_PASSWORD);
        sendKeys(REGISTER_PASSWORD, password);
        return this;
    }

    public RegisterPage checkChangeGender() {
        assertEquals(GENDER_TITLE_TEXT, getText(GENDER_TITLE));
        assertEquals("false", getAttribute(GENDER_FEMALE, "aria-pressed"));
        assertEquals("false", getAttribute(GENDER_MALE, "aria-pressed"));
        click(GENDER_FEMALE);
        assertEquals("true", getAttribute(GENDER_FEMALE, "aria-pressed"));
        assertEquals("false", getAttribute(GENDER_MALE, "aria-pressed"));
        click(GENDER_MALE);
        assertEquals("true", getAttribute(GENDER_MALE, "aria-pressed"));
        assertEquals("false", getAttribute(GENDER_FEMALE, "aria-pressed"));
        return this;
    }

    public RegisterPage checkAndClickPrivacyCheckbox() {
        assertEquals(PRIVACY_TEXT, getText(CHECKBOX_PRIVACY));
        click(CHECKBOX_PRIVACY);
        return this;
    }

    public RegisterPage checkAndClickMarketingCheckbox() {
        assertEquals(MARKETING_TEXT, getText(CHECKBOX_MARKETING));
        click(CHECKBOX_MARKETING);
        return this;
    }

    public RegisterPage checkAndClickPersonalCheckbox() {
        assertEquals(PERSONAL_TEXT, getText(CHECKBOX_PERSONAL));
        click(CHECKBOX_PERSONAL);
        return this;
    }

    public RegisterPage checkContractTextAndClickContract() {
        assertEquals(CONTRACT_TEXT, getText(SIGN_UP_CONTRACT));
        click(SIGN_UP_CONTRACT_LINK);
        assertTrue(isVisibilityOfElementLocated(CONTRACT_POP_UP));
        click(CONTRACT_POP_UP_CLOSE_BUTTON);
        return this;
    }

    public RegisterPage checkInfo() {
        assertEquals(getText(INFO).replaceAll("\\s+", " "), ORDER_ENTRY_INFO_TEXT);
        return this;
    }

    public RegisterPage clickSignInButton() {
        click(SUBMIT);
        return this;
    }

    public RegisterPage checkError(String errorText) {
        assertEquals(getText(LOGIN_ERROR), errorText);
        return this;
    }

}
