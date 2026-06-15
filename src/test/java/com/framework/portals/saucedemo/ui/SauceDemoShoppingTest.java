package com.framework.portals.saucedemo.ui;

import com.framework.core.base.BasePortalTest;
import com.framework.core.config.Portal;
import com.framework.portals.saucedemo.pages.SauceDemoCartPage;
import com.framework.portals.saucedemo.pages.SauceDemoCheckoutPage;
import com.framework.portals.saucedemo.pages.SauceDemoInventoryPage;
import com.framework.portals.saucedemo.pages.SauceDemoLoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("SauceDemo - Shopping")
@Tag("saucedemo")
@Tag("ui")
class SauceDemoShoppingTest extends BasePortalTest {

    SauceDemoShoppingTest() {
        super(Portal.SAUCEDEMO);
    }

    private SauceDemoInventoryPage loginAsStandardUser() {
        return new SauceDemoLoginPage().open().loginWithDefaultCredentials();
    }

    @Test
    @DisplayName("Add single item to cart shows badge count of 1")
    @Story("Cart")
    void addSingleItemToCartShowsBadge() {
        SauceDemoInventoryPage inventory = loginAsStandardUser();
        inventory.addBackpackToCart();

        assertThat(inventory.getCartBadgeCount())
                .as("Cart badge should show 1 after adding one item")
                .isEqualTo("1");
    }

    @Test
    @DisplayName("Add multiple items increments cart count correctly")
    @Story("Cart")
    void addMultipleItemsIncrementsCount() {
        SauceDemoInventoryPage inventory = loginAsStandardUser();
        inventory.addBackpackToCart()
                 .addBikeLightToCart()
                 .addBoltTshirtToCart();

        assertThat(inventory.getCartBadgeCount())
                .as("Cart badge should show 3 after adding three items")
                .isEqualTo("3");
    }

    @Test
    @DisplayName("Cart page shows added item")
    @Story("Cart")
    void cartPageShowsAddedItem() {
        SauceDemoCartPage cart = loginAsStandardUser()
                .addBackpackToCart()
                .goToCart();

        assertThat(cart.isLoaded()).isTrue();
        assertThat(cart.isItemInCart("Sauce Labs Backpack")).isTrue();
        assertThat(cart.getCartItemCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Checkout requires first name — shows error when missing")
    @Story("Checkout")
    void checkoutRequiresFirstName() {
        SauceDemoCheckoutPage checkout = loginAsStandardUser()
                .addBackpackToCart()
                .goToCart()
                .proceedToCheckout();

        assertThat(checkout.isOnStepOne()).isTrue();

        checkout.fillCustomerInfo("", "TestUser", "90210")
                .clickContinue();

        assertThat(checkout.isErrorVisible()).isTrue();
        assertThat(checkout.getErrorMessage()).containsIgnoringCase("First Name");
    }

    @Test
    @DisplayName("Complete checkout flow — order confirmed")
    @Story("Checkout")
    void completeCheckoutFlow() {
        SauceDemoCheckoutPage checkout = loginAsStandardUser()
                .addBackpackToCart()
                .goToCart()
                .proceedToCheckout();

        checkout.fillCustomerInfo("John", "Doe", "90210")
                .clickContinue();

        assertThat(checkout.isOnStepTwo()).isTrue();
        assertThat(checkout.getOrderTotal()).isNotEmpty();

        checkout.clickFinish();

        assertThat(checkout.isOnComplete()).isTrue();
        assertThat(checkout.getCompleteHeader())
                .containsIgnoringCase("Thank you for your order");
    }

    @Test
    @DisplayName("Sort by price low to high — cheapest product appears first")
    @Story("Sorting")
    void sortByPriceLowToHigh() {
        SauceDemoInventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("lohi");

        String firstProductName = inventory.getFirstProductName();

        assertThat(firstProductName)
                .as("Sauce Labs Onesie ($7.99) should be first when sorted low to high")
                .isEqualTo("Sauce Labs Onesie");
    }
}
