package com.framework.portals.customer.api;

import com.framework.core.api.ApiClient;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Global API — reqres.in")
@Tag("api")
class SampleApiTest {

    private Playwright playwright;
    private ApiClient apiClient;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        apiClient = new ApiClient(playwright);
    }

    @AfterEach
    void tearDown() {
        apiClient.dispose();
        playwright.close();
    }

    @Test
    @DisplayName("GET /users returns 200 (jsonplaceholder)")
    @Story("Users API")
    void getUsersReturns200() {
        APIResponse response = apiClient.get("/users");
        assertThat(response.status()).isEqualTo(200);
    }

    @Test
    @DisplayName("POST /users returns 201 (jsonplaceholder)")
    @Story("Users API")
    void createUserReturns201() {
        var body = java.util.Map.of("name", "Test User", "job", "tester");
        APIResponse response = apiClient.post("/users", body);
        assertThat(response.status()).isEqualTo(201);
    }
}
