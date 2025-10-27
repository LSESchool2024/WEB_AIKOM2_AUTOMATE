package com.aikom.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class WebFormPage {
    // Elements
    private final SelenideElement textInput = $("#my-text-id");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement successMessage = $("h1");
    private final SelenideElement signWidgetIframe = $("iframe#sign-widget");
    private static final String IFRAME_SELECTOR = "iframe#sign-widget";
    private static final String PAGE_URL = "https://www.selenium.dev/selenium/web/web-form.html";

    // Page actions
    public WebFormPage open() {
        Selenide.open(PAGE_URL);
        return this;
    }

    public WebFormPage enterText(String text) {
        textInput.setValue(text);
        return this;
    }

    public void submitForm() {
        submitButton.click();
    }

    public String getSuccessMessage() {
        return successMessage.text();
    }

    public boolean isPageOpened() {
        return textInput.isDisplayed();
    }

    /**
     * Checks if the personal key title is visible inside the iframe
     *
     * @return true if the title is visible, false otherwise
     */
    public boolean isPersonalKeyTitleVisible() {
        try {
            // Switch to the iframe
            switchTo().frame(signWidgetIframe);

            // Check if the title element is visible
            boolean isVisible = $("h1#titleLabel").is(visible);

            // Switch back to the default content
            switchTo().defaultContent();

            return isVisible;
        } catch (Exception e) {
            // In case of any error, make sure we switch back to default content
            switchTo().defaultContent();
            return false;
        }
    }

    /**
     * Gets the text of the personal key title inside the iframe
     *
     * @return the text of the title element, or empty string if not found
     */
    public String getPersonalKeyTitle() {
        try {
            // Switch to the iframe
            switchTo().frame(signWidgetIframe);

            // Get the text of the title element
            String titleText = $("h1#titleLabel").getText();

            // Switch back to the default content
            switchTo().defaultContent();

            return titleText;
        } catch (Exception e) {
            // In case of any error, make sure we switch back to default content
            switchTo().defaultContent();
            return "";
        }
    }
}
