package com.framework.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PortalConfigManager {

    private final Properties properties = new Properties();
    private final Portal portal;
    private final ConfigManager globalConfig;

    public PortalConfigManager(Portal portal) {
        this.portal = portal;
        this.globalConfig = ConfigManager.getInstance();
        loadPortalProperties();
    }

    private void loadPortalProperties() {
        String fileName = "portals/" + portal.getKey() + ".properties";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null)
                throw new RuntimeException("Portal config not found: " + fileName
                        + " — create src/main/resources/" + fileName);
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load portal config: " + fileName, e);
        }
    }

    /**
     * Portal-specific key wins over global config.
     * System property format: -Dadmin.base.url=https://... (portal key prefix)
     */
    public String get(String key) {
        String sysProp = System.getProperty(portal.getKey() + "." + key);
        if (sysProp != null) return sysProp;
        String portalVal = properties.getProperty(key);
        if (portalVal != null) return portalVal;
        return globalConfig.get(key);
    }

    public String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    public int getInt(String key, int defaultValue) {
        String value = get(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public Portal getPortal()       { return portal; }
    public String getBaseUrl()      { return get("base.url"); }
    public String getApiBaseUrl()   { return get("api.base.url"); }
    public String getUsername()     { return get("credentials.username"); }
    public String getPassword()     { return get("credentials.password"); }
}
