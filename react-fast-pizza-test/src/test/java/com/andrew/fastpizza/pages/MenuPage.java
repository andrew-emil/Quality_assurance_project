package com.andrew.fastpizza.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MenuPage {

    private final WebDriver driver;

    private final By pizzaItems = By.id("pizza");
    private final By addToCartButton = By.tagName("button");
    private final By soldOutLabel = By.id("soldOut");
    private final By cartCount = By.id("cartItems");
    private final By pizzaImage = By.tagName("img");
    private final By openCartButton = By.id("open-cart");

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

    public WebElement getFirstSoldOutPizza(){
        for (WebElement pizza: getAllPizzaItems()){
            if(!pizza.findElements(soldOutLabel).isEmpty())
                return pizza;

        }
        return null;
    }

    public boolean isAddToCartDisplayed(WebElement pizza) {
        return !pizza.findElements(addToCartButton).isEmpty();
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

    public boolean isImageDisplayed(){
        WebElement image = driver.findElements(pizzaImage).getFirst();

        return image.isDisplayed();
    }

    public boolean isImageLoaded(){
        WebElement image = driver.findElements(pizzaImage).getFirst();

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        @SuppressWarnings("DataFlowIssue") boolean isLoaded =  (Boolean) javascriptExecutor.executeScript(
                "return arguments[0].complete && " +
                        "typeof arguments[0].naturalWidth != 'undefined' && " +
                        "arguments[0].naturalWidth > 0;",
                image
        );

        return isLoaded;
    }

    public void clickOpenCartButton(){
        driver.findElement(openCartButton).click();
    }
}
