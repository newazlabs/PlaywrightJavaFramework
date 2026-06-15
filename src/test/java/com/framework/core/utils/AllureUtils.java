package com.framework.core.utils;

import com.framework.core.config.BrowserManager;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AllureUtils {

    private AllureUtils() {}

    public static void attachScreenshot(String name) {
        Page page = BrowserManager.getPage();
        if (page == null) return;
        byte[] screenshot = page.screenshot();
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
    }

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
    }

    public static void attachJson(String name, String json) {
        Allure.addAttachment(name, "application/json", json);
    }

    public static void attachTrace(String testName) {
        try {
            var tracePath = Paths.get("target/traces/" + testName + ".zip");
            if (Files.exists(tracePath)) {
                Allure.addAttachment("Playwright Trace", "application/zip",
                        new ByteArrayInputStream(Files.readAllBytes(tracePath)), ".zip");
            }
        } catch (Exception ignored) {}
    }
}
