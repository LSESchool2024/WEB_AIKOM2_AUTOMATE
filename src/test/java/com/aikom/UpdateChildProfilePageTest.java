package com.aikom;

import com.aikom.pages.AikomLoginPage;
import com.aikom.pages.AvailableServicesPage;
import com.aikom.pages.MainPageAikom;
import com.aikom.pages.SearchChildInRegistryPage;
import com.aikom.pages.UpdateChildProfilePage;
import org.testng.annotations.Test;

/**
 * UI tests for the page: "Оновити дані освітнього профілю" (UpdateChildProfilePage)
 *
 * Передумова: на сторінці "Пошук дитини в Реєстрі" виконуємо
 * .searchAndOpenProfileChild(fullNameInput) — після чого відкривається сторінка оновлення профілю.
 */
public class UpdateChildProfilePageTest extends BaseTest {

    private static final String FULL_NAME_INPUT = "Марчук Макар Геннадійович, 3-3-Є";

    @Test(description = "Smoke: Перевірити наявність кнопки 'Додати' на сторінці оновлення профілю дитини")
    public void testAddButtonPresence() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        SearchChildInRegistryPage searchPage = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        searchPage
                .verifyHeader()
                .searchAndOpenProfileChild(FULL_NAME_INPUT);

        new UpdateChildProfilePage()
                .verifyAddButtonPresent();
    }

    @Test(description = "Smoke: Клік по кнопці 'Додати' на сторінці оновлення профілю дитини")
    public void testClickAddButton() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        SearchChildInRegistryPage searchPage = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        searchPage
                .verifyHeader()
                .searchAndOpenProfileChild(FULL_NAME_INPUT);

        new UpdateChildProfilePage()
                .verifyAddButtonPresent()
                .clickAddButton();
        // Подальші перевірки (поява форми/діалогу) можуть бути додані окремо за потреби
    }

    @Test(description = "Flow: після збереження країни натиснути кнопку 'Далі' (clickNextButton())")
    public void testClickNextButton() {
        final String countryValue = "Україна";

        // Login
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        // Navigate to the service
        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        // Open search page for the service and select a child
        SearchChildInRegistryPage searchPage = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        searchPage
                .verifyHeader()
                .searchAndOpenProfileChild(FULL_NAME_INPUT); // navigates to UpdateChildProfilePage

        // Complete modal flow to add country and return to UpdateChildProfilePage
        new com.aikom.pages.UpdateChildAddressModal()
                .completeAddCountryFlow(countryValue)
                .verifyNextButtonEnabled()
                .clickNextButton();
        // If navigation happens to a specific next page, replace the return type and add verifications here.
    }


}
