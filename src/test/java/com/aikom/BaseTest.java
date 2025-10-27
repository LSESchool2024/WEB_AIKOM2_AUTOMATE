package com.aikom;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1366x768";
        Configuration.timeout = 30000;
        Configuration.headless = false; // Set to true for CI/CD pipeline
        // Configure file download settings - using FOLDER mode instead of PROXY
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.downloadsFolder = "build/downloads";
        Configuration.proxyEnabled = false;
    }
    
    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}
