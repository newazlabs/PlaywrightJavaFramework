package com.framework.portals.admin.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class AdminLoginPage extends BasePage {

    private static final String USERNAME_INPUT = "#username";
    private static final String PASSWORD_INPUT = "#password";
    private static final String LOGIN_BUTTON   = "#submit";
    private static final String ERROR_MESSAGE  = "#error";

    public AdminLoginPage() {
        super(Portal.ADMIN);
    }

    public AdminLoginPage open() {
        navigateTo("/practice-test-login/");
        return this;
    }

    public AdminLoginPage enterUsername(String username) {
        fill(USERNAME_INPUT, username);
        return this;
    }

    public AdminLoginPage enterPassword(String password) {
        fill(PASSWORD_INPUT, password);
        return this;
    }

    public AdminDashboardPage clickLogin() {
        click(LOGIN_BUTTON);
        return new AdminDashboardPage();
    }

    public AdminDashboardPage loginWithDefaultCredentials() {
        return enterUsername(portalConfig.getUsername())
                .enterPassword(portalConfig.getPassword())
                .clickLogin();
    }

    public String getErrorMessage() {
        waitForVisible(ERROR_MESSAGE);
        return getText(ERROR_MESSAGE);
    }

    public boolean isErrorVisible() {
        return isVisible(ERROR_MESSAGE);
    }
}
