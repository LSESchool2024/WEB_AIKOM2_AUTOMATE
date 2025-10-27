package com.aikom;

import com.aikom.pages.AikomLoginPage;
import com.aikom.pages.MainPageAikom;
import org.testng.annotations.Test;

/**
 * UI tests for the Aikom main page after successful authorization.
 */
public class MainPageAikomTest extends BaseTest {

    /**
     * Перевірка наявності ключових елементів на головній сторінці після авторизації:
     * - Заголовок "Вітаємо!"
     * - Пункт меню "Головна"
     * - Посилання меню "Доступні послуги" з href "/officer/process-list"
     */
    @Test(description = "Перевірка наявності елементів MainPageAikom після авторизації")
    public void testMainPageElementsPresence() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyWelcomeHeader()
                .verifyMenuHomePresent()
                .verifyAvailableServicesMenuPresent();
    }
}
