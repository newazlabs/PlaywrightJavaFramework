package com.framework.portals.portalname.api;

import com.framework.core.api.ApiClient;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

import java.util.Map;

// ─── INSTRUCTIONS ─────────────────────────────────────────────────────────────
// 1. Replace every occurrence of "PortalName" with your portal name (PascalCase)
// 2. Replace every occurrence of "portalname" with your portal name (lowercase)
// 3. Replace every occurrence of "PORTAL_NAME" with your portal name (UPPERCASE)
// 4. Replace "/placeholder-resource" with your real API endpoint path
// 5. Rename this class to reflect the resource it covers, e.g. MyShopProductApi
//    If you have multiple resources (products, orders, users), create one file each.
// ──────────────────────────────────────────────────────────────────────────────

public class PortalNameApi extends ApiClient {

    // The path appended to api.base.url from portalname.properties
    // Example: if api.base.url=https://api.myshop.com and RESOURCE="/products"
    //          then get(RESOURCE) calls https://api.myshop.com/products
    private static final String RESOURCE = "/placeholder-resource";

    public PortalNameApi(Playwright playwright) {
        super(playwright, Portal.PORTAL_NAME);
    }

    // GET /placeholder-resource — fetch all items
    public APIResponse getAll() {
        return get(RESOURCE);
    }

    // GET /placeholder-resource/{id} — fetch one item by ID
    public APIResponse getById(int id) {
        return get(RESOURCE + "/" + id);
    }

    // POST /placeholder-resource — create a new item
    // Replace the Map keys ("name", "value") with your real request body fields
    public APIResponse create(String name, String value) {
        return post(RESOURCE, Map.of(
                "name",  name,
                "value", value
        ));
    }

    // PUT /placeholder-resource/{id} — fully update an existing item
    public APIResponse update(int id, String name, String value) {
        return put(RESOURCE + "/" + id, Map.of(
                "name",  name,
                "value", value
        ));
    }

    // DELETE /placeholder-resource/{id} — delete an item
    public APIResponse delete(int id) {
        return delete(RESOURCE + "/" + id);
    }

    // ── Add your own methods below ────────────────────────────────────────────
    // Example: search by keyword
    //
    // public APIResponse search(String keyword) {
    //     return get(RESOURCE + "?q=" + keyword);
    // }
}
