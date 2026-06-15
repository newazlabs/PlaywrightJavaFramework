package com.framework.portals.admin.ui;

import com.framework.core.base.BaseTest;
import com.framework.portals.admin.pages.LoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Authentication")
@Tag("ui")
class LoginTest extends BaseTest {

    @Test
    @DisplayName("Login page title contains 'Login'")
    @Story("Login")
    void loginPageTitle() {
        LoginPage loginPage = new LoginPage().open();
        assertThat(loginPage.getTitle()).containsIgnoringCase("login");
    }

    @Test
    @DisplayName("Login with invalid credentials shows error")
    @Story("Login")
    void loginWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage();
        loginPage.open()
                 .loginWith("invalid@user.com", "wrongpassword")
                 .clickLogin();

        assertThat(loginPage.isErrorVisible())
                .as("Error message should be visible after failed login")
                .isTrue();
    }
}
