package page;

import base.PageBase;
import driver.DriverProvider;

import static constants.ConstantsFavoritesPage.*;
import static constants.ConstantsProductListingPage.*;
import static constants.ConstantsAddToCart.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FavoritesPage extends PageBase {
    public FavoritesPage(DriverProvider driverProvider) {
        super(driverProvider);
    }

    @Override
    protected void check() {
        assertTrue(isElementPresent(FAVORITES_PAGE));
    }

    public FavoritesPage addToBasket() {
        pause(1000);
        String firstProduct = find(PRODUCT_DESCRIPTION).getText().trim();
        pause(1000);
        click(FAVORITES_ADD_TO_BASKET_BUTTON, "clickable");
        click(GO_TO_BASKET_BUTTON, "clickable");
        assertTrue(find(ADDED_TO_BASKET_PRODUCT).getText().contains(firstProduct.replaceAll("\\s+", " ")));
        click(REMOVE_BUTTON);
        assertTrue(isVisibilityOfElementLocated(REMOVED_ITEM_INFORMATION));
        return this;
    }

    public FavoritesPage removeFavoriteProduct() {
        click(REMOVE_FAVORITE, "clickable");
        return this;
    }

}
