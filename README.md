# Serenity-screenplay-saucedemo

## Summary
Automation of the login flow on SauceDemo using Serenity BDD + Screenplay + Cucumber.  
Main scenario: open the website, log in with valid credentials, and verify that the inventory page and product list are displayed.

---

## What this project does
- Opens https://www.saucedemo.com/  
- Logs in with credentials (e.g. `standard_user` / `secret_sauce`)  
- Verifies that the inventory page loads and products are visible  
- Generates detailed HTML Serenity reports after execution

---

## Requirements
- macOS  
- Java 17 (as specified in `pom.xml`)  
- Maven installed  
- Google Chrome installed  
- Internet connection to download dependencies

---

## Main structure
- Features: `src/test/resources/features/*.feature`  
- Step definitions: `src/test/java/com/saucedemo/stepdefinitions/`  
- Tasks / Questions: `src/test/java/com/saucedemo/tasks/`, `.../questions/`

---

## Useful commands

Run tests only:
```bash
mvn -U -Dwebdriver.driver=chrome clean verify
```

Run tests and immediately open the report on macOS:
```bash
# Runs tests and opens Serenity report index
mvn -U -Dwebdriver.driver=chrome clean verify && open target/site/serenity/index.html
```

Run in headless mode (without opening the browser):
```bash
mvn -U -Dwebdriver.driver=chrome -Dchrome.switches="--headless=new" clean verify && open target/site/serenity/index.html
```

Generate only the report from existing results:
```bash
mvn net.serenity-bdd:serenity-maven-plugin:4.2.34:aggregate && open target/site/serenity/index.html
```

Run tests filtered by tag:
```bash
mvn -U -Dwebdriver.driver=chrome -Dcucumber.filter.tags="@myTag" clean verify
```

---

## Cleanup and forced downloads
Remove corrupted dependencies or force redownload:
```bash
rm -rf ~/.m2/repository/net/serenity-bdd
rm -rf ~/.m2/repository/io/cucumber
mvn -U clean verify
```

---

## Screenshots
- Default behavior: Serenity takes screenshots associated with steps (one per step).  
- If you want more detail (per action) or fewer (only on failures), you can configure `serenity.take.screenshots` in `serenity.properties`:

Examples (`serenity.properties` at project root):
```properties
# Screenshot per step (default)
serenity.take.screenshots=FOR_EACH_STEP

# Screenshot per action (more images)
# serenity.take.screenshots=FOR_EACH_ACTION

# Only capture on failures
# serenity.take.screenshots=FOR_FAILURES
```

Note: running in headless mode may affect screenshot appearance; use `--headless=new` for modern Chrome compatibility.

---

## Where to view reports
- Main report: `target/site/serenity/index.html`  
- Alternative (raw results): `target/serenity`

Open from macOS terminal:
```bash
open target/site/serenity/index.html
```

---

## Quick troubleshooting
- If Maven shows `BUILD SUCCESS` but no tests run: make sure the runner exists (`src/test/java/com/saucedemo/RunCucumberTest.java`) and that steps are not pending.  
- If VS Code shows errors but Maven builds fine: run `Java: Clean the Java language server workspace` in VS Code and reload the window.  
- Chromedriver issues: WebDriverManager handles the driver; if it fails, ensure Chrome is installed and up to date.

Developed by: David Lujan, PhD in nothing.
