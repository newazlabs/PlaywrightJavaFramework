package com.framework.portals.orangehrm.api;

import com.framework.core.base.BaseApiTest;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("OrangeHRM - Employee API")
@Tag("orangehrm")
@Tag("api")
class OrangeHrmEmployeeApiTest extends BaseApiTest {

    OrangeHrmEmployeeApiTest() {
        super(Portal.ORANGEHRM);
    }

    @Test
    @DisplayName("GET /public/v2/users returns 200 with user list")
    @Story("Employee API")
    void getUserListReturns200() {
        OrangeHrmEmployeeApi api = new OrangeHrmEmployeeApi(playwright);

        APIResponse response = api.getAllUsers();

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.text()).contains("id");
    }

    @Test
    @DisplayName("GET /public/v2/users?page=1 returns paginated results")
    @Story("Employee API")
    void getPaginatedUsersReturns200() {
        OrangeHrmEmployeeApi api = new OrangeHrmEmployeeApi(playwright);

        APIResponse response = api.getUsersByPage(1);

        assertThat(response.status()).isEqualTo(200);
    }

    @Test
    @DisplayName("GET /public/v2/users/{id} for user 1 returns 200")
    @Story("Employee API")
    void getUserByIdReturns200() {
        OrangeHrmEmployeeApi api = new OrangeHrmEmployeeApi(playwright);

        APIResponse listResponse = api.getAllUsers();
        assertThat(listResponse.status()).isEqualTo(200);

        // Extract first ID from the response and fetch that user
        String body = listResponse.text();
        // GoRest returns array — first id appears after "\"id\":"
        int idStart = body.indexOf("\"id\":") + 5;
        int idEnd = body.indexOf(",", idStart);
        int userId = Integer.parseInt(body.substring(idStart, idEnd).trim());

        APIResponse userResponse = api.getUserById(userId);
        assertThat(userResponse.status()).isEqualTo(200);
    }
}
