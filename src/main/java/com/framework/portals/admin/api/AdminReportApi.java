package com.framework.portals.admin.api;

import com.framework.core.api.ApiClient;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

public class AdminReportApi extends ApiClient {

    private static final String REPORTS_PATH = "/api/reports";

    public AdminReportApi(Playwright playwright) {
        super(playwright, Portal.ADMIN);
    }

    public APIResponse getSalesReport(String from, String to) {
        return get(REPORTS_PATH + "/sales?from=" + from + "&to=" + to);
    }

    public APIResponse getUserActivityReport() {
        return get(REPORTS_PATH + "/user-activity");
    }

    public APIResponse exportReport(String reportId, String format) {
        return post(REPORTS_PATH + "/" + reportId + "/export", java.util.Map.of("format", format));
    }
}
