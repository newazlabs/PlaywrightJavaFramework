package com.framework.portals.saucedemo.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class SauceDemoInventoryPage extends BasePage {

    private static final String INVENTORY_LIST   = ".inventory_list";
    private static final String INVENTORY_ITEMS  = ".inventory_item";
    private static final String CART_BADGE       = ".shopping_cart_badge";
    private static final String CART_LINK        = ".shopping_cart_link";
    private static final String SORT_DROPDOWN    = "select.product_sort_container";
    private static final String BURGER_MENU      = "#react-burger-menu-btn";
    private static final String LOGOUT_LINK      = "#logout_sidebar_link";

    // Individual product add-to-cart buttons
    private static final String ADD_BACKPACK     = "[data-test='add-to-cart-sauce-labs-backpack']";
    private static final String ADD_BIKE_LIGHT   = "[data-test='add-to-cart-sauce-labs-bike-light']";
    private static final String ADD_BOLT_TSHIRT  = "[data-test='add-to-cart-sauce-labs-bolt-t-shirt']";
    public SauceDemoInventoryPage() {
        super(Portal.SAUCEDEMO);
    }

    public boolean isLoaded() {
        waitForVisible(INVENTORY_LIST);
        return isVisible(INVENTORY_LIST);
    }

    public int getProductCount() {
        waitForVisible(INVENTORY_LIST);
        return page.locator(INVENTORY_ITEMS).count();
    }

    public SauceDemoInventoryPage addBackpackToCart() {
        click(ADD_BACKPACK);
        return this;
    }

    public SauceDemoInventoryPage addBikeLightToCart() {
        click(ADD_BIKE_LIGHT);
        return this;
    }

    public SauceDemoInventoryPage addBoltTshirtToCart() {
        click(ADD_BOLT_TSHIRT);
        return this;
    }

    public String getCartBadgeCount() {
        waitForVisible(CART_BADGE);
        return getText(CART_BADGE);
    }

    public boolean isCartBadgeVisible() {
        return isVisible(CART_BADGE);
    }

    public SauceDemoCartPage goToCart() {
        click(CART_LINK);
        return new SauceDemoCartPage();
    }

    public SauceDemoInventoryPage sortBy(String option) {
        waitForVisible(INVENTORY_LIST);
        waitForVisible(SORT_DROPDOWN);
        selectOption(SORT_DROPDOWN, option);
        return this;
    }

    public String getFirstProductName() {
        waitForVisible(INVENTORY_LIST);
        return page.locator(".inventory_item_name").first().innerText();
    }

    public void logout() {
        click(BURGER_MENU);
        waitForVisible(LOGOUT_LINK);
        click(LOGOUT_LINK);
    }
}
