package com.andrew.fastpizza.utils;

import com.andrew.fastpizza.pages.CartPage;
import com.andrew.fastpizza.pages.HomePage;
import com.andrew.fastpizza.pages.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;

public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    protected MenuPage menuPage;
    protected CartPage cartPage;
    protected String url = "http://localhost:5173/";

    @BeforeClass
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void loadApplication() {
        driver.get(url);
        homePage = new HomePage(driver);
        menuPage = null;
        cartPage = null;
    }

    protected void goToMenuPage(String name){
        homePage.enterName(name);
        driver.findElement(By.xpath("//button[contains(., 'Start')]")).click();

        menuPage = new MenuPage(driver);

        menuPage.waitForMenuToLoad();
    }

    protected void goToCartPage(){
        goToMenuPage("Andrew");

        menuPage.clickAddToCartOnFirstAvailablePizza();

        driver.findElement(By.id("open-cart")).click();

        cartPage = new CartPage(driver);
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(1500);
        if (driver != null) {
            driver.quit();
        }
    }
}
