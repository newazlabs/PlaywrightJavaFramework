package com.framework.core.pages;

import com.framework.core.config.BrowserManager;
import com.framework.core.config.ConfigManager;
import com.framework.core.config.Portal;
import com.framework.core.config.PortalConfigManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasePage {

    protected final Page page;
    protected final ConfigManager config;
    protected final PortalConfigManager portalConfig;
    protected final Logger log;

    /** For portal-aware pages — use this. */
    protected BasePage(Portal portal) {
        this.page = BrowserManager.getPage();
        this.config = ConfigManager.getInstance();
        this.portalConfig = new PortalConfigManager(portal);
        this.log = LogManager.getLogger(getClass());
    }

    /** Legacy: for pages not tied to a specific portal. */
    protected BasePage() {
        this.page = BrowserManager.getPage();
        this.config = ConfigManager.getInstance();
        this.portalConfig = null;
        this.log = LogManager.getLogger(getClass());
    }

    protected void navigateTo(String path) {
        String baseUrl = portalConfig != null ? portalConfig.getBaseUrl() : config.getBaseUrl();
        String url = baseUrl + path;
        log.info("[{}] Navigating to: {}", portalLabel(), url);
        page.navigate(url);
    }

    protected void click(String selector) {
        log.debug("Clicking: {}", selector);
        page.locator(selector).click();
    }

    protected void fill(String selector, String value) {
        log.debug("Filling '{}' with '{}'", selector, value);
        Locator locator = page.locator(selector);
        locator.clear();
        locator.fill(value);
    }

    protected String getText(String selector) {
        return page.locator(selector).innerText();
    }

    protected boolean isVisible(String selector) {
        return page.locator(selector).isVisible();
    }

    protected void waitForVisible(String selector) {
        page.locator(selector).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    protected void waitForHidden(String selector) {
        page.locator(selector).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN));
    }

    protected void selectOption(String selector, String value) {
        page.locator(selector).selectOption(value);
    }

    protected void check(String selector) {
        page.locator(selector).check();
    }

    protected void hover(String selector) {
        page.locator(selector).hover();
    }

    protected void pressKey(String selector, String key) {
        page.locator(selector).press(key);
    }

    protected void scrollIntoView(String selector) {
        page.locator(selector).scrollIntoViewIfNeeded();
    }

    protected void waitForNetworkIdle() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    protected byte[] takeScreenshot() {
        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }

    public String getTitle() {
        return page.title();
    }

    public String getCurrentUrl() {
        return page.url();
    }

    private String portalLabel() {
        return portalConfig != null ? portalConfig.getPortal().getKey().toUpperCase() : "GLOBAL";
    }
}
