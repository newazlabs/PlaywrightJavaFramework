package com.framework.portals.customer.api;

import com.framework.core.api.ApiClient;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

public class CustomerProductApi extends ApiClient {

    // jsonplaceholder.typicode.com — /posts used as the "products" resource in demo mode
    private static final String PRODUCTS_PATH = "/posts";

    public CustomerProductApi(Playwright playwright) {
        super(playwright, Portal.CUSTOMER);
    }

    public APIResponse getProducts() {
        return get(PRODUCTS_PATH);
    }

    public APIResponse getProductById(int id) {
        return get(PRODUCTS_PATH + "/" + id);
    }

    public APIResponse searchProducts(String query) {
        return get(PRODUCTS_PATH + "?q=" + query);
    }

    public APIResponse getProductsByCategory(String category) {
        return get(PRODUCTS_PATH + "?category=" + category);
    }
}
