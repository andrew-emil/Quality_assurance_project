package com.andrew.fastpizza.tests;

import com.andrew.fastpizza.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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

    @DataProvider(name = "phoneCases")
    public Object[][] phoneCases(){
        return new Object[][]{
                {"  ", false, "empty"},
                {"abc123", false, "letters"},
                {"123", false, "too short"},
                {"01012345678", true, "valid"}
        };
    }
    @Test(dataProvider = "phoneCases")
    public void testPhoneValidation(String phone, boolean isValid, String caseName){
        String validAddress = "Ain Shams";
        Assert.assertTrue(orderPage.isPhoneInputRequired(),
                "Expected phone error when phone is empty.");

        orderPage.setPhone(phone);
        orderPage.setAddress(validAddress);
        orderPage.submitOrder();

        String errorInvalid = orderPage.getPhoneError();

        if(isValid){
            String currentUrl = driver.getCurrentUrl();
            Assert.assertNotNull(currentUrl);
            Assert.assertTrue(currentUrl.matches(".*/order/[^/]+"),
                    "Expected URL to match /order/{orderID}, but was: " + currentUrl);
        }else {
            Assert.assertFalse(errorInvalid.isEmpty(),
                    "For case '" + caseName + "' expected phone error but none was shown.");
        }
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

        orderPage.setPriority(true);
        Assert.assertTrue(orderPage.isPriorityChecked(),
                "Priority checkbox is suppose to be set.");

        double totalAfter = orderPage.getTotal();

        Assert.assertEquals(totalAfter, totalBefore + (totalBefore * 0.2),
                "Total order price should increase when priority option is selected.");
    }

}
