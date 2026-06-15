package com.framework.portals.customer.api;

import com.framework.core.base.BaseApiTest;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Customer Portal - Products API")
@Tag("customer")
@Tag("api")
class CustomerProductApiTest extends BaseApiTest {

    CustomerProductApiTest() {
        super(Portal.CUSTOMER);
    }

    @Test
    @DisplayName("GET /posts returns 200 with product list")
    @Story("Products API")
    void getProductsReturns200() {
        CustomerProductApi productApi = new CustomerProductApi(playwright);
        APIResponse response = productApi.getProducts();
        assertThat(response.status()).isEqualTo(200);
    }

    @Test
    @DisplayName("GET /posts/{id} returns 200 with product detail")
    @Story("Products API")
    void getProductByIdReturns200() {
        CustomerProductApi productApi = new CustomerProductApi(playwright);
        APIResponse response = productApi.getProductById(1);
        assertThat(response.status()).isEqualTo(200);
    }

    @Test
    @DisplayName("GET /posts?q=keyword returns 200")
    @Story("Products API")
    void searchProductsReturns200() {
        CustomerProductApi productApi = new CustomerProductApi(playwright);
        APIResponse response = productApi.searchProducts("laptop");
        assertThat(response.status()).isEqualTo(200);
    }
}
