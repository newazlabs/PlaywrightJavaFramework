package com.framework.portals.saucedemo.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class SauceDemoCartPage extends BasePage {

    private static final String CART_ITEMS        = ".cart_item";
    private static final String CART_ITEM_NAME    = ".inventory_item_name";
    private static final String CHECKOUT_BUTTON   = "[data-test='checkout']";
    private static final String CONTINUE_SHOPPING = "[data-test='continue-shopping']";
    private static final String REMOVE_BACKPACK   = "[data-test='remove-sauce-labs-backpack']";

    public SauceDemoCartPage() {
        super(Portal.SAUCEDEMO);
    }

    public boolean isLoaded() {
        return getCurrentUrl().contains("/cart.html");
    }

    public int getCartItemCount() {
        return page.locator(CART_ITEMS).count();
    }

    public boolean isItemInCart(String itemName) {
        return page.locator(CART_ITEM_NAME + ":has-text('" + itemName + "')").isVisible();
    }

    public SauceDemoCartPage removeBackpack() {
        click(REMOVE_BACKPACK);
        return this;
    }

    public SauceDemoCheckoutPage proceedToCheckout() {
        click(CHECKOUT_BUTTON);
        return new SauceDemoCheckoutPage();
    }

    public SauceDemoInventoryPage continueShopping() {
        click(CONTINUE_SHOPPING);
        return new SauceDemoInventoryPage();
    }
}
