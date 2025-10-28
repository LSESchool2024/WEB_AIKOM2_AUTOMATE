package com.aikom.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page Object: Модальне вікно "Внести дані" для додавання адреси проживання.
 *
 * Відкривається після кліку по кнопці "Додати" на сторінці UpdateChildProfilePage.
 */
public class UpdateChildAddressModal {

    // Root dialog by role and title
    private final SelenideElement dialogRoot = $x("//div[@role='dialog' and .//div[contains(@class,'MuiDialogTitle-root')]//text()[normalize-space()='Внести дані']]");

    private final SelenideElement dialogTitle = $x("//div[contains(@class,'MuiDialogTitle-root')][normalize-space()='Внести дані']");

    // Loaders (global/component) that can appear under the modal as well
    private final SelenideElement pageLoader = $("[data-xpath='loader']");
    private final SelenideElement componentLoader = $("[data-xpath='component-loader']");

    // Required input inside the modal: Назва країни проживання
    // Example input markup: <input id="ef7a8lg-realState" name="data[realState]-input" ...>
    private final SelenideElement realStateInput = $x("//div[@role='dialog']//input[@name='data[realState]-input']");

    // Dropdown listbox for MUI Autocomplete. Note: MUI renders options in a body-level Popper (portal),
    // so the listbox is often OUTSIDE the dialog DOM. Use a global locator that targets the visible popper list.
    private final SelenideElement listbox = $x("//div[contains(@class,'MuiAutocomplete-popper') and contains(@style,'display: block')]//ul[@role='listbox'] | //ul[@role='listbox' and not(ancestor::div[@role='dialog'])]");

    // Buttons
    private final SelenideElement saveButton = $x("//div[@role='dialog']//button[@name='data[submit]' and normalize-space()='Зберегти']");
    private final SelenideElement cancelButton = $x("//div[@role='dialog']//button[@name='data[cancel]' and normalize-space()='Відмінити']");

    private void waitUntilOpened() {
        if (componentLoader.exists()) componentLoader.shouldBe(hidden);
        if (pageLoader.exists()) pageLoader.shouldBe(hidden);
        dialogRoot.should(exist).shouldBe(visible);
        dialogTitle.shouldBe(visible).shouldHave(text("Внести дані"));
    }

    @Step("Перевірити, що модальне вікно 'Внести дані' відкрите")
    public UpdateChildAddressModal verifyOpened() {
        waitUntilOpened();
        return this;
    }

    @Step("Перевірити наявність поля 'Назва країни проживання' (realState)")
    public UpdateChildAddressModal verifyRealStateInputPresent() {
        waitUntilOpened();
        realStateInput.should(exist).shouldBe(visible, enabled);
        return this;
    }

    @Step("Ввести значення у поле 'Назва країни проживання': {value}")
    public UpdateChildAddressModal typeRealState(String value) {
        verifyRealStateInputPresent();
        realStateInput.clear();
        realStateInput.setValue(value);
        return this;
    }

    @Step("Відкрити випадаючий список країн (якщо ще не відкритий)")
    public UpdateChildAddressModal openRealStateDropdown() {
        verifyRealStateInputPresent();
        // primary action: click input to trigger popper
        realStateInput.click();
        // wait for the MUI popper listbox to appear (rendered in a portal)
        if (!listbox.is(visible)) {
            // fallback: try clicking the popup indicator
            SelenideElement popupBtn = $x("//div[@role='dialog']//button[contains(@class,'MuiAutocomplete-popupIndicator')]");
            if (popupBtn.exists()) {
                popupBtn.click();
            }
        }
        listbox.shouldBe(visible);
        // ensure it has at least one option rendered
        listbox.$x(".//li").should(exist);
        return this;
    }

    @Step("Вибрати у випадаючому списку значення країни: {value}")
    public UpdateChildAddressModal selectRealStateOption(String value) {
        // переконуємося, що список відкритий
        if (!listbox.exists() || !listbox.is(visible)) {
            openRealStateDropdown();
        }
        // шукаємо елемент з точним (нормалізованим) текстом
        SelenideElement option = listbox.$x(".//li[normalize-space(.)='" + value + "']");
        if (!option.exists()) {
            // fallback: contains, якщо верстка відрізняється
            option = listbox.$x(".//li[contains(normalize-space(.), '" + value + "')]");
        }
        option.shouldBe(visible, enabled).click();
        // чекаємо закриття списку після вибору
        listbox.shouldNotBe(visible);
        return this;
    }

    @Step("Перевірити значення у полі 'Назва країни проживання' дорівнює: {expected}")
    public UpdateChildAddressModal verifyRealStateValue(String expected) {
        // Для MUI Autocomplete значення зазвичай знаходиться у value інпуту
        realStateInput.shouldHave(value(expected));
        return this;
    }

    @Step("Проскролити до кнопки 'Зберегти' у модальному вікні")
    public UpdateChildAddressModal scrollToSaveButton() {
        saveButton.scrollIntoView(true);
        return this;
    }

    @Step("Перевірити, що кнопка 'Зберегти' присутня та неактивна")
    public UpdateChildAddressModal verifySaveButtonDisabled() {
        saveButton.should(exist).shouldBe(visible).shouldBe(disabled);
        return this;
    }

    @Step("Перевірити, що кнопка 'Зберегти' присутня та активна")
    public UpdateChildAddressModal verifySaveButtonEnabled() {
        saveButton.should(exist).shouldBe(visible, enabled).shouldHave(text("Зберегти"));
        return this;
    }

    @Step("Натиснути кнопку 'Зберегти' у модальному вікні")
    public UpdateChildProfilePage clickSaveButton() {
        // переконатися, що кнопка активна і у вʼюпорті
        scrollToSaveButton();
        saveButton.shouldBe(visible, enabled).click();
        // після кліку може зʼявитися loader; дочекаймося його зникнення і закриття діалогу
        if (componentLoader.exists()) componentLoader.shouldBe(hidden);
        if (pageLoader.exists()) pageLoader.shouldBe(hidden);
        dialogRoot.shouldNotBe(visible);
        // повертаємось на сторінку профілю
        return new UpdateChildProfilePage();
    }

    @Step("Сценарій: .verifyAddButtonPresent() → .clickAddButton() → .verifyOpened() → .verifyRealStateInputPresent() → .typeRealState({countryValue}) → .openRealStateDropdown() → .selectRealStateOption({countryValue}) → .verifyRealStateValue({countryValue}) → .scrollToSaveButton() → .verifySaveButtonEnabled() → .clickSaveButton() → .verifyAddButtonPresent()")
    public UpdateChildProfilePage completeAddCountryFlow(String countryValue) {
        return new UpdateChildProfilePage()
                .verifyAddButtonPresent()
                .clickAddButton()
                .verifyOpened()
                .verifyRealStateInputPresent()
                .typeRealState(countryValue)
                .openRealStateDropdown()
                .selectRealStateOption(countryValue)
                .verifyRealStateValue(countryValue)
                .scrollToSaveButton()
                .verifySaveButtonEnabled()
                .clickSaveButton()
                .verifyAddButtonPresent();
    }
}
