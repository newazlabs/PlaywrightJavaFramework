package com.framework.portals.admin.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class AdminDashboardPage extends BasePage {

    // practicetestautomation.com post-login page selectors
    private static final String SUCCESS_HEADER = "h1";
    private static final String LOGOUT_BUTTON  = "a:has-text('Log out')";

    public AdminDashboardPage() {
        super(Portal.ADMIN);
    }

    public boolean isLoaded() {
        waitForVisible(SUCCESS_HEADER);
        return getText(SUCCESS_HEADER).contains("Logged In Successfully");
    }

    public String getWelcomeText() {
        waitForVisible(SUCCESS_HEADER);
        return getText(SUCCESS_HEADER);
    }

    public void logout() {
        click(LOGOUT_BUTTON);
    }
}
