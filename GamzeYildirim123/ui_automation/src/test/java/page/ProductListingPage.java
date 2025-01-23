package page;

import base.PageBase;
import driver.DriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static constants.ConstantsProductListingPage.*;
import static constants.ConstantsAddToCart.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductListingPage extends PageBase {
    public ProductListingPage(DriverProvider driverProvider) {
        super(driverProvider);
    }

    @Override
    protected void check() {
        assertTrue(isVisibilityOfElementLocated(AGGREGATIONS));
        assertTrue(isVisibilityOfElementLocated(PRODUCT_LIST_FIELD));
    }

    public ProductListingPage chooseBrand(String brand) {
        click(AGGREGATION_BRAND_SEARCH);
        sendKeys(AGGREGATION_BRAND_SEARCH, brand);
        click(By.xpath(FILTERED_AGGREGATION_BRAND_RESULT_CHECKBOX.replace("{}", brand)), "clickable");
        pause(1000);
        assertTrue(getAttributes(PRODUCTS_TITLES, "title").contains(brand));
        return this;
    }

    public ProductListingPage filterPrice(int max, int min) {
        click(AGGREGATION_PRICE);
        click(AGGREGATION_PRICE_MIN_TEXTBOX);
        sendKeys(AGGREGATION_PRICE_MIN_TEXTBOX, String.valueOf(min));
        click(AGGREGATION_PRICE_MAX_TEXTBOX);
        sendKeys(AGGREGATION_PRICE_MAX_TEXTBOX, String.valueOf(max));
        click(AGGREGATION_PRICE_SEARCH_BUTTON, "clickable");
        pause(1000);
        List<Integer> integerList = new ArrayList<>();
        List<String> stringPriceList = new ArrayList<>();
        for (WebElement priceElement : findElements(PRODUCT_LIST_PRICES)) {
            stringPriceList.add(priceElement.getText());
        }
        for (String price : stringPriceList) {
            String withoutTl = price.replace("TL", "").trim();
            try {
                String normalizedPrice = withoutTl.replace(".", "").replace(",", ".");
                integerList.add((int) (Double.parseDouble(normalizedPrice)));
            } catch (NumberFormatException e) {
                System.out.println("Invalid format: " + withoutTl);
            }
        }
        for (Integer number : integerList) {
            assertTrue(number >= min && number <= max);
        }
        return this;
    }

    public ProductListingPage clickFistAddToBasketButton() {
        String firstProduct = find(FIRST_PRODUCT_DESCRIPTION).getText().trim();
        click(FIRST_PRODUCT_ADD_TO_CART_BUTTON, "clickable");
        click(GO_TO_BASKET_BUTTON, "clickable");
        assertEquals(firstProduct.replaceAll("\\s+", " "), find(ADDED_TO_BASKET_PRODUCT).getText());
        click(ADD_TO_CART_TOOLTIP, "clickable");
        click(REMOVE_BUTTON);
        assertTrue(isVisibilityOfElementLocated(REMOVED_ITEM_INFORMATION));
        return this;
    }

    public ProductListingPage addToFavoriteFirstProduct() {
        click(FIRST_PRODUCT_ADD_TO_FAVORITE_BUTTON, "clickable");
        return this;
    }

}
