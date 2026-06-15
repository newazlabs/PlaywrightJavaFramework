package com.framework.portals.customer.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class CustomerHomePage extends BasePage {

    // the-internet.herokuapp.com/secure selectors
    private static final String SECURE_HEADER  = "h2";
    private static final String FLASH_MESSAGE  = "#flash";
    private static final String LOGOUT_BUTTON  = "a[href='/logout']";

    public CustomerHomePage() {
        super(Portal.CUSTOMER);
    }

    public boolean isLoaded() {
        waitForVisible(SECURE_HEADER);
        return getText(SECURE_HEADER).contains("Secure Area");
    }

    public String getFlashMessage() {
        waitForVisible(FLASH_MESSAGE);
        return getText(FLASH_MESSAGE);
    }

    public boolean isLoginSuccessVisible() {
        return getText(FLASH_MESSAGE).toLowerCase().contains("secure area");
    }

    public void logout() {
        click(LOGOUT_BUTTON);
    }
}
