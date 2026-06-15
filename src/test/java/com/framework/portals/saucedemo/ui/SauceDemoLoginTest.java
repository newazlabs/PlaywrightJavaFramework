package com.framework.portals.saucedemo.ui;

import com.framework.core.base.BasePortalTest;
import com.framework.core.config.Portal;
import com.framework.core.config.PortalConfigManager;
import com.framework.portals.saucedemo.pages.SauceDemoInventoryPage;
import com.framework.portals.saucedemo.pages.SauceDemoLoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("SauceDemo - Authentication")
@Tag("saucedemo")
@Tag("ui")
class SauceDemoLoginTest extends BasePortalTest {

    SauceDemoLoginTest() {
        super(Portal.SAUCEDEMO);
    }

    @Test
    @DisplayName("Standard user login loads inventory page")
    @Story("Login")
    void standardUserLogin() {
        SauceDemoInventoryPage inventory = new SauceDemoLoginPage()
                .open()
                .loginWithDefaultCredentials();

        assertThat(inventory.isLoaded())
                .as("Inventory list should be visible after standard_user login")
                .isTrue();
    }

    @Test
    @DisplayName("Inventory page shows 6 products")
    @Story("Login")
    void inventoryPageShowsProducts() {
        SauceDemoInventoryPage inventory = new SauceDemoLoginPage()
                .open()
                .loginWithDefaultCredentials();

        assertThat(inventory.getProductCount())
                .as("SauceDemo should show exactly 6 products")
                .isEqualTo(6);
    }

    @Test
    @DisplayName("Locked out user sees error message")
    @Story("Login - Negative")
    void lockedOutUserSeesError() {
        PortalConfigManager cfg = new PortalConfigManager(Portal.SAUCEDEMO);
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage()
                .open()
                .enterUsername(cfg.get("credentials.locked.username"))
                .enterPassword(cfg.get("credentials.password"));

        loginPage.clickLogin();

        assertThat(loginPage.isErrorVisible()).isTrue();
        assertThat(loginPage.getErrorMessage())
                .containsIgnoringCase("locked out");
    }

    @Test
    @DisplayName("Login with wrong password shows error")
    @Story("Login - Negative")
    void wrongPasswordShowsError() {
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage()
                .open()
                .enterUsername("standard_user")
                .enterPassword("wrong_password");

        loginPage.clickLogin();

        assertThat(loginPage.isErrorVisible()).isTrue();
        assertThat(loginPage.getErrorMessage())
                .containsIgnoringCase("Username and password do not match");
    }
}
