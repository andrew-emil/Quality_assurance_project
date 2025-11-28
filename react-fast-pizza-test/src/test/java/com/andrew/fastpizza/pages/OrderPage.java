package com.andrew.fastpizza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;

    //inputs
    private final By nameInput = By.xpath("//input[@name='customer']");
    private final By phoneInput = By.xpath("//input[@name='phone']");
    private final By addressInput = By.xpath("//input[@name='address']");

    //buttons
    private final By getPositionButton = By.xpath("//button[text()='Get position']");
    private final By orderNowButton = By.xpath("//button[contains(., 'Order now')]");
    private final By priorityCheckbox = By.id("priority");

    //error messages
    private final By PhoneInputError = By.id("phone-error");
    private final By addressInputError = By.id("address-error");

    private final By totalPrice = By.id("total-price");
    private final By username = By.id("username");

    public OrderPage(WebDriver driver){
        this.driver = driver;
    }

    public String getDisplayedNameInHeader(){
        return driver.findElement(username).getText();
    }

    public String getCustomerName(){
        return driver.findElement(nameInput).getAttribute("value");
    }

    public boolean isNameFieldReadOnly() {
        WebElement input = driver.findElement(nameInput);
        String readOnly = input.getAttribute("readonly");
        return readOnly != null;
    }

    public void setName(String name) {
        WebElement input = driver.findElement(nameInput);
        input.clear();
        input.sendKeys(name);
    }

    public void setPhone(String phone) {
        WebElement input = driver.findElement(phoneInput);
        input.clear();
        input.sendKeys(phone);
    }

    public Boolean isPhoneInputRequired(){
        return driver.findElement(phoneInput).getAttribute("required") != null;
    }

    public void setAddress(String address) {
        WebElement input = driver.findElement(addressInput);
        input.clear();
        input.sendKeys(address);
    }

    public String getAddress() {
        return driver.findElement(addressInput).getAttribute("value");
    }

    public void clickGetPosition() {
        driver.findElement(getPositionButton).click();
    }

    public void submitOrder() {
        driver.findElement(orderNowButton).click();
    }

    private String getErrorText(By locator) {
        if (driver.findElements(locator).isEmpty()) return "";
        return driver.findElement(locator).getText().trim();
    }

    public String getPhoneError() {
        return getErrorText(PhoneInputError);
    }

    public String getAddressError() {
        return getErrorText(addressInputError);
    }

    public void waitForAddressOrError() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> !getAddress().isEmpty() || !getAddressError().isEmpty());
    }

    public boolean isPriorityChecked() {
        WebElement checkbox = driver.findElement(priorityCheckbox);
        return checkbox.isSelected();
    }

    public void setPriority(boolean value) {
        WebElement checkbox = driver.findElement(priorityCheckbox);
        if (checkbox.isSelected() != value) {
            checkbox.click();
        }
    }

    public double getTotal(){
        String totalText = driver
                .findElement(totalPrice)
                .getText()
                .substring(1);
        return Double.parseDouble(totalText);
    }



}
