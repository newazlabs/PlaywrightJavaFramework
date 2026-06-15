package com.framework.portals.customer.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class CustomerLoginPage extends BasePage {

    // the-internet.herokuapp.com/login selectors
    private static final String USERNAME_INPUT = "#username";
    private static final String PASSWORD_INPUT = "#password";
    private static final String LOGIN_BUTTON   = "button[type='submit']";
    private static final String FLASH_MESSAGE  = "#flash";

    public CustomerLoginPage() {
        super(Portal.CUSTOMER);
    }

    public CustomerLoginPage open() {
        navigateTo("/login");
        return this;
    }

    public CustomerLoginPage enterUsername(String username) {
        fill(USERNAME_INPUT, username);
        return this;
    }

    public CustomerLoginPage enterPassword(String password) {
        fill(PASSWORD_INPUT, password);
        return this;
    }

    public CustomerHomePage clickLogin() {
        click(LOGIN_BUTTON);
        return new CustomerHomePage();
    }

    public CustomerHomePage loginWithDefaultCredentials() {
        return enterUsername(portalConfig.getUsername())
                .enterPassword(portalConfig.getPassword())
                .clickLogin();
    }

    public String getFlashMessage() {
        waitForVisible(FLASH_MESSAGE);
        return getText(FLASH_MESSAGE);
    }

    public boolean isErrorVisible() {
        waitForVisible(FLASH_MESSAGE);
        return getText(FLASH_MESSAGE).toLowerCase().contains("invalid");
    }
}
