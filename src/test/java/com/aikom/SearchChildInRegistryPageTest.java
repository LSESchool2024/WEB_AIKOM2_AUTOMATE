package com.aikom;

import com.aikom.pages.AikomLoginPage;
import com.aikom.pages.MainPageAikom;
import com.aikom.pages.AvailableServicesPage;
import com.aikom.pages.SearchChildInRegistryPage;
import org.testng.annotations.Test;

/**
 * UI tests for page: "Пошук дитини в Реєстрі".
 */
public class SearchChildInRegistryPageTest extends BaseTest {

    @Test(description = "Перевірка елементів сторінки 'Пошук дитини в Реєстрі'")
    public void testSearchChildInRegistryElements() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        // Відкриваємо сервіс і переходимо на сторінку пошуку
        SearchChildInRegistryPage searchPage = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        // Перевірки ключових елементів
        searchPage
                .verifyHeader()
                .verifyLastNameLabelAndInput()
                .verifySearchButtonDisabled();
    }
}
