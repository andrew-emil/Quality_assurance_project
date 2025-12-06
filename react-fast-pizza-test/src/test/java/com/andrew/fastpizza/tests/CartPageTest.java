package com.andrew.fastpizza.tests;

import com.andrew.fastpizza.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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

    @DataProvider(name = "increaseClicks")
    public Object[][] increaseClicks() {
        return new Object[][]{
                {2},
                {5},
                {7}
        };
    }
    @Test(dataProvider = "increaseClicks")
    public void testIncreaseQuantityUpdatesTotal(int clicks){
        int initialQty = cartPage.getFirstItemQuantity();
        Assert.assertEquals(initialQty, 1,
                "Precondition: quantity should start at 1");
        int expectedQuantity = clicks + 1;

        double totalBefore = cartPage.getCartTotal();

        for (int i = 0; i < clicks; i++)
            cartPage.increaseFirstItemQuantity();


        int quantity = cartPage.getFirstItemQuantity();

        double totalAfter = cartPage.getCartTotal();

        Assert.assertEquals(quantity, expectedQuantity,
                "Quantity should be " + expectedQuantity + " after " + clicks + " clicks.");

        Assert.assertEquals(totalAfter, totalBefore * expectedQuantity);
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

    @Test
    public void testDecreaseQuantityUpdatesTotal(){
        cartPage.increaseFirstItemQuantity();
        double totalBefore = cartPage.getCartTotal();

        // now decrease once -> quantity should be 2
        cartPage.decreaseFirstItemQuantity();
        double totalAfter = cartPage.getCartTotal();
        int quantityAfter = cartPage.getFirstItemQuantity();

        Assert.assertEquals(quantityAfter, 1,
                "Quantity should decrease from 2 to 1 after one decrement.");

        double expectedTotalAfter = (totalBefore / 2.0) ;

        Assert.assertEquals(totalAfter, expectedTotalAfter,
                "Cart total should decrease when quantity is decreased.");
    }

    @Test
    public void testCannotOrderWithEmptyCart(){
        cartPage.clearCart();
        Assert.assertEquals(cartPage.getNumberOfItems(), 0,
                "Cart should be empty after clearing.");

        Assert.assertFalse(cartPage.canProceedToOrder(),
                "User should not be able to proceed to order when cart is empty.");
    }

}
