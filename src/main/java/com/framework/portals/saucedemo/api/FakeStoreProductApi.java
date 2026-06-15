package com.framework.portals.saucedemo.api;

import com.framework.core.api.ApiClient;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

import java.util.Map;

public class FakeStoreProductApi extends ApiClient {

    private static final String PRODUCTS_PATH    = "/products";
    private static final String CATEGORIES_PATH  = "/products/categories";

    public FakeStoreProductApi(Playwright playwright) {
        super(playwright, Portal.SAUCEDEMO);
    }

    public APIResponse getAllProducts() {
        return get(PRODUCTS_PATH);
    }

    public APIResponse getProductById(int id) {
        return get(PRODUCTS_PATH + "/" + id);
    }

    public APIResponse getCategories() {
        return get(CATEGORIES_PATH);
    }

    public APIResponse getProductsByCategory(String category) {
        return get(PRODUCTS_PATH + "/category/" + category);
    }

    public APIResponse createProduct(String title, double price, String category, String description) {
        return post(PRODUCTS_PATH, Map.of(
                "title", title,
                "price", price,
                "category", category,
                "description", description,
                "image", "https://fakestoreapi.com/img/placeholder.jpg"
        ));
    }

    public APIResponse deleteProduct(int id) {
        return delete(PRODUCTS_PATH + "/" + id);
    }
}
