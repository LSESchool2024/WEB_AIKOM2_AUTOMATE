package com.aikom;

import com.aikom.pages.AikomLoginPage;
import com.aikom.pages.MainPageAikom;
import com.aikom.pages.AvailableServicesPage;
import com.aikom.pages.SearchChildInRegistryPage;
import org.testng.annotations.Test;

/**
 * UI tests for the reusable page: "Пошук дитини в Реєстрі".
 */
public class SearchChildInRegistryPageTest extends BaseTest {

    /**
     * Повний користувацький шлях:
     * - Авторизація
     * - Перехід на "Доступні послуги"
     * - Відкриття послуги "Оновлення освітнього профілю дитини"
     * - Перевірка ключових елементів сторінки "Пошук дитини в Реєстрі"
     */
    @Test(description = "Перевірити наявність заголовка, мітки/поля прізвища та неактивної кнопки 'Знайти'")
    public void testSearchChildInRegistryPageElements() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        SearchChildInRegistryPage page = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        page.verifyAllElements();
    }

    /**
     * Додатково перевіряємо допоміжні методи введення/перевірки значення у полі прізвища.
     */
    @Test(description = "Smoke: введення прізвища та перевірка значення у полі")
    public void testEnterAndVerifyLastName() {
        String fullName = "Марчук Макар Геннадійович, 3-3-Є";
        String shortName= "Марчук";
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        SearchChildInRegistryPage page = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        page.verifyHeader()
                .enterLastNameFromFullStringAndVerify(fullName, shortName);
    }

    /**
     * Клік по кнопці "Знайти" та перевірка появи випадаючого списку з елементом повного імені.
     */
    @Test(description = "Клік 'Знайти' і перевірка, що у підказках присутнє повне ім'я")
    public void testClickSearchAndVerifyDropdownContainsFullName() {
        String fullNameInput = "Марчук Макар Геннадійович, 3-3-Є";
        String expectedFullNameOption = "Марчук Макар Геннадійович, 3-3-Є"; // елемент у списку має містити ПІБ без класу

        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        SearchChildInRegistryPage page = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        page.verifyHeader()
                .enterLastNameFromFullString(fullNameInput)
                .clickSearchButton()
                .verifyChildDataAutocompletePresent()
                .openChildDataDropdown()
                .verifySuggestionPresent(expectedFullNameOption);
    }

    /**
     * Кнопка "Далі": після попередніх кроків має з'явитися та бути клікабельною.
     * Smoke‑перевірка натискання.
     */
    @Test(description = "Перевірити появу та клік по кнопці 'Далі'")
    public void testNextButtonAppearsAndClickable() {
        String fullNameInput = "Марчук Макар Геннадійович, 3-3-Є";

        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        SearchChildInRegistryPage page = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        page.verifyHeader()
                .searchAndOpenProfileChild(fullNameInput);
        // Подальша верифікація залежить від наступної сторінки; наразі smoke‑клік без падіння тесту
    }

    /**
     * Обрати у випадаючому списку саме той рядок, що вводили в методі enterLastNameFromFullString(fullNameInput)
     */
    @Test(description = "Вибір елемента у випадаючому списку, що дорівнює введеному ПІБ")
    public void testClickSearchSelectFullNameFromDropdown() {
        String fullNameInput = "Марчук Макар Геннадійович, 3-3-Є";

        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        SearchChildInRegistryPage page = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        page.verifyHeader()
                .enterLastNameFromFullString(fullNameInput)
                .clickSearchButton()
                .verifyChildDataAutocompletePresent()
                .openChildDataDropdown()
                .selectSuggestion(fullNameInput)
                .verifyChildDataSelectedValue(fullNameInput);
    }
}
