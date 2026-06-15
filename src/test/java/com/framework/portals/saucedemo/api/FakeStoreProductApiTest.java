package com.framework.portals.saucedemo.api;

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

@Feature("SauceDemo - Product API")
@Tag("saucedemo")
@Tag("api")
class FakeStoreProductApiTest extends BaseApiTest {

    FakeStoreProductApiTest() {
        super(Portal.SAUCEDEMO);
    }

    @Test
    @DisplayName("GET /products returns 200 with product list")
    @Story("Products API")
    void getAllProductsReturns200() {
        FakeStoreProductApi api = new FakeStoreProductApi(playwright);

        APIResponse response = api.getAllProducts();

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.text()).contains("\"title\"");
    }

    @Test
    @DisplayName("GET /products/1 returns single product")
    @Story("Products API")
    void getProductByIdReturns200() {
        FakeStoreProductApi api = new FakeStoreProductApi(playwright);

        APIResponse response = api.getProductById(1);

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.text()).contains("\"id\":1");
    }

    @Test
    @DisplayName("GET /products/categories returns category list")
    @Story("Products API")
    void getCategoriesReturns200() {
        FakeStoreProductApi api = new FakeStoreProductApi(playwright);

        APIResponse response = api.getCategories();

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.text()).contains("electronics");
    }

    @Test
    @DisplayName("GET /products/category/electronics returns products in that category")
    @Story("Products API")
    void getProductsByCategoryReturns200() {
        FakeStoreProductApi api = new FakeStoreProductApi(playwright);

        APIResponse response = api.getProductsByCategory("electronics");

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.text()).contains("\"category\":\"electronics\"");
    }

    @Test
    @DisplayName("POST /products creates new product and returns 201")
    @Story("Products API")
    void createProductReturns201() {
        FakeStoreProductApi api = new FakeStoreProductApi(playwright);

        APIResponse response = api.createProduct(
                "Automation Test Product - " + DataFactory.randomAlphanumeric(6),
                29.99,
                "electronics",
                "Created by automation test"
        );

        assertThat(response.status()).isEqualTo(201);
        assertThat(response.text()).contains("\"id\"");
    }

    @Test
    @DisplayName("DELETE /products/1 returns 200")
    @Story("Products API")
    void deleteProductReturns200() {
        FakeStoreProductApi api = new FakeStoreProductApi(playwright);

        APIResponse response = api.deleteProduct(1);

        assertThat(response.status()).isEqualTo(200);
    }
}
