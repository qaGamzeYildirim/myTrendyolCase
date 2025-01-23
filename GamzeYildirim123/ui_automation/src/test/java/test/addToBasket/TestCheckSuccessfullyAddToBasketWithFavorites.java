package test.addToBasket;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import page.FavoritesPage;
import page.HomePage;
import page.LoginPage;
import page.ProductListingPage;
import propManager.PropertiesReader;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCheckSuccessfullyAddToBasketWithFavorites extends BaseTest {
    private static final String QUERY = "Gömlek";

    Map<String, String> propertiesMap = PropertiesReader.readPropertiesAsMap();

    @Test
    public void testCheckAddToFavoritesAndAddToBasket() {
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
        productListingPage.addToFavoriteFirstProduct();
        homePage.clickFavorites();
        FavoritesPage favoritesPage = new FavoritesPage(driverProvider);
        favoritesPage.addToBasket();
        homePage.clickFavorites();
        favoritesPage.removeFavoriteProduct();

    }
}
