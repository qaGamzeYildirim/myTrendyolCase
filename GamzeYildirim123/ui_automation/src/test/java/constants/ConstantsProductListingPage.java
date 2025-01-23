package constants;

import org.openqa.selenium.By;

public class ConstantsProductListingPage {
    public static final By AGGREGATIONS = By.id("sticky-aggregations");
    public static final By PRODUCT_LIST_FIELD = By.className("srch-prdcts-cntnr");
    public static final By AGGREGATION_BRAND_SEARCH = By.className("fltr-srch-inpt");
    public static final String FILTERED_AGGREGATION_BRAND_RESULT_CHECKBOX = "//div[@class='fltr-item-text' and text()='{}']";
    public static final By PRODUCTS_TITLES = By.className("prdct-desc-cntnr-ttl");
    public static final By AGGREGATION_PRICE = By.xpath("//div[@class='fltr-cntnr-ttl' and text()='Fiyat']");
    public static final By AGGREGATION_PRICE_MIN_TEXTBOX = By.cssSelector(".fltr-srch-prc-rng .min");
    public static final By AGGREGATION_PRICE_MAX_TEXTBOX = By.cssSelector(".fltr-srch-prc-rng .max");
    public static final By AGGREGATION_PRICE_SEARCH_BUTTON = By.className("fltr-srch-prc-rng-srch");
    public static final By PRODUCT_LIST_PRICES = By.className("prc-box-dscntd");
    public static final By FIRST_PRODUCT_ADD_TO_CART_BUTTON = By.xpath("(//button[@class='add-to-basket-button'])[1]");
    public static final By FIRST_PRODUCT_DESCRIPTION = By.xpath("(//h3[@class='prdct-desc-cntnr-ttl-w'])[1]");
    public static final By GO_TO_BASKET_BUTTON = By.className("account-basket");
    public static final By FIRST_PRODUCT_ADD_TO_FAVORITE_BUTTON = By.xpath("(//i[@class='fvrt-btn'])[1]");
    public static final By ADD_TO_CART_TOOLTIP = By.xpath("//button[text()='Anladım']");
}
