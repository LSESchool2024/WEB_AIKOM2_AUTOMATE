package com.aikom;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ExampleTest extends BaseTest {

    @Test
    public void testGoogleSearch() throws InterruptedException {
        open("https://www.google.com");
        $("input[name='q']").setValue("Selenide");
        $("input[name='btnK']").click();
        $("#search").shouldBe(visible);
    }
}
