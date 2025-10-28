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

    // Child data autocomplete input (appears after click Search)
    private final SelenideElement childDataInput = $x("//input[@name='data[childData]-input']");

    // "Далі" (Next) submit button appears after search/autocomplete stage
    private final SelenideElement nextButton = $x("//button[@name='data[submit]' and normalize-space()='Далі']");

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

    /**
     * @param raw  pattern string:  Марчук Макар Геннадійович, 3-3-Є
     * @return
     */
    @Step("Ввести у поле прізвища перше слово з рядка: {raw}")
    public SearchChildInRegistryPage enterLastNameFromFullString(String raw) {
        waitUntilPageReady();
        String input = raw == null ? "" : raw.trim();
        String surname;
        int spaceIdx = input.indexOf(' ');
        if (spaceIdx > 0) {
            surname = input.substring(0, spaceIdx);
        } else {
            surname = input; // якщо пробілів немає — беремо увесь рядок
        }
        lastNameInput.should(exist).shouldBe(visible, enabled).setValue(surname);
        return this;
    }

    @Step("Перевірити значення у полі прізвища: {expected}")
    public SearchChildInRegistryPage verifyLastNameValue(String expected) {
        lastNameInput.shouldHave(value(expected));
        return this;
    }

    /**
     * Composite step: enter surname parsed from full name string and verify it in the input field.
     * Example: fullName="Марчук Макар Геннадійович, 3-3-Є" -> shortName="Марчук".
     *
     * @param fullName  Full name string from which the first word (surname) will be taken
     * @param shortName Expected surname value to verify in the input
     */
    @Step("Крок: ввести прізвище з повного ПІБ та перевірити значення (fullName='{fullName}', expected='{shortName}')")
    public SearchChildInRegistryPage enterLastNameFromFullStringAndVerify(String fullName, String shortName) {
        return this
                .enterLastNameFromFullString(fullName)
                .verifyLastNameValue(shortName);
    }

    // ========================= New actions for Search -> Autocomplete =========================

    @Step("Натиснути кнопку 'Знайти' і дочекатися появи поля автодоповнення")
    public SearchChildInRegistryPage clickSearchButton() {
        waitUntilPageReady();
        // Після введення прізвища кнопка має стати активною
        searchButton.shouldBe(visible, enabled).click();
        // Очікуємо появу та готовність інпуту автодоповнення
        childDataInput.should(exist).shouldBe(visible, enabled);
        return this;
    }

    @Step("Перевірити наявність поля автодоповнення для пошуку дитини")
    public SearchChildInRegistryPage verifyChildDataAutocompletePresent() {
        childDataInput.should(exist).shouldBe(visible);
        return this;
    }

    @Step("Відкрити випадаючий список результатів у полі автодоповнення")
    public SearchChildInRegistryPage openChildDataDropdown() {
        childDataInput.shouldBe(visible, enabled).click();
        // Очікуємо появу списку з підказками (Material UI listbox)
        $x("//ul[@role='listbox']").shouldBe(visible);
        return this;
    }

    @Step("Переконатися, що у списку є елемент з повним ім'ям: {expectedFullName}")
    public SearchChildInRegistryPage verifySuggestionPresent(String expectedFullName) {
        SelenideElement listbox = $x("//ul[@role='listbox']").shouldBe(visible);
        // Шукаємо елемент, що містить очікуваний текст (нормалізований)
        listbox.$x(".//li[contains(normalize-space(.), '" + expectedFullName + "')]").shouldBe(visible);
        return this;
    }

    @Step("Крок: пошук дитини за прізвищем і перевірка підказок (full='{fullName}', expect option='{expectedFullName}')")
    public SearchChildInRegistryPage searchAndOpenSuggestions(String fullName, String expectedFullName) {
        return this
                .enterLastNameFromFullString(fullName)
                .clickSearchButton()
                .verifyChildDataAutocompletePresent()
                .openChildDataDropdown()
                .verifySuggestionPresent(expectedFullName);
    }

    @Step("Перевірити кнопку 'Далі' (наявність та активний стан)")
    public SearchChildInRegistryPage verifyNextButtonPresentEnabled() {
        nextButton.should(exist).shouldBe(visible, enabled).shouldHave(text("Далі"));
        return this;
    }

    @Step("Натиснути кнопку 'Далі'")
    public SearchChildInRegistryPage clickNextButton() {
        nextButton.shouldBe(visible, enabled).click();
        // Після кліку очікуємо завершення можливого завантаження
        if (componentLoader.exists()) componentLoader.shouldBe(hidden);
        if (pageLoader.exists()) pageLoader.shouldBe(hidden);
        return this;
    }

    // ========================= Select option in autocomplete =========================

    @Step("Вибрати у випадаючому списку елемент з ПІБ: {fullName}")
    public SearchChildInRegistryPage selectSuggestion(String fullName) {
        // якщо список ще не відкритий — відкриваємо
        if (!$x("//ul[@role='listbox']").exists()) {
            openChildDataDropdown();
        } else {
            $x("//ul[@role='listbox']").shouldBe(visible);
        }
        // клікаємо по потрібному елементу
        $x("//ul[@role='listbox']").
                $x(".//li[contains(normalize-space(.), '" + fullName + "')]")
                .shouldBe(visible)
                .click();
        // чекаємо, поки список закриється
        $x("//ul[@role='listbox']").shouldNotBe(visible);
        return this;
    }

    @Step("Перевірити, що поле автодоповнення містить вибране значення: {expected}")
    public SearchChildInRegistryPage verifyChildDataSelectedValue(String expected) {
        // В більшості випадків вибране значення потрапляє у value інпуту
        boolean valueMatches = childDataInput.exists() && childDataInput.getValue() != null && childDataInput.getValue().contains(expected);
        if (!valueMatches) {
            // Альтернатива для режиму з 'chips'
            $x("//div[contains(@class,'MuiAutocomplete-root')]//span[contains(@class,'MuiChip-label') and contains(normalize-space(.), '" + expected + "')]")
                    .should(exist);
        }
        return this;
    }

    @Step("Крок: знайти за прізвищем, відкрити підказки та вибрати ПІБ: {fullName}")
    public SearchChildInRegistryPage searchAndOpenProfileChild(String fullNameInput) {
        return this
                .enterLastNameFromFullString(fullNameInput)
                .clickSearchButton()
                .verifyChildDataAutocompletePresent()
                .openChildDataDropdown()
                .verifySuggestionPresent(fullNameInput)
                .selectSuggestion(fullNameInput)
                .verifyChildDataSelectedValue(fullNameInput)
                .verifyNextButtonPresentEnabled()
                .clickNextButton();
    }
}
