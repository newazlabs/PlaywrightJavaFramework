package com.framework.portals.admin.api;

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

@Feature("Admin Portal - Users API")
@Tag("admin")
@Tag("api")
class AdminUserApiTest extends BaseApiTest {

    AdminUserApiTest() {
        super(Portal.ADMIN);
    }

    @Test
    @DisplayName("GET /users returns 200 with user list")
    @Story("Users API")
    void getAllUsersReturns200() {
        AdminUserApi userApi = new AdminUserApi(playwright);
        APIResponse response = userApi.getAllUsers();
        assertThat(response.status()).isEqualTo(200);
    }

    @Test
    @DisplayName("POST /users/add creates a user and returns 201")
    @Story("Users API")
    void createUserReturns201() {
        AdminUserApi userApi = new AdminUserApi(playwright);
        APIResponse response = userApi.createUser(
                DataFactory.randomFirstName(),
                DataFactory.randomEmail(),
                "editor"
        );
        assertThat(response.status()).isEqualTo(201);
    }

    @Test
    @DisplayName("GET /users/{id} for non-existent user returns 404")
    @Story("Users API")
    void getNonExistentUserReturns404() {
        AdminUserApi userApi = new AdminUserApi(playwright);
        APIResponse response = userApi.getUserById(9999);
        assertThat(response.status()).isEqualTo(404);
    }

    @Test
    @DisplayName("DELETE /users/{id} returns 200")
    @Story("Users API")
    void deleteUserReturns200() {
        AdminUserApi userApi = new AdminUserApi(playwright);
        APIResponse response = userApi.deleteUser(1);
        assertThat(response.status()).isEqualTo(200);
    }
}
