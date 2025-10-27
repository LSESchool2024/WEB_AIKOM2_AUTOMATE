package com.aikom;

import com.aikom.pages.WebFormPage;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ExampleTest extends BaseTest {

    @Test
    public void testDemoGoogleSearch() {
        // Initialize page object and open the page
        WebFormPage webFormPage = new WebFormPage().open();
        
        // Verify page is opened
        assertTrue(webFormPage.isPageOpened(), "Web form page should be opened");
        
        // Fill in the form
        webFormPage.enterText("Test Input")
                  .submitForm();
        
        // Verify the success message
        assertEquals(webFormPage.getSuccessMessage(), "Form submitted", 
                   "Success message should be displayed after form submission");
    }
}
