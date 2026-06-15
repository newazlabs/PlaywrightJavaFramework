package com.framework.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();
    private static ConfigManager instance;

    private ConfigManager() {
        String env = System.getProperty("env", "staging");
        loadProperties("config.properties");
        loadProperties("config-" + env + ".properties");
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) instance = new ConfigManager();
            }
        }
        return instance;
    }

    private void loadProperties(String fileName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + fileName, e);
        }
    }

    public String get(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    public String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    public int getInt(String key, int defaultValue) {
        String value = get(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public String getBrowser()          { return get("browser", "chromium"); }
    public boolean isHeadless()         { return getBoolean("headless", true); }
    public int getSlowMo()              { return getInt("slowMo", 0); }
    public String getBaseUrl()          { return get("base.url"); }
    public String getApiBaseUrl()       { return get("api.base.url"); }
    public int getDefaultTimeout()      { return getInt("default.timeout", 30000); }
    public int getNavigationTimeout()   { return getInt("navigation.timeout", 30000); }
    public int getViewportWidth()       { return getInt("viewport.width", 1920); }
    public int getViewportHeight()      { return getInt("viewport.height", 1080); }
    public String getVideoOption()      { return get("video", "retain-on-failure"); }
    public String getTraceOption()      { return get("trace", "retain-on-failure"); }
    public String getScreenshotOption() { return get("screenshot", "only-on-failure"); }
}
