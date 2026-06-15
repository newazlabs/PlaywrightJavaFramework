package com.framework.core.config;

/**
 * Add a new portal here (one line) + create src/main/resources/portals/<key>.properties
 * That's all the core framework change needed to onboard a new portal.
 */
public enum Portal {
    ADMIN("admin"),
    CUSTOMER("customer"),
    ORANGEHRM("orangehrm"),
    SAUCEDEMO("saucedemo");

    private final String key;

    Portal(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
