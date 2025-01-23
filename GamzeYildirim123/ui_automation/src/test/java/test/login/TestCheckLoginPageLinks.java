package test.login;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import page.HomePage;
import page.LoginPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCheckLoginPageLinks extends BaseTest {

    @Test
    public void testCheckLinks() {
        HomePage homePage = new HomePage(driverProvider);
        homePage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driverProvider);
        loginPage.clickForgotPassword();
        loginPage.checkForgotPasswordPage();
        loginPage.backToPreviousPage();
        loginPage.checkInfo();
        loginPage.clickInfo();
        loginPage.checkInfoPage();
        loginPage.backToPreviousPage();
    }

}
