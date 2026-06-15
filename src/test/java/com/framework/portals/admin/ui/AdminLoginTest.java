package com.framework.portals.admin.ui;

import com.framework.core.base.BasePortalTest;
import com.framework.core.config.Portal;
import com.framework.portals.admin.pages.AdminDashboardPage;
import com.framework.portals.admin.pages.AdminLoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Admin Portal - Authentication")
@Tag("admin")
@Tag("ui")
class AdminLoginTest extends BasePortalTest {

    AdminLoginTest() {
        super(Portal.ADMIN);
    }

    @Test
    @DisplayName("Admin login with valid credentials shows success page")
    @Story("Login")
    void loginWithValidCredentials() {
        AdminDashboardPage dashboard = new AdminLoginPage()
                .open()
                .loginWithDefaultCredentials();

        assertThat(dashboard.isLoaded())
                .as("Success header should be visible after admin login")
                .isTrue();
    }

    @Test
    @DisplayName("Admin login with invalid credentials shows error")
    @Story("Login")
    void loginWithInvalidCredentials() {
        AdminLoginPage loginPage = new AdminLoginPage()
                .open()
                .enterUsername("wronguser")
                .enterPassword("wrongpassword");

        loginPage.clickLogin();

        assertThat(loginPage.isErrorVisible())
                .as("Error message should appear for invalid admin credentials")
                .isTrue();
    }

    @Test
    @DisplayName("Admin portal navigates to correct base URL")
    @Story("Login")
    void loginPageUrlIsCorrect() {
        AdminLoginPage loginPage = new AdminLoginPage().open();

        assertThat(loginPage.getCurrentUrl())
                .contains(portalConfig.getBaseUrl());
    }
}
