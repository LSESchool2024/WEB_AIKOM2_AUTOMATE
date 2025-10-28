package com.aikom;

import com.aikom.pages.AikomLoginPage;
import com.aikom.pages.AvailableServicesPage;
import com.aikom.pages.MainPageAikom;
import com.aikom.pages.SearchChildInRegistryPage;
import com.aikom.pages.UpdateChildAddressModal;
import com.aikom.pages.UpdateChildProfilePage;
import org.testng.annotations.Test;

/**
 * UI tests for the modal dialog "Внести дані" that opens after clicking "Додати"
 * on the UpdateChildProfilePage (section: Адреса фактичного місця проживання).
 */
public class UpdateChildAddressModalTest extends BaseTest {

    private static final String FULL_NAME_INPUT = "Марчук Макар Геннадійович, 3-3-Є";

    @Test(description = "Smoke: Після кліку 'Додати' модальне вікно відкрите і поле 'Назва країни проживання' присутнє")
    public void testModalOpensAndRealStateInputPresent() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        SearchChildInRegistryPage searchPage = new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();

        searchPage
                .verifyHeader()
                .searchAndOpenProfileChild(FULL_NAME_INPUT);

        UpdateChildAddressModal modal = new UpdateChildProfilePage()
                .verifyAddButtonPresent()
                .clickAddButton();

        modal.verifyOpened()
             .verifyRealStateInputPresent();
    }

    @Test(description = "Smoke: Ввести значення у поле 'Назва країни проживання' у модалі та перевірити його")
    public void testTypeRealStateValue() {
        final String countryValue = "Україна";

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
                .clickAddButton()
                .verifyRealStateInputPresent()
                .typeRealState(countryValue)
                .verifyRealStateValue(countryValue);
    }

    @Test(description = "Dropdown: після введення країни вибрати її зі списку та перевірити, що 'Зберегти' стає активною")
    public void testSelectRealStateFromDropdownAndVerifySaveEnabled() {
        final String countryValue = "Україна";

        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo()
                .verifyHeader()
                .searchAndOpenProfileChild(FULL_NAME_INPUT);

        new UpdateChildProfilePage()
                .verifyAddButtonPresent()
                .clickAddButton()
                .verifyOpened()
                .scrollToSaveButton()
                .verifySaveButtonDisabled()
                .verifyRealStateInputPresent()
                .typeRealState(countryValue)
                .openRealStateDropdown()
                .selectRealStateOption(countryValue)
                .verifyRealStateValue(countryValue)
                .scrollToSaveButton()
                .verifySaveButtonEnabled();
    }

    @Test(description = "Click: після активації 'Зберегти' натиснути і перевірити закриття модалі")
    public void testClickSaveButtonAfterSelectingCountry() {
        final String countryValue = "Україна";

        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo()
                .verifyHeader()
                .searchAndOpenProfileChild(FULL_NAME_INPUT);

        new UpdateChildAddressModal().completeAddCountryFlow(countryValue);
    }
}
