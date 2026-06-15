package com.framework.core.base;

import com.framework.core.config.BrowserManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestLifecycleExtension.class)
public abstract class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeAll
    static void setUpBrowser() {
        BrowserManager.initBrowser();
    }

    @BeforeEach
    void setUpContext(TestInfo testInfo) {
        BrowserManager.createContext();
        BrowserManager.createPage();
        log.info("Starting test: {}", testInfo.getDisplayName());
        Allure.label("thread", Thread.currentThread().getName());
    }

    @AfterEach
    void tearDownContext(TestInfo testInfo) {
        String testName = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9]", "_");
        try {
            BrowserManager.saveTrace(testName);
        } finally {
            BrowserManager.closeContext();
            log.info("Finished test: {}", testInfo.getDisplayName());
        }
    }

    @AfterAll
    static void tearDownBrowser() {
        BrowserManager.closeBrowser();
    }
}
