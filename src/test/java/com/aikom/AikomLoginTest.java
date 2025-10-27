package com.aikom;

import com.aikom.pages.AikomLoginPage;
import org.testng.annotations.Test;

public class AikomLoginTest extends BaseTest {

    /**
     * Verifies that the Aikom login page is loaded correctly.
     */
    @Test
    public void testAikomLoginPage() {
        AikomLoginPage loginPage = new AikomLoginPage()
                .open()
                .verifyPageTitle()
                .clickLoginButton()
                .verifyAuthTitle()
                .verifyPersonalKeyTitle()
                .verifyPersonalKeyFileSection()
                .verifyPasswordSection();
    }

    /**
     * Tests the functionality of uploading a secret key file
     */
    @Test
    public void testSecretKeyFileUpload() {
        AikomLoginPage loginPage = new AikomLoginPage()
                .FirstAuthorizationOnAikom();

    }
}
