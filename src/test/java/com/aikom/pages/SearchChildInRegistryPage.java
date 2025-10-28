package com.aikom.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page Object: "Пошук дитини в Реєстрі"
 * Reusable page that opens after selecting service
 * "Оновлення освітнього профілю дитини".
 */
public class SearchChildInRegistryPage {

    // Header H1 "Пошук дитини в Реєстрі"
    private final SelenideElement header = $x("//h1[normalize-space()='Пошук дитини в Реєстрі']");

    // Label and input for last name (IDs are dynamic, use robust locators)
    private final SelenideElement lastNameLabel = $x("//label[contains(normalize-space(),'Прізвище для пошуку')]");
    private final SelenideElement lastNameInput = $x("//input[@name='data[lastName]']");

    // "Знайти" button (secondary, initially disabled)
    private final SelenideElement searchButton = $x("//button[normalize-space()='Знайти']");

    // Loaders that should be hidden
    private final SelenideElement pageLoader = $("[data-xpath='loader']");
    private final SelenideElement componentLoader = $("[data-xpath='component-loader']");

    private void waitUntilPageReady() {
        if (pageLoader.exists()) pageLoader.shouldBe(hidden);
        if (componentLoader.exists()) componentLoader.shouldBe(hidden);
        // Header presence is the most reliable readiness indicator for this page
        header.shouldBe(visible);
    }

    @Step("Перевірити заголовок сторінки 'Пошук дитини в Реєстрі'")
    public SearchChildInRegistryPage verifyHeader() {
        waitUntilPageReady();
        header.shouldBe(visible).shouldHave(text("Пошук дитини в Реєстрі"));
        return this;
        }

    @Step("Перевірити наявність мітки та поля 'Прізвище для пошуку'")
    public SearchChildInRegistryPage verifyLastNameLabelAndInput() {
        waitUntilPageReady();
        lastNameLabel.should(exist).shouldBe(visible).shouldHave(text("Прізвище для пошуку"));
        lastNameInput.should(exist).shouldBe(visible).shouldHave(name("data[lastName]"));
        return this;
    }

    @Step("Перевірити кнопку 'Знайти' (наявність та неактивний стан)")
    public SearchChildInRegistryPage verifySearchButtonDisabled() {
        waitUntilPageReady();
        searchButton.shouldBe(visible).shouldHave(text("Знайти"));
        // Перевіряємо disabled як атрибут або стан через клас/ARIA
        searchButton.shouldBe(disabled);
        return this;
    }

    @Step("Перевірити всі ключові елементи сторінки 'Пошук дитини в Реєстрі'")
    public SearchChildInRegistryPage verifyAllElements() {
        return this
                .verifyHeader()
                .verifyLastNameLabelAndInput()
                .verifySearchButtonDisabled();
    }
}
