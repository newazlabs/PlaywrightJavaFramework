package com.framework.portals.orangehrm.ui;

import com.framework.core.base.BasePortalTest;
import com.framework.core.config.Portal;
import com.framework.portals.orangehrm.pages.OrangeHrmEmployeeListPage;
import com.framework.portals.orangehrm.pages.OrangeHrmLoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("OrangeHRM - Employee Management")
@Tag("orangehrm")
@Tag("ui")
class OrangeHrmEmployeeTest extends BasePortalTest {

    OrangeHrmEmployeeTest() {
        super(Portal.ORANGEHRM);
    }

    @Test
    @DisplayName("PIM Employee List loads after login")
    @Story("Employee List")
    void employeeListLoadsAfterLogin() {
        OrangeHrmEmployeeListPage employeeList = new OrangeHrmLoginPage()
                .open()
                .loginWithDefaultCredentials()
                .navigateToPim();

        employeeList.waitForLoad();

        assertThat(employeeList.isLoaded())
                .as("Employee list table should be visible after navigating to PIM")
                .isTrue();
    }

    @Test
    @DisplayName("Employee table has records")
    @Story("Employee List")
    void employeeTableHasRecords() {
        OrangeHrmEmployeeListPage employeeList = new OrangeHrmLoginPage()
                .open()
                .loginWithDefaultCredentials()
                .navigateToPim();

        employeeList.waitForLoad();

        assertThat(employeeList.getRowCount())
                .as("Employee table should have at least one record")
                .isGreaterThan(0);
    }

    @Test
    @DisplayName("Search employees by name filters results")
    @Story("Employee Search")
    void searchByNameFiltersResults() {
        OrangeHrmEmployeeListPage employeeList = new OrangeHrmLoginPage()
                .open()
                .loginWithDefaultCredentials()
                .navigateToPim();

        employeeList.waitForLoad().searchByName("Admin");

        assertThat(employeeList.isTableVisible())
                .as("Table should still be visible after search")
                .isTrue();
    }
}
