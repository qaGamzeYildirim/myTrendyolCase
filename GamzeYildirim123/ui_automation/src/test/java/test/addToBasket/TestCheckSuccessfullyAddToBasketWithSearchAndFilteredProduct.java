package test.addToBasket;

import base.BaseTest;;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import page.HomePage;
import page.LoginPage;
import page.ProductListingPage;
import propManager.PropertiesReader;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCheckSuccessfullyAddToBasketWithSearchAndFilteredProduct extends BaseTest {
    private static final String QUERY = "Oyuncu Bilgisayarı";
    private static final String BRAND = "MONSTER";
    private static final int MIN_PRICE = 3000;
    private static final int MAX_PRICE = 10000;

    Map<String, String> propertiesMap = PropertiesReader.readPropertiesAsMap();

    @Test
    public void testCheckAddToBasket() {
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
        homePage.clickSearchTextBox()
                .searchQuery(QUERY);
        ProductListingPage productListingPage = new ProductListingPage(driverProvider);
        productListingPage.chooseBrand(BRAND);
        productListingPage.filterPrice(MAX_PRICE, MIN_PRICE);
        productListingPage.clickFistAddToBasketButton();
    }
}
