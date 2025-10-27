package com.aikom;

import com.codeborne.selenide.ex.ElementNotFound;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AikomLoginTest extends BaseTest {

    /**
     * Verifies that the Aikom login page is loaded correctly.
     */
    @Test
    public void testAikomLoginPage() {
        // Open the Aikom login page
        open("https://cabinet.aikom.gov.ua/officer/login");

        // Verify the page is loaded by checking the title and login form
        // Header with the title "Кабінет користувача" should be present
        $("h1").shouldHave(text("Кабінет користувача"));
        // Click the "Увійти до кабінету" button
        $("button[data-xpath='loginButton']").click();

        // Verify that the authentication title is present
        // Title should be "Будь ласка, автентифікуйтесь"
        $("h1.MuiTypography-root").shouldHave(text("Будь ласка, автентифікуйтесь"));

    }
}
