package com.andrew.fastpizza.tests;

import com.andrew.fastpizza.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {

    @BeforeMethod
    public void goToCart(){
        goToCartPage();
    }

    @Test
    public void testCartShowsAddedPizza(){
        Assert.assertEquals(cartPage.getNumberOfItems(), 1,
                "Cart should contain exactly one item after adding a single pizza from menu.");

        String name = cartPage.getFirstItemName();
        int quantity = cartPage.getFirstItemQuantity();

        Assert.assertNotNull(name);
        Assert.assertFalse(name.isEmpty(),
                "Pizza name in cart should not be empty.");
        Assert.assertEquals(quantity, 1,
                "Initial quantity in cart should be 1.");
    }

    @Test
    public void testIncreaseQuantityUpdatesTotal(){
        double totalBefore = cartPage.getCartTotal();
        cartPage.increaseFirstItemQuantity();
        cartPage.increaseFirstItemQuantity();
        double totalAfter = cartPage.getCartTotal();

        int quantity = cartPage.getFirstItemQuantity();

        Assert.assertEquals(quantity, 3,
                "Quantity should increase from 1 to 3.");

        Assert.assertEquals(totalBefore * 3, totalAfter,
                "Cart total should increase after increasing item quantity.");
    }

    @Test
    public void testClearCart(){
        cartPage.clearCart();
        int quantityAfter = cartPage.getNumberOfItems();

        Assert.assertEquals(quantityAfter, 0,
                "Cart should have 0 items after removing the only item.");

        Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed(),
                "Empty cart message should be displayed when there are no items.");
    }
}
