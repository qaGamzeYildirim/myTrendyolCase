package test.login;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import page.HomePage;
import page.LoginPage;
import propManager.PropertiesReader;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestLoginWithEmptyEmailAndPassword extends BaseTest {

    Map<String, String> propertiesMap = PropertiesReader.readPropertiesAsMap();
    private static final String E_MAIL_ERROR_TEXT = "Lütfen geçerli bir e-posta adresi giriniz.";

    @Test
    public void testCheckLoginWithEmptyEMailAndPassword() {
        HomePage homePage = new HomePage(driverProvider);
        homePage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driverProvider);
        loginPage.clickLoginButton();
        loginPage.checkError(E_MAIL_ERROR_TEXT);
        loginPage.setPassword(propertiesMap.get("password"));
        loginPage.clickLoginButton();
        loginPage.checkError(E_MAIL_ERROR_TEXT);
    }
}
