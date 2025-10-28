package com.aikom.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page Object: Наступна сторінка після натискання кнопки "Далі" на UpdateChildProfilePage.
 *
 * За описом, сторінка містить заголовок:
 * <h1 class="MuiTypography-root jss59 jss24 MuiTypography-body1">Оновити дані освітнього профілю</h1>
 * та активну кнопку "Далі" внизу форми.
 */
public class UpdateChildProfileNextPage {

    // Header to confirm we are on this next step page
    private final SelenideElement header = $x("//h1[normalize-space()='Оновити дані освітнього профілю']");

    // Global/component loaders (можуть з'являтися між кроками)
    private final SelenideElement pageLoader = $("[data-xpath='loader']");
    private final SelenideElement componentLoader = $("[data-xpath='component-loader']");

    // Кнопка "Далі" на цій сторінці (активна)
    private final SelenideElement nextButton = $x("//button[@name='data[submit]' and normalize-space()='Далі']");

    private void waitUntilPageReady() {
        if (componentLoader.exists()) componentLoader.shouldBe(hidden);
        if (pageLoader.exists()) pageLoader.shouldBe(hidden);
        header.should(exist).shouldBe(visible);
    }

    @Step("Перевірити, що відкрита наступна сторінка з заголовком 'Оновити дані освітнього профілю'")
    public UpdateChildProfileNextPage verifyOpened() {
        waitUntilPageReady();
        return this;
    }

    @Step("Проскролити до кнопки 'Далі' на наступній сторінці")
    public UpdateChildProfileNextPage scrollToNextButton() {
        nextButton.scrollIntoView(true);
        return this;
    }

    @Step("Перевірити, що кнопка 'Далі' присутня та активна на наступній сторінці")
    public UpdateChildProfileNextPage verifyNextButtonEnabled() {
        waitUntilPageReady();
        nextButton.should(exist).shouldBe(visible, enabled).shouldHave(text("Далі"));
        return this;
    }

    @Step("Натиснути кнопку 'Далі' на наступній сторінці")
    public UpdateChildProfileNextPage clickNextButton() {
        verifyNextButtonEnabled();
        nextButton.scrollIntoView(true).click();
        if (componentLoader.exists()) componentLoader.shouldBe(hidden);
        if (pageLoader.exists()) pageLoader.shouldBe(hidden);
        // Якщо після цього відкривається ще одна сторінка, замініть тип, що повертається, на відповідний PageObject
        return this;
    }
}
