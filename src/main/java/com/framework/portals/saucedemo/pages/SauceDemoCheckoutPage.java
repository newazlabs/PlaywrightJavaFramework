package com.framework.portals.saucedemo.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class SauceDemoCheckoutPage extends BasePage {

    // Step 1 — customer info
    private static final String FIRST_NAME_INPUT = "[data-test='firstName']";
    private static final String LAST_NAME_INPUT  = "[data-test='lastName']";
    private static final String ZIP_INPUT        = "[data-test='postalCode']";
    private static final String CONTINUE_BUTTON  = "[data-test='continue']";
    private static final String ERROR_MESSAGE    = "[data-test='error']";

    // Step 2 — overview
    private static final String ORDER_TOTAL      = ".summary_total_label";
    private static final String FINISH_BUTTON    = "[data-test='finish']";

    // Step 3 — complete
    private static final String COMPLETE_HEADER  = ".complete-header";
    private static final String BACK_HOME_BUTTON = "[data-test='back-to-products']";

    public SauceDemoCheckoutPage() {
        super(Portal.SAUCEDEMO);
    }

    public boolean isOnStepOne() {
        return getCurrentUrl().contains("checkout-step-one");
    }

    public boolean isOnStepTwo() {
        return getCurrentUrl().contains("checkout-step-two");
    }

    public boolean isOnComplete() {
        return getCurrentUrl().contains("checkout-complete");
    }

    public SauceDemoCheckoutPage fillCustomerInfo(String firstName, String lastName, String zip) {
        waitForVisible(FIRST_NAME_INPUT);
        fill(FIRST_NAME_INPUT, firstName);
        fill(LAST_NAME_INPUT, lastName);
        fill(ZIP_INPUT, zip);
        return this;
    }

    public SauceDemoCheckoutPage clickContinue() {
        click(CONTINUE_BUTTON);
        return this;
    }

    public boolean isErrorVisible() {
        return isVisible(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        waitForVisible(ERROR_MESSAGE);
        return getText(ERROR_MESSAGE);
    }

    public String getOrderTotal() {
        waitForVisible(ORDER_TOTAL);
        return getText(ORDER_TOTAL);
    }

    public SauceDemoCheckoutPage clickFinish() {
        click(FINISH_BUTTON);
        return this;
    }

    public String getCompleteHeader() {
        waitForVisible(COMPLETE_HEADER);
        return getText(COMPLETE_HEADER);
    }

    public SauceDemoInventoryPage backToProducts() {
        click(BACK_HOME_BUTTON);
        return new SauceDemoInventoryPage();
    }
}
