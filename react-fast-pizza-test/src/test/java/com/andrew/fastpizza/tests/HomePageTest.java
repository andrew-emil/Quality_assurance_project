package com.andrew.fastpizza.tests;

import com.andrew.fastpizza.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void testPageLoading(){
        String title = driver.getTitle();

        Assert.assertNotNull(title);
        Assert.assertFalse(title.isEmpty());
    }

    @Test
    public void testButtonDisabledWhenNameEmpty(){
        String fieldValue = homePage.getNameValue();
        Assert.assertTrue(fieldValue == null || fieldValue.isEmpty());

        boolean isEnabled = homePage.isStartButtonRendered();

        Assert.assertFalse(isEnabled,
                "Start ordering button should be disabled when name is empty");
    }

    @Test
    public void testWhitespaceOnlyNameShouldNotBeAccepted(){
        //should fail
        homePage.enterName("    ");

        boolean isEnabled = homePage.isStartButtonRendered();

        Assert.assertFalse(isEnabled,
                "Start ordering button should be disabled when name is whitespace only");
    }
}
