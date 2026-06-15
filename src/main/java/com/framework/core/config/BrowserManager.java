package com.framework.core.config;

import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.List;

public class BrowserManager {

    private static final Logger log = LogManager.getLogger(BrowserManager.class);
    private static final ConfigManager config = ConfigManager.getInstance();

    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public static void initBrowser() {
        Playwright playwright = Playwright.create();
        playwrightThreadLocal.set(playwright);

        Browser browser = launchBrowser(playwright);
        browserThreadLocal.set(browser);
        log.info("Browser launched: {}", config.getBrowser());
    }

    public static BrowserContext createContext() {
        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setViewportSize(config.getViewportWidth(), config.getViewportHeight())
                .setRecordVideoDir(Paths.get("target/videos/"))
                .setIgnoreHTTPSErrors(true);

        BrowserContext context = browserThreadLocal.get().newContext(options);
        context.setDefaultTimeout(config.getDefaultTimeout());
        context.setDefaultNavigationTimeout(config.getNavigationTimeout());
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        contextThreadLocal.set(context);
        return context;
    }

    public static Page createPage() {
        Page page = contextThreadLocal.get().newPage();
        pageThreadLocal.set(page);
        return page;
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    public static BrowserContext getContext() {
        return contextThreadLocal.get();
    }

    public static void saveTrace(String testName) {
        BrowserContext context = contextThreadLocal.get();
        if (context != null) {
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("target/traces/" + testName + ".zip")));
        }
    }

    public static void closeContext() {
        BrowserContext context = contextThreadLocal.get();
        if (context != null) {
            context.close();
            contextThreadLocal.remove();
            pageThreadLocal.remove();
        }
    }

    public static void closeBrowser() {
        Browser browser = browserThreadLocal.get();
        if (browser != null) {
            browser.close();
            browserThreadLocal.remove();
        }
        Playwright playwright = playwrightThreadLocal.get();
        if (playwright != null) {
            playwright.close();
            playwrightThreadLocal.remove();
        }
        log.info("Browser closed");
    }

    private static Browser launchBrowser(Playwright playwright) {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(config.isHeadless())
                .setSlowMo(config.getSlowMo())
                .setArgs(List.of("--start-maximized"));

        return switch (config.getBrowser().toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit"  -> playwright.webkit().launch(options);
            default        -> playwright.chromium().launch(options);
        };
    }
}
