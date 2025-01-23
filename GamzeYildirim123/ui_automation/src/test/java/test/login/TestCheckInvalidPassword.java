package test.login;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import page.HomePage;
import page.LoginPage;
import propManager.PropertiesReader;

import java.util.Map;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TestCheckInvalidPassword extends BaseTest {
    Map<String, String> propertiesMap = PropertiesReader.readPropertiesAsMap();
    private static final String E_MAIL_ERROR_TEXT = "E-posta adresiniz ve/veya şifreniz hatalı.";
    private static final String INVALID_PASSWORD = "aaaaaaaaa";

    @Test
    public void testCheckLoginWithEmptyEMailAndPassword() {
        HomePage homePage = new HomePage(driverProvider);
        homePage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driverProvider);
        loginPage.setEMail(propertiesMap.get("e_mail"));
        loginPage.setPassword(INVALID_PASSWORD);
        loginPage.clickLoginButton();
        loginPage.checkError(E_MAIL_ERROR_TEXT);
    }
}
