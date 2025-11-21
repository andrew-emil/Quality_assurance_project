package com.andrew.fastpizza.tests;

import com.andrew.fastpizza.utils.BaseTest;
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
    public void testEachPizzaHaveNameAndIngredients(){
        Assert.assertTrue(menuPage.allPizzasHaveNameAndIngredients(),
                "Each pizza item should display a name and ingredients.");
    }

    @Test
    public void testAddPizzaToCartFromMenuUpdatesCart(){
        menuPage.clickAddToCartOnFirstAvailablePizza();
        int cartCount = menuPage.getCartCount();

        Assert.assertTrue(0 < cartCount,
                "Cart count should increase after adding a pizza from the menu.");
    }
}
