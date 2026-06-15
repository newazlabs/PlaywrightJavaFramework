package com.framework.portals.orangehrm.ui;

import com.framework.core.base.BasePortalTest;
import com.framework.core.config.Portal;
import com.framework.portals.orangehrm.pages.OrangeHrmDashboardPage;
import com.framework.portals.orangehrm.pages.OrangeHrmLoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("OrangeHRM - Authentication")
@Tag("orangehrm")
@Tag("ui")
class OrangeHrmLoginTest extends BasePortalTest {

    OrangeHrmLoginTest() {
        super(Portal.ORANGEHRM);
    }

    @Test
    @DisplayName("Admin login with valid credentials loads dashboard")
    @Story("Login")
    void loginWithValidCredentials() {
        OrangeHrmDashboardPage dashboard = new OrangeHrmLoginPage()
                .open()
                .loginWithDefaultCredentials();

        assertThat(dashboard.isLoaded())
                .as("OrangeHRM main navigation should be visible after login")
                .isTrue();
    }

    @Test
    @DisplayName("Login with invalid credentials shows error alert")
    @Story("Login")
    void loginWithInvalidCredentials() {
        OrangeHrmLoginPage loginPage = new OrangeHrmLoginPage()
                .open()
                .enterUsername("invalid_user")
                .enterPassword("wrong_pass");

        loginPage.clickLogin();

        assertThat(loginPage.isErrorVisible())
                .as("Error alert should appear for invalid credentials")
                .isTrue();

        assertThat(loginPage.getErrorMessage())
                .containsIgnoringCase("Invalid credentials");
    }

    @Test
    @DisplayName("Login page loads at correct URL")
    @Story("Login")
    void loginPageUrlIsCorrect() {
        OrangeHrmLoginPage loginPage = new OrangeHrmLoginPage().open();

        assertThat(loginPage.getCurrentUrl())
                .contains("orangehrmlive.com");
    }
}
