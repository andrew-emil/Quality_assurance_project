package com.andrew.fastpizza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {

    private final WebDriver driver;

    private final By cartItems = By.tagName("li");
    private final By cartItemName = By.id("item-name");
    private final By cartItemQuantity = By.id("quantity");
    private final By clearCartButton = By.xpath("//button[text()='Clear cart']");
    private final By increaseButton = By.xpath("//button[text()='+']");
    private final By cartTotal = By.id("cartPrice");
    private final By emptyCartMessage = By.id("empty-cart");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getNumberOfItems() {
        return driver.findElements(cartItems).size();
    }

    public WebElement getFirstCartItem() {
        List<WebElement> items = driver.findElements(cartItems);
        if (items.isEmpty()) {
            return null;
        }
        return items.getFirst();
    }

    public String getFirstItemName() {
        WebElement item = getFirstCartItem();
        return item.findElement(cartItemName).getText().trim();
    }

    public int getFirstItemQuantity() {
        WebElement item = getFirstCartItem();
        String qtyText = item
                .findElement(cartItemQuantity)
                .getText()
                .trim();
        return Integer.parseInt(qtyText);
    }

    public double getCartTotal() {
        String totalText = driver
                .findElement(cartTotal)
                .getText()
                .substring(1);
        return Double.parseDouble(totalText);
    }

    public void increaseFirstItemQuantity() {
        WebElement item = getFirstCartItem();
        item.findElement(increaseButton).click();
    }

    public void clearCart(){
        WebElement clearBtn = driver.findElement(clearCartButton);
        clearBtn.click();
    }

    public boolean isEmptyCartMessageDisplayed() {
        WebElement msg = driver.findElement(emptyCartMessage);
        return  msg.isDisplayed();
    }
}
