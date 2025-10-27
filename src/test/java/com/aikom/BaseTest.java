package com.aikom;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1366x768";
        Configuration.timeout = 10000;
        Configuration.headless = false; // Set to true for CI/CD pipeline
    }
    
    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}
