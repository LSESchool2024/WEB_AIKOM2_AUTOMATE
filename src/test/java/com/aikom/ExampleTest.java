package com.aikom;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ExampleTest extends BaseTest {

    @Test
    public void testDemoGoogleSearch() {
        // Using a demo page that's better suited for testing
        open("https://www.selenium.dev/selenium/web/web-form.html");
        
        // Fill in the form
        $("#my-text-id").setValue("Test Input");
        $("button[type='submit']").click();
        
        // Verify the success message
        $("h1").shouldHave(text("Form submitted"));
    }
}
