# How to Add a New Portal and Write Test Cases

This guide walks you through every single step needed to add a brand-new client portal
to this framework and write both UI and API tests for it.

You do **not** need to touch any core framework code. Everything stays inside your
portal's own folder.

> **Example used throughout this guide:** A fictional portal called **"MyShop"**
> — a shopping website at `https://www.myshop.com` with a REST API at `https://api.myshop.com`

---

## What You Will Create

| # | File | What It Does |
|---|------|--------------|
| 1 | `Portal.java` | Register the portal name (1 line edit) |
| 2 | `portals/myshop.properties` | Store the portal's URLs and login credentials |
| 3 | `portals/myshop/pages/MyShopLoginPage.java` | Page object for the login screen |
| 4 | `portals/myshop/pages/MyShopHomePage.java` | Page object for the home screen after login |
| 5 | `portals/myshop/api/MyShopProductApi.java` | API helper to call product endpoints |
| 6 | `portals/myshop/ui/MyShopLoginTest.java` | UI test cases for login |
| 7 | `portals/myshop/api/MyShopProductApiTest.java` | API test cases for products |

Total: **1 line edited** + **6 new files**. That's it.

---

## Before You Start — Understand the Folder Structure

```
src/
├── main/java/com/framework/
│   ├── core/                   ← DO NOT TOUCH — framework engine
│   │   ├── config/             (BrowserManager, Portal, PortalConfigManager, ConfigManager)
│   │   ├── pages/              (BasePage — parent of all page objects)
│   │   ├── api/                (ApiClient — parent of all API helpers)
│   │   └── utils/              (DataFactory — random test data)
│   │
│   └── portals/                ← YOUR WORK GOES HERE
│       ├── admin/
│       ├── customer/
│       ├── orangehrm/
│       ├── saucedemo/
│       └── myshop/             ← you will create this
│           ├── pages/
│           └── api/
│
├── main/resources/
│   └── portals/
│       ├── admin.properties
│       ├── saucedemo.properties
│       └── myshop.properties   ← you will create this
│
└── test/java/com/framework/
    ├── core/                   ← DO NOT TOUCH — test engine
    │   ├── base/               (BaseTest, BasePortalTest, BaseApiTest)
    │   └── utils/              (AllureUtils)
    │
    └── portals/                ← YOUR TESTS GO HERE
        ├── saucedemo/
        └── myshop/             ← you will create this
            ├── ui/
            └── api/
```

**Simple rule:** Anything inside `core/` is hands-off.
Everything you create lives inside `portals/myshop/`.

---

## Step 1 — Register the Portal Name

Open this file:
```
src/main/java/com/framework/core/config/Portal.java
```

You will see a list of existing portals. Add your new portal as one line:

**Before:**
```java
public enum Portal {
    ADMIN("admin"),
    CUSTOMER("customer"),
    ORANGEHRM("orangehrm"),
    SAUCEDEMO("saucedemo");
    ...
}
```

**After (add the highlighted line):**
```java
public enum Portal {
    ADMIN("admin"),
    CUSTOMER("customer"),
    ORANGEHRM("orangehrm"),
    SAUCEDEMO("saucedemo"),
    MYSHOP("myshop");          // <-- add this line
    ...
}
```

> The string `"myshop"` is the **key**. It must be all lowercase and must exactly
> match the name you use in the next step for the `.properties` file.

---

## Step 2 — Create the Properties File

Create a new file at:
```
src/main/resources/portals/myshop.properties
```

Paste this content and fill in the real values for your portal:

```properties
# MyShop Portal — www.myshop.com
base.url=https://www.myshop.com
api.base.url=https://api.myshop.com

credentials.username=testuser@myshop.com
credentials.password=TestPassword123
```

**What each line means:**

| Key | What It Is |
|-----|-----------|
| `base.url` | The website URL your browser tests will open |
| `api.base.url` | The base URL all API calls will use |
| `credentials.username` | The test account username |
| `credentials.password` | The test account password |

You can add any extra keys you need, for example:
```properties
credentials.admin.username=admin@myshop.com
credentials.admin.password=AdminPass456
```

You can read them later in your tests with `portalConfig.get("credentials.admin.username")`.

---

## Step 3 — Create the Login Page Object

A **Page Object** is a Java class that represents one screen of the website.
It holds the selectors (CSS / XPath) for the elements on that screen and
provides methods to interact with them.

**How to find a CSS selector:**
1. Open the website in Chrome
2. Right-click on an element (e.g., the username field) → Inspect
3. Look at the HTML and find a unique `id`, `class`, or `data-*` attribute
4. Write it as `#id` or `.class` or `[attribute='value']`

Create a new file at:
```
src/main/java/com/framework/portals/myshop/pages/MyShopLoginPage.java
```

```java
package com.framework.portals.myshop.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class MyShopLoginPage extends BasePage {

    // Selectors — CSS or XPath for each element on the login page
    private static final String EMAIL_INPUT    = "#email";
    private static final String PASSWORD_INPUT = "#password";
    private static final String LOGIN_BUTTON   = "button[type='submit']";
    private static final String ERROR_MESSAGE  = ".error-message";

    public MyShopLoginPage() {
        super(Portal.MYSHOP);   // <-- always pass your portal enum here
    }

    // Opens the login page in the browser
    public MyShopLoginPage open() {
        navigateTo("/login");           // appended to base.url from myshop.properties
        waitForVisible(EMAIL_INPUT);    // wait until the page is ready
        return this;
    }

    // Types into the email field
    public MyShopLoginPage enterEmail(String email) {
        fill(EMAIL_INPUT, email);
        return this;    // returning "this" lets you chain calls: .enterEmail().enterPassword()
    }

    // Types into the password field
    public MyShopLoginPage enterPassword(String password) {
        fill(PASSWORD_INPUT, password);
        return this;
    }

    // Clicks the login button and returns the next page (home page)
    public MyShopHomePage clickLogin() {
        click(LOGIN_BUTTON);
        return new MyShopHomePage();
    }

    // Logs in using the credentials from myshop.properties
    public MyShopHomePage loginWithDefaultCredentials() {
        return enterEmail(portalConfig.getUsername())
                .enterPassword(portalConfig.getPassword())
                .clickLogin();
    }

    // Returns the error message text shown on bad login
    public String getErrorMessage() {
        waitForVisible(ERROR_MESSAGE);
        return getText(ERROR_MESSAGE);
    }

    // Returns true if an error message is visible
    public boolean isErrorVisible() {
        try {
            waitForVisible(ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

**Methods inherited from `BasePage` that you can use in any page object:**

| Method | What It Does |
|--------|-------------|
| `navigateTo("/path")` | Opens `base.url + /path` in the browser |
| `fill(selector, text)` | Clears a field and types text into it |
| `click(selector)` | Clicks an element |
| `getText(selector)` | Returns the visible text of an element |
| `isVisible(selector)` | Returns `true` if the element is on screen right now |
| `waitForVisible(selector)` | Waits up to 30 s for the element to appear |
| `waitForHidden(selector)` | Waits up to 30 s for the element to disappear |
| `selectOption(selector, value)` | Picks an option from a `<select>` dropdown |
| `getCurrentUrl()` | Returns the current browser URL |
| `getTitle()` | Returns the page `<title>` |

---

## Step 4 — Create the Home Page Object

After a successful login, the user lands on the home page.
Create a page object for it.

Create a new file at:
```
src/main/java/com/framework/portals/myshop/pages/MyShopHomePage.java
```

```java
package com.framework.portals.myshop.pages;

import com.framework.core.config.Portal;
import com.framework.core.pages.BasePage;

public class MyShopHomePage extends BasePage {

    private static final String WELCOME_BANNER = ".welcome-banner";
    private static final String LOGOUT_BUTTON  = "a[href='/logout']";
    private static final String PRODUCT_LIST   = ".product-grid";

    public MyShopHomePage() {
        super(Portal.MYSHOP);
    }

    // Returns true if the home page loaded correctly
    public boolean isLoaded() {
        waitForVisible(WELCOME_BANNER);
        return isVisible(WELCOME_BANNER);
    }

    // Returns the text shown in the welcome banner
    public String getWelcomeText() {
        waitForVisible(WELCOME_BANNER);
        return getText(WELCOME_BANNER);
    }

    // Returns true if the product list is visible
    public boolean isProductListVisible() {
        return isVisible(PRODUCT_LIST);
    }

    // Clicks the logout link
    public void logout() {
        click(LOGOUT_BUTTON);
    }
}
```

> **Tip:** Create one page object file per screen. If you have a checkout screen,
> create `MyShopCheckoutPage.java`. If you have a product detail screen,
> create `MyShopProductPage.java`. Keep each file focused on one screen.

---

## Step 5 — Create the API Helper

An **API helper** is a class that makes HTTP calls (GET, POST, PUT, DELETE)
to the portal's REST API. You use it in API tests.

Create a new file at:
```
src/main/java/com/framework/portals/myshop/api/MyShopProductApi.java
```

```java
package com.framework.portals.myshop.api;

import com.framework.core.api.ApiClient;
import com.framework.core.config.Portal;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

import java.util.Map;

public class MyShopProductApi extends ApiClient {

    private static final String PRODUCTS_PATH = "/products";

    public MyShopProductApi(Playwright playwright) {
        super(playwright, Portal.MYSHOP);   // <-- always pass your portal enum here
    }

    // GET /products — fetch all products
    public APIResponse getAllProducts() {
        return get(PRODUCTS_PATH);
    }

    // GET /products/{id} — fetch one product by ID
    public APIResponse getProductById(int id) {
        return get(PRODUCTS_PATH + "/" + id);
    }

    // POST /products — create a new product
    public APIResponse createProduct(String name, double price) {
        return post(PRODUCTS_PATH, Map.of(
                "name",  name,
                "price", price
        ));
    }

    // DELETE /products/{id} — delete a product
    public APIResponse deleteProduct(int id) {
        return delete(PRODUCTS_PATH + "/" + id);
    }
}
```

**Methods inherited from `ApiClient` that you can use:**

| Method | What It Does |
|--------|-------------|
| `get(path)` | Sends a GET request, returns the response |
| `post(path, body)` | Sends a POST request with a JSON body |
| `put(path, body)` | Sends a PUT request with a JSON body |
| `patch(path, body)` | Sends a PATCH request with a JSON body |
| `delete(path)` | Sends a DELETE request |
| `deserialize(response, MyClass.class)` | Parses the JSON response into a Java object |

The base URL is automatically taken from `api.base.url` in `myshop.properties`,
so you only write the path (`/products`, not the full URL).

---

## Step 6 — Create the UI Test Class

Now write the actual tests that open a real browser and click through the UI.

Create a new file at:
```
src/test/java/com/framework/portals/myshop/ui/MyShopLoginTest.java
```

```java
package com.framework.portals.myshop.ui;

import com.framework.core.base.BasePortalTest;
import com.framework.core.config.Portal;
import com.framework.portals.myshop.pages.MyShopHomePage;
import com.framework.portals.myshop.pages.MyShopLoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("MyShop - Authentication")   // shown in Allure report as the feature name
@Tag("myshop")                         // lets you run only myshop tests: -Dgroups=myshop
@Tag("ui")
class MyShopLoginTest extends BasePortalTest {

    MyShopLoginTest() {
        super(Portal.MYSHOP);   // <-- always pass your portal enum here
    }

    @Test
    @DisplayName("Valid login lands on the home page")
    @Story("Login")
    void validLoginLandsOnHomePage() {
        // Arrange — set up the starting point
        MyShopLoginPage loginPage = new MyShopLoginPage().open();

        // Act — perform the action being tested
        MyShopHomePage homePage = loginPage.loginWithDefaultCredentials();

        // Assert — check the expected outcome
        assertThat(homePage.isLoaded())
                .as("Home page should load after a valid login")
                .isTrue();
    }

    @Test
    @DisplayName("Valid login shows welcome message")
    @Story("Login")
    void validLoginShowsWelcomeMessage() {
        MyShopHomePage homePage = new MyShopLoginPage()
                .open()
                .loginWithDefaultCredentials();

        assertThat(homePage.getWelcomeText())
                .as("Welcome banner should contain 'Welcome'")
                .containsIgnoringCase("welcome");
    }

    @Test
    @DisplayName("Wrong password shows an error message")
    @Story("Login - Negative")
    void wrongPasswordShowsError() {
        // Arrange + Act
        MyShopLoginPage loginPage = new MyShopLoginPage()
                .open()
                .enterEmail("user@myshop.com")
                .enterPassword("wrongpassword123");

        loginPage.clickLogin();

        // Assert
        assertThat(loginPage.isErrorVisible())
                .as("An error message should appear for a wrong password")
                .isTrue();

        assertThat(loginPage.getErrorMessage())
                .containsIgnoringCase("invalid");
    }

    @Test
    @DisplayName("Empty email shows a validation error")
    @Story("Login - Negative")
    void emptyEmailShowsError() {
        MyShopLoginPage loginPage = new MyShopLoginPage()
                .open()
                .enterEmail("")
                .enterPassword("anypassword");

        loginPage.clickLogin();

        assertThat(loginPage.isErrorVisible())
                .as("Validation error should appear when email is blank")
                .isTrue();
    }
}
```

**What every test must follow — the Arrange / Act / Assert pattern:**

```
Arrange  → set up the starting state (open the page, prepare data)
Act      → do the thing you are testing (click, type, submit)
Assert   → check that the result is what you expected
```

---

## Step 7 — Create the API Test Class

Write tests that call the REST API directly — no browser needed.

Create a new file at:
```
src/test/java/com/framework/portals/myshop/api/MyShopProductApiTest.java
```

```java
package com.framework.portals.myshop.api;

import com.framework.core.base.BaseApiTest;
import com.framework.core.config.Portal;
import com.framework.core.utils.DataFactory;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("MyShop - Products API")
@Tag("myshop")
@Tag("api")
class MyShopProductApiTest extends BaseApiTest {

    MyShopProductApiTest() {
        super(Portal.MYSHOP);   // <-- always pass your portal enum here
    }

    @Test
    @DisplayName("GET /products returns 200 with a list of products")
    @Story("Products API")
    void getAllProductsReturns200() {
        MyShopProductApi api = new MyShopProductApi(playwright);

        APIResponse response = api.getAllProducts();

        assertThat(response.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);
    }

    @Test
    @DisplayName("GET /products/1 returns the product with id 1")
    @Story("Products API")
    void getProductByIdReturns200() {
        MyShopProductApi api = new MyShopProductApi(playwright);

        APIResponse response = api.getProductById(1);

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.text())
                .as("Response body should contain the product id")
                .contains("\"id\"");
    }

    @Test
    @DisplayName("POST /products creates a new product and returns 201")
    @Story("Products API")
    void createProductReturns201() {
        MyShopProductApi api = new MyShopProductApi(playwright);

        // DataFactory generates random data so each test run uses fresh values
        String productName = "Test Product " + DataFactory.randomAlphanumeric(6);
        APIResponse response = api.createProduct(productName, 19.99);

        assertThat(response.status())
                .as("Status code should be 201 Created")
                .isEqualTo(201);
    }

    @Test
    @DisplayName("DELETE /products/1 returns 200")
    @Story("Products API")
    void deleteProductReturns200() {
        MyShopProductApi api = new MyShopProductApi(playwright);

        APIResponse response = api.deleteProduct(1);

        assertThat(response.status()).isEqualTo(200);
    }
}
```

**Key difference between UI tests and API tests:**

| | UI Test | API Test |
|---|---|---|
| Extends | `BasePortalTest` | `BaseApiTest` |
| Uses browser? | Yes (Playwright opens Chrome/Firefox) | No |
| Speed | Slower (~5–30 s per test) | Faster (~0.5–2 s per test) |
| Needs `playwright` variable? | No (browser is managed automatically) | Yes — pass `playwright` to your API helper |

---

## Step 8 — Run Your Tests

**Run only your new portal's tests:**
```bash
mvn test -Dgroups=myshop -Dheadless=true
```

**Run only UI tests for your portal:**
```bash
mvn test -Dgroups="myshop & ui" -Dheadless=true
```

**Run only API tests for your portal:**
```bash
mvn test -Dgroups="myshop & api" -Dheadless=true
```

**Run a single specific test class:**
```bash
mvn test -Dtest="MyShopLoginTest" -Dheadless=true
```

**Run a single specific test method:**
```bash
mvn test -Dtest="MyShopLoginTest#validLoginLandsOnHomePage" -Dheadless=true
```

**Run everything (all portals):**
```bash
mvn test -Dheadless=true
```

**Run with a visible browser (useful when debugging):**
```bash
mvn test -Dtest="MyShopLoginTest" -Dheadless=false
```

---

## Step 9 — View the Allure Report

After running tests, generate and open the HTML report:

```bash
mvn allure:serve
```

This opens a browser with a full report showing:
- Which tests passed / failed
- Screenshots on failure
- Playwright trace files (click-by-click replay of the test)
- How long each test took

---

## Checklist — Did You Do Everything?

Use this as a final check before you consider your portal done:

- [ ] Added `MYSHOP("myshop")` to `Portal.java`
- [ ] Created `src/main/resources/portals/myshop.properties` with real URLs and credentials
- [ ] Created at least one page object in `portals/myshop/pages/`
- [ ] All page object constructors call `super(Portal.MYSHOP)`
- [ ] Created an API helper in `portals/myshop/api/` (if the portal has an API)
- [ ] API helper constructor calls `super(playwright, Portal.MYSHOP)`
- [ ] Created UI tests in `src/test/java/com/framework/portals/myshop/ui/`
- [ ] UI test class extends `BasePortalTest` and constructor calls `super(Portal.MYSHOP)`
- [ ] Created API tests in `src/test/java/com/framework/portals/myshop/api/`
- [ ] API test class extends `BaseApiTest` and constructor calls `super(Portal.MYSHOP)`
- [ ] All tests have `@Tag("myshop")` so they can be run in isolation
- [ ] All tests have `@DisplayName(...)` with a clear human-readable description
- [ ] Ran `mvn test -Dgroups=myshop` and all tests pass

---

## Common Mistakes

| Mistake | What Goes Wrong | Fix |
|---------|----------------|-----|
| Wrong key in `Portal.java` | Properties file not found at runtime | Key in `Portal("myshop")` must exactly match the filename `myshop.properties` |
| Forgetting `super(Portal.MYSHOP)` in page object | Config not loaded, `portalConfig` is null | Every portal page object constructor must call `super(Portal.MYSHOP)` |
| Using `isVisible()` instead of `waitForVisible()` for slow elements | Test fails because element hasn't appeared yet | Use `waitForVisible()` when an element takes time to load after an action |
| Hardcoding credentials in test files | Tests break when password changes | Always use `portalConfig.getUsername()` / `portalConfig.getPassword()` |
| Putting UI and API test files in the wrong folders | Tests harder to find and filter | UI tests go in `portals/myshop/ui/`, API tests in `portals/myshop/api/` |
| Touching files inside `core/` | Risk of breaking all other portals | Never edit anything inside `core/` — it is shared by everyone |

---

## Quick Reference — Full File List for a New Portal

```
# Edit this file (1 line added):
src/main/java/com/framework/core/config/Portal.java

# Create these files:
src/main/resources/portals/myshop.properties

src/main/java/com/framework/portals/myshop/pages/MyShopLoginPage.java
src/main/java/com/framework/portals/myshop/pages/MyShopHomePage.java

src/main/java/com/framework/portals/myshop/api/MyShopProductApi.java

src/test/java/com/framework/portals/myshop/ui/MyShopLoginTest.java
src/test/java/com/framework/portals/myshop/api/MyShopProductApiTest.java
```
