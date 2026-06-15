package com.framework.portals.saucedemo.pages;

import com.framework.core.config.Portal;
import com.framework.core.config.PortalConfigManager;
import com.framework.core.pages.BasePage;

public class SauceDemoLoginPage extends BasePage {

    private static final String USERNAME_INPUT = "#user-name";
    private static final String PASSWORD_INPUT = "#password";
    private static final String LOGIN_BUTTON   = "#login-button";
    private static final String ERROR_MESSAGE  = "[data-test='error']";

    public SauceDemoLoginPage() {
        super(Portal.SAUCEDEMO);
    }

    public SauceDemoLoginPage open() {
        navigateTo("/");
        waitForVisible(LOGIN_BUTTON);
        return this;
    }

    public SauceDemoLoginPage enterUsername(String username) {
        fill(USERNAME_INPUT, username);
        return this;
    }

    public SauceDemoLoginPage enterPassword(String password) {
        fill(PASSWORD_INPUT, password);
        return this;
    }

    public SauceDemoInventoryPage clickLogin() {
        click(LOGIN_BUTTON);
        return new SauceDemoInventoryPage();
    }

    public SauceDemoInventoryPage loginWithDefaultCredentials() {
        enterUsername(portalConfig.getUsername()).enterPassword(portalConfig.getPassword());
        click(LOGIN_BUTTON);
        page.waitForURL("**/inventory.html");
        return new SauceDemoInventoryPage();
    }

    public SauceDemoLoginPage loginWithLockedUser() {
        PortalConfigManager cfg = new PortalConfigManager(Portal.SAUCEDEMO);
        return enterUsername(cfg.get("credentials.locked.username"))
                .enterPassword(cfg.get("credentials.password"));
    }

    public String getErrorMessage() {
        waitForVisible(ERROR_MESSAGE);
        return getText(ERROR_MESSAGE);
    }

    public boolean isErrorVisible() {
        try {
            waitForVisible(ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
