package com.andrew.fastpizza.tests;

import com.andrew.fastpizza.pages.MenuPage;
import com.andrew.fastpizza.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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
    public void testButtonIsRendered(){
        homePage.enterName("Andrew");
        boolean isRendered = homePage.isStartButtonRendered();

        Assert.assertTrue(isRendered,
                "Start ordering button should be rendered when enter a valid name");
    }

    @DataProvider(name = "invalidNames")
    public Object[][] invalidNames() {
        return new Object[][]{
                {""},
                {"    "},
                {"1"},
                {"@@@"}
        };
    }

    @Test(dataProvider = "invalidNames")
    public void testInvalidNamesDoNotRenderStartButton(String name){
        //should fail
        homePage.enterName(name);

        boolean isEnabled = homePage.isStartButtonRendered();

        Assert.assertFalse(isEnabled,
                "Start ordering button should be disabled");
    }

    @Test
    public void testNavigatesToMenuPage(){
        homePage.enterName("Andrew");
        homePage.clickStartOrdering();

        MenuPage menuPage = new MenuPage(driver);

        menuPage.waitForMenuToLoad();

        Assert.assertTrue(menuPage.hasAtLeastOnePizza(),
                "After clicking Start ordering with a valid name, user should be navigated to the menu page and see at least one pizza.");
    }
}
