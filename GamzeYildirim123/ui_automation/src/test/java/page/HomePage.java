package page;

import base.PageBase;
import driver.DriverProvider;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

import static constants.ConstantsCategory.*;
import static constants.ConstantsHomePage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePage extends PageBase {

    public static final String MY_ACCOUNT_TEXT = "Hesabım";

    public HomePage(DriverProvider driverProvider) {
        super(driverProvider);
    }

    @Override
    protected void check() {
        assertTrue(isVisibilityOfElementLocated(LOGO));
    }

    public HomePage clickLoginButton() {

        click(CLOSE_POPUP, "clickable");
        hoverTo(LOGIN);
        click(LOGIN_BUTTON, "clickable");
        return this;
    }

    public HomePage clickSignUpButton() {

        click(CLOSE_POPUP, "clickable");
        hoverTo(LOGIN);
        click(SIGN_UP_BUTTON, "clickable");
        return this;
    }

    public HomePage isLogged() {
        waitVisibility(HOME_PAGE);
        assertEquals(getText(MY_ACCOUNT), MY_ACCOUNT_TEXT);
        return this;
    }

    public HomePage clickSearchTextBox() {
        click(SEARCH_TEXTBOX, "clickable");
        return this;
    }

    public HomePage searchQuery(String query) {
        click(SEARCH_TEXTBOX, "clickable");
        sendKeys(SEARCH_TEXTBOX, query);
        sendKeys(SEARCH_TEXTBOX, Keys.ENTER);
        return this;
    }

    public HomePage clickFavorites() {
        click(FAVORITES, "clickable");
        return this;
    }

    public HomePage clickCategoryTabAndCheckImage() {
        List<WebElement> categoryList = findElements(CATEGORIES);
        for (int i = 0; i < categoryList.size() - 2; ++i) {
            findElements(CATEGORIES).get(i).click();
            if (i == 5) {
                click(FIRST_COMPONENT_FOR_COSMETIC, "clickable");
            } else {
                click(FIRST_COMPONENT, "clickable");
            }

            List<WebElement> imageList = findElements(IMAGES).subList(0, 4);
            for (WebElement image : imageList) {
                boolean isImageLoaded = (Boolean) executeJavaScript(
                        "return arguments[0].complete && " +
                                "typeof arguments[0].naturalWidth != 'undefined' && " +
                                "arguments[0].naturalWidth > 0", image);
                assertTrue(isImageLoaded, "Image not loaded: " + image.getAttribute("src"));
            }
            backPage();
            backPage();
        }
        return this;
    }
}
