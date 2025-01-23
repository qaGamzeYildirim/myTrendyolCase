package constants;

import org.openqa.selenium.By;

public class ConstantsFavoritesPage {
    public static final By FAVORITES_PAGE = By.id("account-gw-favorites");
    public static final By FAVORITES_ADD_TO_BASKET_BUTTON = By.xpath("//div[contains(@class,'basket-button')]");
    public static final By PRODUCT_DESCRIPTION = By.className("prdct-desc-cntnr-name");
    public static final By REMOVE_FAVORITE = By.className("i-close");
}
