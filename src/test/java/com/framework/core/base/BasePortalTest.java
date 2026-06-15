package com.framework.core.base;

import com.framework.core.config.Portal;
import com.framework.core.config.PortalConfigManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

/**
 * Extend this for any portal-specific UI test suite.
 * Adds the portal label to every Allure report automatically.
 */
public abstract class BasePortalTest extends BaseTest {

    protected final PortalConfigManager portalConfig;

    protected BasePortalTest(Portal portal) {
        this.portalConfig = new PortalConfigManager(portal);
    }

    @BeforeEach
    void tagPortal(TestInfo testInfo) {
        Allure.label("portal", portalConfig.getPortal().getKey());
        log.info("[{}] Test: {}", portalConfig.getPortal().getKey().toUpperCase(),
                testInfo.getDisplayName());
    }
}
