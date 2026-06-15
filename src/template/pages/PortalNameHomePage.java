package com.framework.portals.portalname.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

// ─── INSTRUCTIONS ─────────────────────────────────────────────────────────────
// 1. Replace every occurrence of "PortalName" with your portal name (PascalCase)
// 2. Replace every occurrence of "portalname" with your portal name (lowercase)
// 3. Replace every occurrence of "PORTAL_NAME" with your portal name (UPPERCASE)
// 4. Replace placeholder selectors (#placeholder-*) with real CSS/XPath selectors
// 5. Add more methods for any actions available on this page
// ──────────────────────────────────────────────────────────────────────────────

public class PortalNameHomePage extends BasePage {

    // ── Selectors ─────────────────────────────────────────────────────────────
    // Replace these with the real CSS selectors from your portal's home page.
    private static final String MAIN_CONTENT  = "#placeholder-main-content";
    private static final String LOGOUT_BUTTON = "#placeholder-logout";

    public PortalNameHomePage() {
        super(Portal.PORTAL_NAME);
    }

    // Returns true if the home page has loaded successfully
    public boolean isLoaded() {
        waitForVisible(MAIN_CONTENT);
        return isVisible(MAIN_CONTENT);
    }

    // Returns the page title (browser tab title)
    public String getPageTitle() {
        return getTitle();
    }

    // Returns the current URL — useful to assert the page navigated correctly
    public String getUrl() {
        return getCurrentUrl();
    }

    // Logs out and returns to the login page
    public PortalNameLoginPage logout() {
        click(LOGOUT_BUTTON);
        return new PortalNameLoginPage();
    }

    // ── Add your own methods below ────────────────────────────────────────────
    // Example: click a "Products" link and return a ProductsPage object
    //
    // private static final String PRODUCTS_LINK = "a[href='/products']";
    //
    // public PortalNameProductsPage goToProducts() {
    //     click(PRODUCTS_LINK);
    //     return new PortalNameProductsPage();
    // }
}
