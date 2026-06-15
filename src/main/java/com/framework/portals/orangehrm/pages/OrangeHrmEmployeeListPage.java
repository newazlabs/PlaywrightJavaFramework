package com.framework.portals.orangehrm.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class OrangeHrmEmployeeListPage extends BasePage {

    private static final String SEARCH_NAME_INPUT  = "(//input[@placeholder='Type for hints...'])[1]";
    private static final String SEARCH_BUTTON      = "button[type='submit']";
    private static final String RESET_BUTTON       = "button[type='reset']";
    private static final String EMPLOYEE_TABLE     = ".oxd-table";
    private static final String TABLE_ROWS         = ".oxd-table-body .oxd-table-row";
    private static final String RECORDS_FOUND_TEXT = ".orangehrm-horizontal-padding span";

    public OrangeHrmEmployeeListPage() {
        super(Portal.ORANGEHRM);
    }

    public OrangeHrmEmployeeListPage waitForLoad() {
        waitForVisible(EMPLOYEE_TABLE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(EMPLOYEE_TABLE);
    }

    public OrangeHrmEmployeeListPage searchByName(String name) {
        fill(SEARCH_NAME_INPUT, name);
        click(SEARCH_BUTTON);
        waitForNetworkIdle();
        return this;
    }

    public OrangeHrmEmployeeListPage resetSearch() {
        click(RESET_BUTTON);
        return this;
    }

    public String getRecordsFoundText() {
        waitForVisible(RECORDS_FOUND_TEXT);
        return getText(RECORDS_FOUND_TEXT);
    }

    public int getRowCount() {
        try {
            page.waitForSelector(TABLE_ROWS, new com.microsoft.playwright.Page.WaitForSelectorOptions().setTimeout(10000));
        } catch (Exception e) {
            return 0;
        }
        return page.locator(TABLE_ROWS).count();
    }

    public boolean isTableVisible() {
        return isVisible(EMPLOYEE_TABLE);
    }
}
