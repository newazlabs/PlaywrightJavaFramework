# Portal Template — Quick Start

Copy this folder, do **3 find-and-replace operations**, move the files.  
No other changes needed.

---

## Step 1 — Copy this folder

Duplicate the entire `src/template/` folder somewhere on your desktop
(or any scratch location). You will rename and move the files in the next steps.

---

## Step 2 — Do 3 Find & Replace operations

Open every file in the copied folder and replace these three placeholders.
Every modern editor (IntelliJ, VS Code) can do this across all files at once
with **Ctrl + Shift + H** (Windows/Linux) or **Cmd + Shift + H** (Mac).

| Find (exact, case-sensitive) | Replace with | Example |
|------------------------------|--------------|---------|
| `PortalName` | Your portal name in PascalCase | `MyShop` |
| `portalname` | Your portal name in lowercase | `myshop` |
| `PORTAL_NAME` | Your portal name in UPPERCASE | `MYSHOP` |

> **Important:** Replace in this order — longest first — so one replacement
> does not partially overlap another.

---

## Step 3 — Rename the files

After the replacements, rename each file to match:

| Template filename | Rename to |
|-------------------|-----------|
| `portalname.properties` | `myshop.properties` |
| `PortalNameLoginPage.java` | `MyShopLoginPage.java` |
| `PortalNameHomePage.java` | `MyShopHomePage.java` |
| `PortalNameApi.java` | `MyShopApi.java` |
| `PortalNameLoginTest.java` | `MyShopLoginTest.java` |
| `PortalNameApiTest.java` | `MyShopApiTest.java` |

---

## Step 4 — Move files to the right folders

| File | Move to |
|------|---------|
| `myshop.properties` | `src/main/resources/portals/` |
| `MyShopLoginPage.java` | `src/main/java/com/framework/portals/myshop/pages/` |
| `MyShopHomePage.java` | `src/main/java/com/framework/portals/myshop/pages/` |
| `MyShopApi.java` | `src/main/java/com/framework/portals/myshop/api/` |
| `MyShopLoginTest.java` | `src/test/java/com/framework/portals/myshop/ui/` |
| `MyShopApiTest.java` | `src/test/java/com/framework/portals/myshop/api/` |

Create the folders if they do not exist yet.

---

## Step 5 — Register the portal (1 line edit)

Open:
```
src/main/java/com/framework/core/config/Portal.java
```

Add your portal as the last entry:

```java
public enum Portal {
    ADMIN("admin"),
    CUSTOMER("customer"),
    ORANGEHRM("orangehrm"),
    SAUCEDEMO("saucedemo"),
    MYSHOP("myshop");       // <-- add this line
    ...
}
```

The string `"myshop"` must be lowercase and must exactly match your
`.properties` filename (without the extension).

---

## Step 6 — Fill in the real values

Open `myshop.properties` and replace the placeholder URLs and credentials
with the real ones for your portal.

Open each page object file and replace the placeholder CSS selectors
(`#placeholder-...`) with the real selectors from your portal's HTML.

---

## Step 7 — Run your tests

```bash
# Run all tests for your new portal
mvn test -Dgroups=myshop -Dheadless=true

# Run with a visible browser (useful while filling in selectors)
mvn test -Dgroups=myshop -Dheadless=false
```

---

For the full explanation of every method and concept, see:
```
src/doc/HOW_TO_ADD_A_PORTAL.md
```
