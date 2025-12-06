package com.andrew.fastpizza.tests;

import com.andrew.fastpizza.pages.CartPage;
import com.andrew.fastpizza.utils.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MenuPageTest extends BaseTest {

    @BeforeMethod
    public void goToMenuPage(){
        goToMenuPage("Andrew");
    }

    @Test
    public void testMenuLoadsAndShowsPizzas(){
        Assert.assertTrue(menuPage.hasAtLeastOnePizza(),
                "Menu should display at least one pizza item.");
    }

    @Test
    public void testHeroPizzaImageLoads(){
        Assert.assertTrue(menuPage.isImageDisplayed(),
                "Hero pizza image element should be visible on the home page.");

        Assert.assertTrue(menuPage.isImageLoaded(),
                "Hero pizza image should be loaded successfully.");
    }

    @Test
    public void testAddPizzaToCartFromMenuUpdatesCart(){
        menuPage.clickAddToCartOnFirstAvailablePizza();
        int cartCount = menuPage.getCartCount();

        Assert.assertEquals(cartCount, 1,
                "Cart count should increase after adding a pizza from the menu.");
    }

    @Test
    public void testAddToCartButton(){
        WebElement pizza = menuPage.getFirstSoldOutPizza();

        boolean isDisplayed = menuPage.isAddToCartDisplayed(pizza);

        Assert.assertFalse(isDisplayed,
                "Add to cart button should not be displayed");
    }

    @Test
    public void testNavigateToCartPage(){
        menuPage.clickAddToCartOnFirstAvailablePizza();

        menuPage.clickOpenCartButton();

        CartPage cartPage1 = new CartPage(driver);

        Assert.assertEquals(cartPage1.getNumberOfItems(), 1,
                "cart page should be displayed and has 1 item");
    }

}
