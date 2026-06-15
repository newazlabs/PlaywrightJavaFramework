package com.framework.core.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.core.config.ConfigManager;
import com.framework.core.config.Portal;
import com.framework.core.config.PortalConfigManager;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    protected static final Logger log = LogManager.getLogger(ApiClient.class);
    protected static final ObjectMapper mapper = new ObjectMapper();

    private final APIRequestContext requestContext;
    protected final PortalConfigManager portalConfig;
    private final Map<String, String> defaultHeaders = new HashMap<>();

    /** Portal-scoped client — base URL comes from portals/<portal>.properties */
    public ApiClient(Playwright playwright, Portal portal) {
        this.portalConfig = new PortalConfigManager(portal);
        this.requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(portalConfig.getApiBaseUrl())
                .setIgnoreHTTPSErrors(true));
        log.info("[{}] API client initialised → {}", portal.getKey().toUpperCase(), portalConfig.getApiBaseUrl());
    }

    /** Global client — base URL comes from config.properties */
    public ApiClient(Playwright playwright) {
        ConfigManager globalConfig = ConfigManager.getInstance();
        this.portalConfig = null;
        this.requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(globalConfig.getApiBaseUrl())
                .setIgnoreHTTPSErrors(true));
    }

    public ApiClient withBearerToken(String token) {
        defaultHeaders.put("Authorization", "Bearer " + token);
        return this;
    }

    public ApiClient withHeader(String key, String value) {
        defaultHeaders.put(key, value);
        return this;
    }

    public APIResponse get(String path) {
        log.info("GET {}", path);
        APIResponse response = requestContext.get(path, buildOptions(null));
        logResponse(response);
        return response;
    }

    public APIResponse post(String path, Object body) {
        log.info("POST {}", path);
        APIResponse response = requestContext.post(path, buildOptions(body));
        logResponse(response);
        return response;
    }

    public APIResponse put(String path, Object body) {
        log.info("PUT {}", path);
        APIResponse response = requestContext.put(path, buildOptions(body));
        logResponse(response);
        return response;
    }

    public APIResponse patch(String path, Object body) {
        log.info("PATCH {}", path);
        APIResponse response = requestContext.patch(path, buildOptions(body));
        logResponse(response);
        return response;
    }

    public APIResponse delete(String path) {
        log.info("DELETE {}", path);
        APIResponse response = requestContext.delete(path, buildOptions(null));
        logResponse(response);
        return response;
    }

    public <T> T deserialize(APIResponse response, Class<T> clazz) {
        try {
            return mapper.readValue(response.body(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize response to " + clazz.getSimpleName(), e);
        }
    }

    private RequestOptions buildOptions(Object body) {
        RequestOptions options = RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json");
        defaultHeaders.forEach(options::setHeader);
        if (body != null) {
            try {
                options.setData(mapper.writeValueAsString(body));
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize request body", e);
            }
        }
        return options;
    }

    private void logResponse(APIResponse response) {
        log.info("Response: {} {}", response.status(), response.statusText());
    }

    public void dispose() {
        requestContext.dispose();
    }
}
