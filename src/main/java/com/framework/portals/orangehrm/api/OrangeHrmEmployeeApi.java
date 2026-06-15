package com.framework.portals.orangehrm.api;

import com.framework.core.api.ApiClient;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

public class OrangeHrmEmployeeApi extends ApiClient {

    private static final String USERS_PATH = "/public/v2/users";

    public OrangeHrmEmployeeApi(Playwright playwright) {
        super(playwright, Portal.ORANGEHRM);
    }

    public APIResponse getAllUsers() {
        return get(USERS_PATH);
    }

    public APIResponse getUsersByPage(int page) {
        return get(USERS_PATH + "?page=" + page);
    }

    public APIResponse getUserById(int id) {
        return get(USERS_PATH + "/" + id);
    }
}
