package test.login;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import page.HomePage;
import page.LoginPage;
import propManager.PropertiesReader;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestSuccessfullyLogin extends BaseTest {

    Map<String, String> propertiesMap = PropertiesReader.readPropertiesAsMap();

    @Test
    public void testCheckSuccessfullyLogin() {
        HomePage homePage = new HomePage(driverProvider);
        homePage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driverProvider);
        loginPage.setEMail(propertiesMap.get("e_mail"));
        loginPage.setPassword(propertiesMap.get("password"));
        loginPage.checkForgotPassword();
        loginPage.checkSocialMedia();
        loginPage.checkInfo();
        loginPage.clickLoginButton();
        homePage.isLogged();
    }
}
