package test.register;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import page.HomePage;
import page.RegisterPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCheckRegisterWithEmptyEMailAndPassword extends BaseTest {
    private static final String ERROR_TEXT = "E-posta ve şifrenizi giriniz.";

    @Test
    public void testCheckRegisterWithoutCaptcha() {
        HomePage homePage = new HomePage(driverProvider);
        homePage.clickSignUpButton();
        RegisterPage registerPage = new RegisterPage(driverProvider);
        registerPage.checkPasswordInfoText();
        registerPage.checkChangeGender();
        registerPage.checkAndClickPrivacyCheckbox();
        registerPage.checkAndClickMarketingCheckbox();
        registerPage.checkAndClickPersonalCheckbox();
        registerPage.checkContractTextAndClickContract();
        registerPage.checkInfo();
        registerPage.clickSignInButton();
        registerPage.checkError(ERROR_TEXT);
    }
}
