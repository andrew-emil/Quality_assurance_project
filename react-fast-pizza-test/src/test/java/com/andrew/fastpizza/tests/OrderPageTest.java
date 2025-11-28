package com.andrew.fastpizza.tests;

import com.andrew.fastpizza.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OrderPageTest extends BaseTest {

    private String originalName;

    @BeforeMethod
    public void goToOrder() {
        goToOrderPage();
        originalName = orderPage.getDisplayedNameInHeader().trim();
    }

    @Test
    public void testCustomerNameIsSameInTheHeader(){
        String orderName = orderPage.getCustomerName().trim().toUpperCase();
        Assert.assertEquals(originalName, orderName,
                "Name on order page should match the name from the home page.");
    }

    @Test
    public void testNameCannotBeChanged() {
        Assert.assertTrue(orderPage.isNameFieldReadOnly(),
                "Customer name should not be changeable on the order page.");
    }

    @Test
    public void testPhoneValidation(){
        String validAddress = "Ain Shams";
        Assert.assertTrue(orderPage.isPhoneInputRequired(),
                "Expected phone error when phone is empty.");

        orderPage.setPhone("abc123");
        orderPage.setAddress(validAddress);
        orderPage.submitOrder();

        String errorInvalid = orderPage.getPhoneError();
        Assert.assertFalse(errorInvalid.isEmpty(),
                "Expected phone error when phone format is invalid.");

        orderPage.setPhone("01204936350");
        orderPage.setAddress(validAddress);
        orderPage.submitOrder();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(currentUrl.matches(".*/order/[^/]+"),
                "Expected URL to match /order/{orderID}, but was: " + currentUrl);
    }

    @Test
    public void testGetPositionFillsAddress() {
        orderPage.clickGetPosition();
        orderPage.waitForAddressOrError();

        String addr = orderPage.getAddress();

        Assert.assertTrue(addr != null && !addr.isEmpty(),
                "Address should be filled after clicking Get position.");
    }

    @Test
    public void testPriorityCheckboxIncreasesTotal(){
        double totalBefore = orderPage.getTotal();
        System.out.println(totalBefore);

        orderPage.setPriority(true);
        Assert.assertTrue(orderPage.isPriorityChecked(),
                "Priority checkbox is suppose to be set.");

        double totalAfter = orderPage.getTotal();

        Assert.assertEquals(totalAfter, totalBefore + (totalBefore * 0.2),
                "Total order price should increase when priority option is selected.");
    }

}
