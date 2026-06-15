package com.framework.portals.orangehrm.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class OrangeHrmLoginPage extends BasePage {

    private static final String USERNAME_INPUT = "input[name='username']";
    private static final String PASSWORD_INPUT = "input[name='password']";
    private static final String LOGIN_BUTTON   = "button[type='submit']";
    private static final String ERROR_ALERT    = ".oxd-alert-content-text";

    public OrangeHrmLoginPage() {
        super(Portal.ORANGEHRM);
    }

    public OrangeHrmLoginPage open() {
        navigateTo("/web/index.php/auth/login");
        waitForVisible(USERNAME_INPUT);
        return this;
    }

    public OrangeHrmLoginPage enterUsername(String username) {
        fill(USERNAME_INPUT, username);
        return this;
    }

    public OrangeHrmLoginPage enterPassword(String password) {
        fill(PASSWORD_INPUT, password);
        return this;
    }

    public OrangeHrmDashboardPage clickLogin() {
        click(LOGIN_BUTTON);
        return new OrangeHrmDashboardPage();
    }

    public OrangeHrmDashboardPage loginWithDefaultCredentials() {
        return enterUsername(portalConfig.getUsername())
                .enterPassword(portalConfig.getPassword())
                .clickLogin();
    }

    public String getErrorMessage() {
        waitForVisible(ERROR_ALERT);
        return getText(ERROR_ALERT);
    }

    public boolean isErrorVisible() {
        try {
            waitForVisible(ERROR_ALERT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
