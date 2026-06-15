package com.framework.portals.portalname.ui;

import com.framework.core.base.BasePortalTest;
import com.framework.core.config.Portal;
import com.framework.portals.portalname.pages.PortalNameHomePage;
import com.framework.portals.portalname.pages.PortalNameLoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// ─── INSTRUCTIONS ─────────────────────────────────────────────────────────────
// 1. Replace every occurrence of "PortalName" with your portal name (PascalCase)
// 2. Replace every occurrence of "portalname" with your portal name (lowercase)
// 3. Replace every occurrence of "PORTAL_NAME" with your portal name (UPPERCASE)
// 4. Update @Feature, @Tag, and @DisplayName text to describe your portal
// 5. Update the assertions to match what your portal actually shows
// ──────────────────────────────────────────────────────────────────────────────

@Feature("PortalName - Authentication")
@Tag("portalname")
@Tag("ui")
class PortalNameLoginTest extends BasePortalTest {

    PortalNameLoginTest() {
        super(Portal.PORTAL_NAME);
    }

    // ── Positive tests (happy path) ───────────────────────────────────────────

    @Test
    @DisplayName("Valid login redirects to the home page")
    @Story("Login")
    void validLoginRedirectsToHomePage() {
        PortalNameHomePage homePage = new PortalNameLoginPage()
                .open()
                .loginWithDefaultCredentials();

        assertThat(homePage.isLoaded())
                .as("Home page should be visible after a successful login")
                .isTrue();
    }

    @Test
    @DisplayName("Login page URL is correct")
    @Story("Login")
    void loginPageUrlIsCorrect() {
        PortalNameLoginPage loginPage = new PortalNameLoginPage().open();

        assertThat(loginPage.getCurrentUrl())
                .as("URL should contain the portal's base URL")
                .contains(portalConfig.getBaseUrl());
    }

    // ── Negative tests (unhappy path) ─────────────────────────────────────────

    @Test
    @DisplayName("Wrong password shows an error message")
    @Story("Login - Negative")
    void wrongPasswordShowsError() {
        PortalNameLoginPage loginPage = new PortalNameLoginPage()
                .open()
                .enterUsername(portalConfig.getUsername())
                .enterPassword("ThisIsTheWrongPassword999");

        loginPage.clickLogin();

        assertThat(loginPage.isErrorVisible())
                .as("An error message should appear for a wrong password")
                .isTrue();
    }

    @Test
    @DisplayName("Empty username shows an error message")
    @Story("Login - Negative")
    void emptyUsernameShowsError() {
        PortalNameLoginPage loginPage = new PortalNameLoginPage()
                .open()
                .enterUsername("")
                .enterPassword(portalConfig.getPassword());

        loginPage.clickLogin();

        assertThat(loginPage.isErrorVisible())
                .as("A validation error should appear when username is blank")
                .isTrue();
    }

    // ── Add your own tests below ──────────────────────────────────────────────
    // Each @Test method is one test case.
    // Follow the same pattern: open page → do action → assert result.
}
