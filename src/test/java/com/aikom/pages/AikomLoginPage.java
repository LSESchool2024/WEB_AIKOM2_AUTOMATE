package com.aikom.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class AikomLoginPage {
    // Page elements
    private final SelenideElement pageTitle = $("h1");
    private final SelenideElement loginButton = $("button[data-xpath='loginButton']");
    private final SelenideElement authTitle = $("h1.MuiTypography-root");
    private final SelenideElement signWidgetIframe = $("iframe#sign-widget");

    // Keycloak login page elements
    private final SelenideElement keycloakUsername = $("#username");
    private final SelenideElement keycloakPassword = $("#password");
    private final SelenideElement keycloakLoginButton = $("#kc-login");

    // Elements inside iframe (for certificate login)
    private final SelenideElement personalKeyLabel = $("label#pkReadFileTitleLabel");
    private final SelenideElement selectButton = $("a[title='Обрати']");
    private final SelenideElement passwordLabel = $("label:contains('Пароль захисту ключа')");
    private final SelenideElement passwordInput = $("input#pkReadFilePasswordTextField");
    private final SelenideElement fileInput = $("input[type='file']");
    private final SelenideElement fileTextField = $("input[readonly]");
    private final SelenideElement caSelect = $("select#pkCASelect");
    private final SelenideElement readButton = $("div#pkReadFileButton");
    private final SelenideElement loginSubmitButton = $("button[data-xpath='signButton']");

    // Login method selectors
    // Try to find the certificate login button with different possible selectors
    private SelenideElement getCertificateLoginButton() {
        // Try different selectors in order of preference
        String[] selectors = {
                "a:contains('За допомогою КЕП')",
                "a:contains('КЕП')",
                "button:contains('За допомогою КЕП')",
                "button:contains('КЕП')",
                "[data-testid='certificate-login']",
                "[id*='certificate']"
        };

        for (String selector : selectors) {
            SelenideElement element = $(selector);
            if (element.exists()) {
                return element;
            }
        }
        // If no element found, return the original one (will throw a proper error)
        return $("a:contains('За допомогою КЕП')");
    }

    // Page URL
    private static final String PAGE_URL = "https://cabinet.aikom.gov.ua/officer/login";
    private static final String KEY_FILE_PATH = "key_1303348978_71256677.jks";

    // Page actions
    public AikomLoginPage open() {
        Selenide.open(PAGE_URL);
        return this;
    }

    public AikomLoginPage verifyPageTitle() {
        pageTitle.shouldHave(text("Кабінет користувача"));
        return this;
    }

    public AikomLoginPage clickLoginButton() {
        loginButton.shouldBe(interactable, Duration.ofSeconds(10)).click();
        return this;
    }

    public AikomLoginPage verifyAuthTitle() {
        // Check for either the certificate login button or the Keycloak login form
        try {
            // First try to find certificate login button
            SelenideElement certButton = getCertificateLoginButton();
            if (certButton.isDisplayed()) {
                return this;
            }
        } catch (Exception e) {
            // Certificate button not found, try Keycloak form
        }

        // If we get here, check for Keycloak form
        keycloakUsername.shouldBe(visible, Duration.ofSeconds(10));
        keycloakPassword.shouldBe(visible, Duration.ofSeconds(10));

        return this;
    }

    public AikomLoginPage clickCertificateLoginIfPresent() {
        // Just wait for the certificate login form to appear
        signWidgetIframe.shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    /**
     * Verifies the presence of the personal key title inside the iframe
     *
     * @return current page object for method chaining
     */
    public AikomLoginPage verifyPersonalKeyTitle() {
        return withInIframe(() -> {
            $("h1#titleLabel")
                    .shouldBe(visible)
                    .shouldHave(text("Зчитування особистого ключа"));
            return this;
        });
    }


    /**
     * Verifies the personal key file upload section and CA select
     *
     * @return current page object for method chaining
     */
    public AikomLoginPage verifyPersonalKeyFileSection() {
        return withInIframe(() -> {
            personalKeyLabel.shouldBe(visible);
            selectButton.shouldBe(visible);

            // Wait for CA select to be visible and have the default value selected
            caSelect.shouldBe(visible)
                    .shouldHave(value("0"))
                    .shouldHave(text("Визначити автоматично"));

            return this;
        });
    }

    /**
     * Verifies the password input section
     *
     * @return current page object for method chaining
     */
    public AikomLoginPage verifyPasswordSection() {
        return withInIframe(() -> {
            // Verify password label
            passwordLabel
                    .shouldBe(visible)
                    .shouldHave(text("Пароль захисту ключа"));

            // Verify password input is present
            passwordInput
                    .shouldBe(visible)
                    .shouldBe(enabled);

            return this;
        });
    }

    /**
     * Uploads the key file
     *
     * @return current page object for method chaining
     */
    public AikomLoginPage uploadKeyFile() {
        return withInIframe(() -> {
            // Get the absolute path to the key file
            String absolutePath = new java.io.File(KEY_FILE_PATH).getAbsolutePath();

            // Upload the file using the hidden input
            fileInput.uploadFromClasspath("secret_key/" + new java.io.File(KEY_FILE_PATH).getName());

            // Verify the file was selected (the text field should contain the file name)
            fileTextField
                    .shouldBe(visible)
                    .shouldNotBe(empty);

            return this;
        });
    }

    /**
     * Enters the password for the key file
     *
     * @param password the password to enter
     * @return current page object for method chaining
     */
    public AikomLoginPage enterKeyPassword(String password) {
        return withInIframe(() -> {
            passwordInput
                    .shouldBe(visible)
                    .setValue(password);
            return this;
        });
    }

    /**
     * Enters the default key password
     *
     * @return current page object for method chaining
     */
    public AikomLoginPage enterDefaultKeyPassword() {
        return enterKeyPassword("Golkiper2025");
    }

    /**
     * Clicks the 'Read' button to process the key file
     *
     * @return current page object for method chaining
     */
    /**
     * Refreshes the sign widget iframe by switching back to default content and then back to the iframe
     * @return current page object for method chaining
     */
    public AikomLoginPage refreshSignWidgetIframe() {
        // Switch back to default content
        switchTo().defaultContent();
        
        // Wait for the iframe to be present and switch to it
        signWidgetIframe.shouldBe(visible, Duration.ofSeconds(10));
        switchTo().frame(signWidgetIframe);
        
        return this;
    }
    
    /**
     * Clicks the 'Read' button to process the key file
     * @return current page object for method chaining
     */
    public AikomLoginPage clickReadButton() {
        withInIframe(() -> {
            readButton
                    .shouldBe(visible, enabled)
                    .click();
            return null;
        });
        // After clicking Read, switch back to default content where the "Увійти" button appears
        switchTo().defaultContent();
        return this;
    }

    /**
     * Clicks the 'Увійти' (Login) button
     *
     * @return current page object for method chaining
     */
    public AikomLoginPage clickLoginSubmitButton() {
        // If Keycloak login form is shown, use it, otherwise click the main-form "Увійти" button
        if (keycloakLoginButton.exists() && keycloakLoginButton.isDisplayed()) {
            keycloakLoginButton.click();
            return this;
        }
        // Ensure we are in default content (button is outside iframe now)
        switchTo().defaultContent();
        loginSubmitButton
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(enabled)
                .click();
        return this;
    }

    /**
     * Performs Keycloak login with username and password
     *
     * @param username the username to log in with
     * @param password the password to use
     * @return current page object for method chaining
     */
    public AikomLoginPage keycloakLogin(String username, String password) {
        keycloakUsername.setValue(username);
        keycloakPassword.setValue(password);
        keycloakLoginButton.click();
        return this;
    }

    /**
     * Verifies that the user's full name is displayed after successful key processing
     * @return current page object for method chaining
     */
    /**
     * Helper method to execute actions within the iframe context
     */
    private <T> T withInIframe(IframeAction<T> action) {
        try {
            // Switch to the iframe
            switchTo().frame(signWidgetIframe);
            // Execute the action
            return action.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            // Always switch back to default content
            switchTo().defaultContent();
        }
    }

    /**
     * Functional interface for iframe actions
     */
    @FunctionalInterface
    private interface IframeAction<T> {
        T execute();
    }

    /**
     * Verifies that the key file was successfully processed by checking for the login submit button
     * which should be enabled after successful key processing
     * 
     * @return current page object for method chaining
     */
    public AikomLoginPage verifyKeyFileProcessed() {
        // After successful key processing, the "Увійти" button appears/enables on the main form (outside iframe)
        switchTo().defaultContent();
        loginSubmitButton
                .shouldBe(visible, Duration.ofSeconds(20))
                .shouldBe(enabled);
        return this;
    }

    @Step("FirstAuthorizationOnAikom")
    public AikomLoginPage FirstAuthorizationOnAikom() {
        return this
                .open()
                .clickLoginButton()
                .verifyPersonalKeyTitle()
                .verifyPersonalKeyFileSection()
                .uploadKeyFile()
                .enterDefaultKeyPassword()
                .clickReadButton()
                .verifyKeyFileProcessed()
                .clickLoginSubmitButton();
    }
}
