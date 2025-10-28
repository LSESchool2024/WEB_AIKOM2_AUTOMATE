package com.aikom.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page Object: "Оновити дані освітнього профілю" (Update Child Profile)
 *
 * Appears after clicking "Далі" on the page "Пошук дитини в Реєстрі".
 * Contains an EditGrid section "Адреса фактичного місця проживання" з кнопкою "Додати".
 */
public class UpdateChildProfilePage {

    // Optional header to confirm we are on the correct page
    private final SelenideElement header = $x("//h1[normalize-space()='Оновити дані освітнього профілю']");

    // Global/component loaders
    private final SelenideElement pageLoader = $("[data-xpath='loader']");
    private final SelenideElement componentLoader = $("[data-xpath='component-loader']");

    // The "Додати" button inside the address EditGrid
    // Markup example: <button ...><div .../> <p ...>Додати</p></button>
    private final SelenideElement addButton = $x("//button[.//p[normalize-space()='Додати'] or normalize-space()='Додати']");

    // The "Далі" (Next) button that becomes enabled after saving in the modal
    // Example markup from description:
    // <button lang="uk" class="btn btn-primary btn-md bootstrapFormStyles mdtuddm-button-primary" type="submit" name="data[submit]" ref="button">Далі</button>
    private final SelenideElement nextButton = $x("//button[@name='data[submit]' and normalize-space()='Далі']");

    private void waitUntilPageReady() {
        if (componentLoader.exists()) componentLoader.shouldBe(hidden);
        if (pageLoader.exists()) pageLoader.shouldBe(hidden);
        // Header may or may not always be present; if exists, ensure visible
        if (header.exists()) header.shouldBe(visible);
    }

    @Step("Перевірити наявність кнопки 'Додати' на сторінці оновлення профілю дитини")
    public UpdateChildProfilePage verifyAddButtonPresent() {
        waitUntilPageReady();
        addButton.should(exist).shouldBe(visible, enabled);
        return this;
    }

    @Step("Клік по кнопці 'Додати' на сторінці оновлення профілю дитини")
    public UpdateChildAddressModal clickAddButton() {
        verifyAddButtonPresent();
        addButton.click();
        // Після кліку має відкритися модальне вікно "Внести дані" — дочекаємось його появи
        if (componentLoader.exists()) componentLoader.shouldBe(hidden);
        if (pageLoader.exists()) pageLoader.shouldBe(hidden);
        return new UpdateChildAddressModal().verifyOpened();
    }

    @Step("Перевірити, що кнопка 'Далі' присутня та активна")
    public UpdateChildProfilePage verifyNextButtonEnabled() {
        waitUntilPageReady();
        nextButton.should(exist).shouldBe(visible, enabled).shouldHave(text("Далі"));
        return this;
    }

    @Step("Натиснути кнопку 'Далі' на сторінці оновлення профілю дитини")
    public UpdateChildProfileNextPage clickNextButton() {
        // Переконуємось, що кнопка доступна для кліку
        verifyNextButtonEnabled();
        nextButton.scrollIntoView(true).click();
        // Після кліку можливі лоадери — чекаємо їх зникнення
        if (componentLoader.exists()) componentLoader.shouldBe(hidden);
        if (pageLoader.exists()) pageLoader.shouldBe(hidden);
        // Повертаємо наступну сторінку та валідуємо, що вона відкрита
        return new UpdateChildProfileNextPage().verifyOpened();
    }
}
