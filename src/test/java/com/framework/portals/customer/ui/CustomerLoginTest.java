package com.framework.portals.customer.ui;

import com.framework.core.base.BasePortalTest;
import com.framework.core.config.Portal;
import com.framework.portals.customer.pages.CustomerHomePage;
import com.framework.portals.customer.pages.CustomerLoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Customer Portal - Authentication")
@Tag("customer")
@Tag("ui")
class CustomerLoginTest extends BasePortalTest {

    CustomerLoginTest() {
        super(Portal.CUSTOMER);
    }

    @Test
    @DisplayName("Customer login with valid credentials lands on secure area")
    @Story("Login")
    void loginWithValidCredentials() {
        CustomerHomePage homePage = new CustomerLoginPage()
                .open()
                .loginWithDefaultCredentials();

        assertThat(homePage.isLoaded())
                .as("Secure area should be visible after customer login")
                .isTrue();
    }

    @Test
    @DisplayName("Customer login with wrong credentials shows invalid message")
    @Story("Login")
    void loginWithWrongPassword() {
        CustomerLoginPage loginPage = new CustomerLoginPage()
                .open()
                .enterUsername("baduser")
                .enterPassword("badpassword");

        loginPage.clickLogin();

        assertThat(loginPage.isErrorVisible())
                .as("Error flash should appear for wrong credentials")
                .isTrue();
    }

    @Test
    @DisplayName("Customer portal URL is different from admin portal URL")
    @Story("Portal Isolation")
    void customerAndAdminUrlsAreDifferent() {
        String customerUrl = portalConfig.getBaseUrl();
        String adminUrl    = new com.framework.core.config.PortalConfigManager(Portal.ADMIN).getBaseUrl();

        assertThat(customerUrl)
                .as("Customer and admin portals should have different base URLs")
                .isNotEqualTo(adminUrl);
    }
}
