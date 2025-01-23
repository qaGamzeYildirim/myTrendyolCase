package test.register;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import page.HomePage;
import page.RegisterPage;
import propManager.PropertiesReader;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCheckRegister extends BaseTest {

    Map<String, String> propertiesMap = PropertiesReader.readPropertiesAsMap();

    @Test
    public void testCheckRegisterWithoutCaptcha() {
        HomePage homePage = new HomePage(driverProvider);
        homePage.clickSignUpButton();
        RegisterPage registerPage = new RegisterPage(driverProvider);
        registerPage.checkPasswordInfoText();
        registerPage.setEMail(propertiesMap.get("e_mail_register"));
        registerPage.setPassword(propertiesMap.get("password_register"));
        registerPage.checkChangeGender();
        registerPage.checkAndClickPrivacyCheckbox();
        registerPage.checkAndClickMarketingCheckbox();
        registerPage.checkAndClickPersonalCheckbox();
        registerPage.checkContractTextAndClickContract();
        registerPage.checkInfo();
    }
}
