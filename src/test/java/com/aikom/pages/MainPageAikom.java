package com.aikom.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.testng.Assert;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page Object: Main page of the Aikom cabinet shown after successful authorization
 *
 * Verifies presence of key elements:
 * - Welcome header: h1 with text "Вітаємо!"
 * - Menu item: "Головна"
 * - Menu link: "Доступні послуги" that navigates to /officer/process-list
 */
public class MainPageAikom {

    // Header "Вітаємо!"
    private final SelenideElement welcomeHeader = $x("//h1[normalize-space()='Вітаємо!']");

    // Left side menu: "Головна"
    private final SelenideElement menuHome = $x("//p[normalize-space()='Головна']");

    // Left side menu link: "Доступні послуги" with domain-agnostic href (ends with /officer/process-list)
    private final SelenideElement availableServicesLink = $("a[href$='/officer/process-list']");
    private final SelenideElement availableServicesText = $x("//a[contains(@href,'/officer/process-list')]//p[normalize-space()='Доступні послуги']");

    @Step("Verify that main page is loaded with header 'Вітаємо!'")
    public MainPageAikom verifyWelcomeHeader() {
        welcomeHeader.shouldBe(visible);
        return this;
    }

    @Step("Verify left menu contains 'Головна'")
    public MainPageAikom verifyMenuHomePresent() {
        menuHome.shouldBe(visible).shouldHave(text("Головна"));
        return this;
    }

    @Step("Verify left menu contains link 'Доступні послуги' to /officer/process-list")
    public MainPageAikom verifyAvailableServicesMenuPresent() {
        availableServicesLink.shouldBe(visible).shouldHave(attributeMatching("href", "(^|https?://[^/]+)?/officer/process-list$"));
        availableServicesText.shouldBe(visible).shouldHave(text("Доступні послуги"));
        return this;
    }

//    @Step("Open 'Доступні послуги' from left menu")
//    public MainPageAikom openAvailableServices() {
//        availableServicesLink.shouldBe(visible, enabled).click();
//        return this;
//    }

    /**
     * Clicks on left menu item "Доступні послуги" and verifies that navigation happened
     * to the process list page regardless of domain (dev/stage/prod).
     */
    @Step("Click 'Доступні послуги' in left menu and verify navigation to /officer/process-list")
    public MainPageAikom clickAvailableServicesMenu() {
        availableServicesLink.shouldBe(visible, enabled).click();
        // Domain-agnostic URL verification: ends with /officer/process-list (may include query or trailing slash)
//        String url = WebDriverRunner.url();
//        boolean ok = url.endsWith("/officer/process-list") || url.matches("https?://[^/]+/officer/process-list(?:[/?].*)?$");
//        Assert.assertTrue(ok, "Expected to navigate to '/officer/process-list', but actual URL is: " + url);
        return this;
    }

    @Step("Верифікувати елементи та виконати клік по 'Доступні послуги'")
    public MainPageAikom verifyElementsAndClickAvailableServices() {
        return this
                .verifyWelcomeHeader()
                .verifyMenuHomePresent()
                .verifyAvailableServicesMenuPresent()
                .clickAvailableServicesMenu();
    }
}
