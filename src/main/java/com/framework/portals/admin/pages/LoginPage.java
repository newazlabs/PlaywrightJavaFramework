package com.framework.portals.admin.pages;

import com.framework.core.pages.BasePage;

public class LoginPage extends BasePage {

    private static final String USERNAME_INPUT = "#username";
    private static final String PASSWORD_INPUT = "#password";
    private static final String LOGIN_BUTTON   = "#submit";
    private static final String ERROR_MESSAGE  = "#error";

    public LoginPage open() {
        navigateTo("/practice-test-login/");
        return this;
    }

    public LoginPage enterUsername(String username) {
        fill(USERNAME_INPUT, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        fill(PASSWORD_INPUT, password);
        return this;
    }

    public LoginPage loginWith(String username, String password) {
        return enterUsername(username).enterPassword(password);
    }

    public void clickLogin() {
        click(LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        waitForVisible(ERROR_MESSAGE);
        return getText(ERROR_MESSAGE);
    }

    public boolean isErrorVisible() {
        return isVisible(ERROR_MESSAGE);
    }
}
