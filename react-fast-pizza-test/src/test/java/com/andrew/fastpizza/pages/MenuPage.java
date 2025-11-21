package com.andrew.fastpizza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MenuPage {

    private final WebDriver driver;

    private final By pizzaItems = By.id("pizza");
    private final By pizzaName = By.id("pizzaName");
    private final By pizzaIngredients = By.id("pizzaIngredients");
    private final By addToCartButton = By.tagName("button");
    private final By soldOutLabel = By.id("soldOut");
    private final By cartCount = By.id("cartItems");

    public MenuPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForMenuToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pizzaItems, 0));
    }

    private List<WebElement> getAllPizzaItems() {
        return driver.findElements(pizzaItems);
    }

    public boolean hasAtLeastOnePizza(){
        return !driver.findElements(pizzaItems).isEmpty();
    }

    public boolean allPizzasHaveNameAndIngredients(){
        for (WebElement pizza : getAllPizzaItems()){
            String name = pizza.findElement(pizzaName).getText();
            String ingredients = pizza.findElement(pizzaIngredients).getText();

            if(name.isEmpty() || ingredients.isEmpty())
                return false;
        }

        return true;
    }

    private WebElement getFirstAvailablePizza() {
        for (WebElement pizza : getAllPizzaItems()) {

            List<WebElement> sold = pizza.findElements(soldOutLabel);
            if (!sold.isEmpty()) continue;

            List<WebElement> addButton = pizza.findElements(addToCartButton);
            if(addButton.isEmpty())
                continue;

            return pizza;
        }
        return null;
    }

    public void clickAddToCartOnFirstAvailablePizza() {
        WebElement pizza = getFirstAvailablePizza();

        if (pizza != null) {
            pizza.findElement(addToCartButton).click();
        } else {
            throw new IllegalStateException("No available pizza with 'Add to cart' button found.");
        }
    }

    public int getCartCount(){
        String count = driver.findElement(cartCount).getText().split(" ")[0];

        return Integer.parseInt(count);
    }
}
