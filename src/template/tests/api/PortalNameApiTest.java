package com.framework.portals.portalname.api;

import com.framework.core.base.BaseApiTest;
import com.framework.core.config.Portal;
import com.framework.core.utils.DataFactory;
import com.microsoft.playwright.APIResponse;
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
// 4. Update @Feature, @Tag, and @DisplayName text to describe your portal/API
// 5. Update assertions to match what your API actually returns
// ──────────────────────────────────────────────────────────────────────────────

@Feature("PortalName - Resource API")
@Tag("portalname")
@Tag("api")
class PortalNameApiTest extends BaseApiTest {

    PortalNameApiTest() {
        super(Portal.PORTAL_NAME);
    }

    // ── GET all ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /placeholder-resource returns 200 with a list")
    @Story("Resource API")
    void getAllReturns200() {
        PortalNameApi api = new PortalNameApi(playwright);

        APIResponse response = api.getAll();

        assertThat(response.status())
                .as("Status should be 200 OK")
                .isEqualTo(200);
    }

    // ── GET by ID ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /placeholder-resource/1 returns a single item")
    @Story("Resource API")
    void getByIdReturns200() {
        PortalNameApi api = new PortalNameApi(playwright);

        APIResponse response = api.getById(1);

        assertThat(response.status())
                .as("Status should be 200 OK")
                .isEqualTo(200);
    }

    @Test
    @DisplayName("GET /placeholder-resource/99999 returns 404 for unknown ID")
    @Story("Resource API")
    void getByUnknownIdReturns404() {
        PortalNameApi api = new PortalNameApi(playwright);

        APIResponse response = api.getById(99999);

        assertThat(response.status())
                .as("Status should be 404 Not Found for an ID that does not exist")
                .isEqualTo(404);
    }

    // ── POST (create) ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /placeholder-resource creates a new item and returns 201")
    @Story("Resource API")
    void createReturns201() {
        PortalNameApi api = new PortalNameApi(playwright);

        // DataFactory generates unique random values so each test run is independent
        String uniqueName = "Test Item " + DataFactory.randomAlphanumeric(6);
        APIResponse response = api.create(uniqueName, "placeholder-value");

        assertThat(response.status())
                .as("Status should be 201 Created")
                .isEqualTo(201);
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /placeholder-resource/1 returns 200")
    @Story("Resource API")
    void deleteReturns200() {
        PortalNameApi api = new PortalNameApi(playwright);

        APIResponse response = api.delete(1);

        assertThat(response.status())
                .as("Status should be 200 OK after deletion")
                .isEqualTo(200);
    }

    // ── Add your own tests below ──────────────────────────────────────────────
    // Each @Test method is one test case.
    // Follow the same pattern: create API → call method → assert response status.
}
