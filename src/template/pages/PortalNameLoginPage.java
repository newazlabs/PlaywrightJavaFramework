package com.framework.portals.portalname.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

// ─── INSTRUCTIONS ─────────────────────────────────────────────────────────────
// 1. Replace every occurrence of "PortalName" with your portal name (PascalCase)
// 2. Replace every occurrence of "portalname" with your portal name (lowercase)
// 3. Replace every occurrence of "PORTAL_NAME" with your portal name (UPPERCASE)
// 4. Replace placeholder selectors (#placeholder-*) with real CSS/XPath selectors
//    from your portal's HTML (right-click an element in Chrome → Inspect)
// ──────────────────────────────────────────────────────────────────────────────

public class PortalNameLoginPage extends BasePage {

    // ── Selectors ─────────────────────────────────────────────────────────────
    // Replace these with the real CSS selectors from your portal's login page.
    // Tip: right-click the element in Chrome → Inspect → copy the id or class.
    private static final String USERNAME_INPUT = "#placeholder-username";
    private static final String PASSWORD_INPUT = "#placeholder-password";
    private static final String LOGIN_BUTTON   = "#placeholder-login-btn";
    private static final String ERROR_MESSAGE  = "#placeholder-error";

    public PortalNameLoginPage() {
        super(Portal.PORTAL_NAME);
    }

    // Opens the login page in the browser.
    // Replace "/login" with the actual login path of your portal.
    public PortalNameLoginPage open() {
        navigateTo("/login");
        waitForVisible(USERNAME_INPUT);
        return this;
    }

    public PortalNameLoginPage enterUsername(String username) {
        fill(USERNAME_INPUT, username);
        return this;
    }

    public PortalNameLoginPage enterPassword(String password) {
        fill(PASSWORD_INPUT, password);
        return this;
    }

    // Logs in with username + password from portalname.properties
    public PortalNameHomePage loginWithDefaultCredentials() {
        return enterUsername(portalConfig.getUsername())
                .enterPassword(portalConfig.getPassword())
                .clickLogin();
    }

    // Clicks the login button and goes to the home page
    public PortalNameHomePage clickLogin() {
        click(LOGIN_BUTTON);
        return new PortalNameHomePage();
    }

    public String getErrorMessage() {
        waitForVisible(ERROR_MESSAGE);
        return getText(ERROR_MESSAGE);
    }

    // Returns true if an error message appeared on the page
    public boolean isErrorVisible() {
        try {
            waitForVisible(ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
