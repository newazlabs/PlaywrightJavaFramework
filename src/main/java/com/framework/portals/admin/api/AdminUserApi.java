package com.framework.portals.admin.api;

import com.framework.core.api.ApiClient;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

import java.util.Map;

public class AdminUserApi extends ApiClient {

    private static final String USERS_PATH     = "/users";
    private static final String USERS_ADD_PATH = "/users/add";

    public AdminUserApi(Playwright playwright) {
        super(playwright, Portal.ADMIN);
    }

    public APIResponse getAllUsers() {
        return get(USERS_PATH);
    }

    public APIResponse getUserById(int id) {
        return get(USERS_PATH + "/" + id);
    }

    public APIResponse createUser(String firstName, String email, String role) {
        return post(USERS_ADD_PATH, Map.of("firstName", firstName, "email", email, "role", role));
    }

    public APIResponse updateUser(int id, Map<String, Object> updates) {
        return patch(USERS_PATH + "/" + id, updates);
    }

    public APIResponse deleteUser(int id) {
        return delete(USERS_PATH + "/" + id);
    }

    public APIResponse searchUsers(String query) {
        return get(USERS_PATH + "/search?q=" + query);
    }
}
