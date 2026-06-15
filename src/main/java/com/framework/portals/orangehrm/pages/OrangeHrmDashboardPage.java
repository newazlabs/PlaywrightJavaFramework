package com.framework.portals.orangehrm.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class OrangeHrmDashboardPage extends BasePage {

    private static final String MAIN_MENU         = "ul.oxd-main-menu";
    private static final String DASHBOARD_HEADER  = ".oxd-topbar-header-breadcrumb-module";
    private static final String USER_MENU         = ".oxd-userdropdown-tab";
    private static final String LOGOUT_OPTION     = "//a[normalize-space()='Logout']";
    private static final String PIM_MENU_ITEM     = "//span[normalize-space()='PIM']";

    public OrangeHrmDashboardPage() {
        super(Portal.ORANGEHRM);
    }

    public boolean isLoaded() {
        waitForVisible(MAIN_MENU);
        return isVisible(MAIN_MENU);
    }

    public String getDashboardTitle() {
        waitForVisible(DASHBOARD_HEADER);
        return getText(DASHBOARD_HEADER);
    }

    public OrangeHrmEmployeeListPage navigateToPim() {
        click(PIM_MENU_ITEM);
        return new OrangeHrmEmployeeListPage();
    }

    public void logout() {
        click(USER_MENU);
        page.locator(LOGOUT_OPTION).click();
    }
}
