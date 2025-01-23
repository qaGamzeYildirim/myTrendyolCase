package test.login;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import page.HomePage;
import page.LoginPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestLoginWithEmptyPassword extends BaseTest {

    private static final String PASSWORD_ERROR_TEXT = "Lütfen şifrenizi giriniz.";

    @Test
    public void testCheckLoginWithEmptyEMailAndPassword() {
        HomePage homePage = new HomePage(driverProvider);
        homePage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driverProvider);
        loginPage.setEMail("gamzeyildirim000@outlook.com.tr");
        loginPage.clickLoginButton();
        loginPage.checkError(PASSWORD_ERROR_TEXT);

    }
}
