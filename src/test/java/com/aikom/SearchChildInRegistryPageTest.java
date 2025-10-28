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
                .enterLastNameFromFullString(fullName)
                .verifyLastNameValue(shortName);
    }
}
