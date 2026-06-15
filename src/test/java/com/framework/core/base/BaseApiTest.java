package com.framework.core.base;

import com.framework.core.config.Portal;
import com.framework.core.config.PortalConfigManager;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

/**
 * Base for API-only tests. No browser overhead — pure APIRequestContext.
 * Extend this for portal-scoped API test suites.
 */
public abstract class BaseApiTest {

    protected static final Logger log = LogManager.getLogger(BaseApiTest.class);

    protected Playwright playwright;
    protected final PortalConfigManager portalConfig;

    protected BaseApiTest(Portal portal) {
        this.portalConfig = new PortalConfigManager(portal);
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        playwright = Playwright.create();
        Allure.label("portal", portalConfig.getPortal().getKey());
        Allure.label("layer", "api");
        log.info("[{}][API] Starting: {}", portalConfig.getPortal().getKey().toUpperCase(),
                testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown() {
        if (playwright != null) playwright.close();
    }
}
