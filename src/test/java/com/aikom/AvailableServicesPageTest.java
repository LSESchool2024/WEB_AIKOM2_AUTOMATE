package com.aikom;

import com.aikom.pages.AikomLoginPage;
import com.aikom.pages.MainPageAikom;
import com.aikom.pages.AvailableServicesPage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.Condition.*;

/**
 * UI tests for the "Доступні послуги" page.
 */
public class AvailableServicesPageTest extends BaseTest {

    /**
     * Після виконання кроку verifyElementsAndClickAvailableServices() відкривається сторінка "Доступні послуги".
     * Тут перевіряємо наявність ключових елементів, наданих у вимогах:
     * - Заголовок h1 "Доступні послуги"
     * - Картка групи "Інформація про здобувачів освіти" з назвою та стрілкою
     * - Картка групи "Інформація про працівників" з назвою та стрілкою
     */
    @Test(description = "Перевірка елементів сторінки 'Доступні послуги'")
    public void testAvailableServicesPageElements() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        new AvailableServicesPage()
                .verifyAllElements();
    }

    /**
     * Натискання на картку "Інформація про здобувачів освіти".
     * Перевірка полягає у відсутності помилки під час кліку ( smoke-click ).
     */
    @Test(description = "Клік по картці 'Інформація про здобувачів освіти' на сторінці 'Доступні послуги'")
    public void testClickStudentsInfoCard() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        new AvailableServicesPage()
                .verifyHeader()
                .verifyStudentsInfoCard()
                .clickStudentsInfoCard();
    }

    /**
     * Після кліку по групі "Інформація про здобувачів освіти" повинні з'явитися послуги цієї групи.
     * Перевіряємо наявність елемента:
     * "Оновлення освітнього профілю дитини"
     */
    @Test(description = "Поява послуг після кліку по групі 'Інформація про здобувачів освіти'")
    public void testStudentsServicesListContainsUpdateChildProfile() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        new AvailableServicesPage()
                .verifyHeader()
                .verifyStudentsInfoCard()
                .clickStudentsInfoCard()
                .verifyUpdateChildProfileServicePresent();
        System.out.println("End test");
    }

    /**
     * Перевірка окремого методу кліку по елементу
     * 'Оновлення освітнього профілю дитини' — smoke‑клік без перевірки навігації.
     */
    @Test(description = "Клік по послузі 'Оновлення освітнього профілю дитини'")
    public void testClickUpdateChildProfileService() {
        new AikomLoginPage()
                .FirstAuthorizationOnAikom();

        new MainPageAikom()
                .verifyElementsAndClickAvailableServices();

        new AvailableServicesPage()
                .openUpdateChildProfileServiceViaStudentsInfo();
    }

}
