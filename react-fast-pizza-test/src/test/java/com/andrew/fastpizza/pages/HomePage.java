package com.andrew.fastpizza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage {

    private final WebDriver driver;

    private final By nameInput = By.xpath("//input[@placeholder='Your full name']");
    private final By startButton = By.xpath("//button[contains(., 'Start')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterName(String name) {
        driver.findElement(nameInput).clear();
        driver.findElement(nameInput).sendKeys(name);
    }

    public boolean isStartButtonRendered() {
        List<WebElement> buttons = driver.findElements(startButton);
        return !buttons.isEmpty();
    }

    public void clickStartOrdering() {
        driver.findElement(startButton).click();
    }

    public String getNameValue() {
        return driver.findElement(nameInput).getAttribute("value");
    }
}
