package com.framework.portals.customer.api;

import com.framework.core.api.ApiClient;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

import java.util.List;
import java.util.Map;

public class CustomerOrderApi extends ApiClient {

    public CustomerOrderApi(Playwright playwright) {
        super(playwright, Portal.CUSTOMER);
    }

    public APIResponse getOrders() {
        return get("/orders");
    }

    public APIResponse getOrderById(String orderId) {
        return get("/orders/" + orderId);
    }

    public APIResponse placeOrder(List<Map<String, Object>> items, String shippingAddressId) {
        return post("/orders", Map.of("items", items, "shippingAddressId", shippingAddressId));
    }

    public APIResponse cancelOrder(String orderId) {
        return post("/orders/" + orderId + "/cancel", Map.of());
    }

    public APIResponse trackOrder(String orderId) {
        return get("/orders/" + orderId + "/tracking");
    }
}
