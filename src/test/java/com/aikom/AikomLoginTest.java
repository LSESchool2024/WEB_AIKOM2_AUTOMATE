package com.aikom;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AikomLoginTest extends BaseTest {

    @Test
    public void testAikomLoginPage() {
        // Open the Aikom login page
        open("https://cabinet.aikom.gov.ua/officer/login");
        
        // Verify the page is loaded by checking the title and login form
        $("h1").shouldHave(text("Вхід до кабінету"));
        $("input[name='login']").shouldBe(visible);
        $("input[name='password']").shouldBe(visible);
        $("button[type='submit']").shouldBe(visible);
        
        // Here you can add login logic when needed
        // $("input[name='login']").setValue("your_login");
        // $("input[name='password']").setValue("your_password");
        // $("button[type='submit']").click();
    }
}
