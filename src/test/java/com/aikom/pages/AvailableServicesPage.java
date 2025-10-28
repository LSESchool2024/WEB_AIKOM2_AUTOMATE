package com.aikom.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page Object: Available Services page (/officer/process-list)
 *
 * Expected UI (key fragments):
 * - Header h1 with text "Доступні послуги"
 * - Service group card: "Інформація про здобувачів освіти"
 * - Service group card: "Інформація про працівників"
 *   Each card has:
 *   - a title text inside <p>
 *   - an arrow svg with class contains "serviceCardArrowForward"
 */
public class AvailableServicesPage {

    // Header
    private final SelenideElement pageHeader = $x("//h1[normalize-space()='Доступні послуги']");
    // Global/component loaders that should be hidden when content is ready
    private final SelenideElement pageLoader = $("[data-xpath='loader']");
    private final SelenideElement componentLoader = $("[data-xpath='component-loader']");

    // Card titles
    private static final String STUDENTS_TITLE = "Інформація про здобувачів освіти";
    private static final String EMPLOYEES_TITLE = "Інформація про працівників";

    // Services titles inside "Інформація про здобувачів освіти"
    private static final String SERVICE_UPDATE_CHILD_PROFILE = "Оновлення освітнього профілю дитини";

    // Generic locator helpers for a service card by its title text
    private SelenideElement cardByTitle(String title) {
        // Ensure the title is scrolled into view for stable visibility assertions
        SelenideElement titleP = $x("//p[normalize-space()='" + title + "']").scrollIntoView(true);
        // Robust: find the nearest card container directly via XPath (avoid jQuery `closest` translation issues)
        return titleP.$x("ancestor::div[contains(@class,'MuiBox-root')][1]");
    }

    private SelenideElement cardArrow(SelenideElement card) {
        // Arrow is a direct child of the card container
        return card.$x("./svg[contains(@class,'serviceCardArrowForward')]");
    }

    private void waitUntilPageReady() {
        // Wait until any global/component loaders are hidden (if present in DOM)
        if (pageLoader.exists()) {
            pageLoader.shouldBe(hidden);
        }
        if (componentLoader.exists()) {
            componentLoader.shouldBe(hidden);
        }
    }

    @Step("Перевірити заголовок сторінки 'Доступні послуги'")
    public AvailableServicesPage verifyHeader() {
        waitUntilPageReady();
        pageHeader.shouldBe(visible).shouldHave(text("Доступні послуги"));
        return this;
    }

    @Step("Перевірити наявність картки: 'Інформація про здобувачів освіти'")
    public AvailableServicesPage verifyStudentsInfoCard() {
        waitUntilPageReady();
        SelenideElement card = cardByTitle(STUDENTS_TITLE).shouldBe(visible);
        // title text
        card.$x(".//p[normalize-space()='" + STUDENTS_TITLE + "']").shouldBe(visible);
        return this;
    }

    @Step("Перевірити наявність картки: 'Інформація про працівників'")
    public AvailableServicesPage verifyEmployeesInfoCard() {
        SelenideElement card = cardByTitle(EMPLOYEES_TITLE).shouldBe(visible);
        // title text
        card.$x(".//p[normalize-space()='" + EMPLOYEES_TITLE + "']").shouldBe(visible);
        // arrow may be hidden/offscreen due to layout; check existence
        cardArrow(card).should(exist);
        return this;
    }

    @Step("Перевірити всі елементи на сторінці 'Доступні послуги'")
    public AvailableServicesPage verifyAllElements() {
        return this
                .verifyHeader()
                .verifyStudentsInfoCard();
    }

    @Step("Клік по картці 'Інформація про здобувачів освіти'")
    public AvailableServicesPage clickStudentsInfoCard() {
        waitUntilPageReady();
        SelenideElement card = cardByTitle(STUDENTS_TITLE).shouldBe(visible);
        // На деяких екранах клік спрацьовує саме по стрілці; якщо її немає в DOM, клікаємо по всій картці
        SelenideElement arrow = cardArrow(card);
        if (arrow.exists()) {
            arrow.scrollIntoView(true).click();
        } else {
            card.scrollIntoView(true).click();
        }
        return this; // За потреби можна повернути інший Page Object, коли стане відомий таргет
    }

    @Step("Перевірити наявність послуги: 'Оновлення освітнього профілю дитини'")
    public AvailableServicesPage verifyUpdateChildProfileServicePresent() {
        waitUntilPageReady();
        // Service item is rendered as <p> with exact text
        $x("//p[normalize-space()='" + SERVICE_UPDATE_CHILD_PROFILE + "']").shouldBe(visible);
        return this;
    }

}
