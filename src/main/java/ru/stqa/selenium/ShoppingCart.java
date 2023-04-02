package ru.stqa.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void shoppingCartTest() {

        driver.get("http://localhost/litecart/en/");

        for (int i = 1; i < 4; i++) {
            driver.findElement(By.cssSelector("div#box-most-popular ul li")).click();
            if (driver.findElements(By.cssSelector("select[name='options[Size]']")).size() == 1) {
                new Select(driver.findElement(By.cssSelector("select[name='options[Size]']"))).selectByValue("Medium");
            }
            driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector("div#cart a:nth-child(2) span.quantity"), "" + i));
            driver.navigate().back();
        }

        driver.findElement(By.xpath("//a[text()='Checkout »']")).click();

        driver.findElement(By.cssSelector("div#box-checkout-cart ul.shortcuts  li.shortcut:nth-child(1)")).click();
        List<String> productCountsList = driver.findElements(By.cssSelector(("div#box-checkout-cart ul li.item input[name=quantity]"))).stream().map(p -> p.getAttribute("value")).collect(Collectors.toList());
        int totalProductsCount = 0;
        for (int j = 0; j < productCountsList.size(); j++)
            totalProductsCount = totalProductsCount + Integer.parseInt(productCountsList.get(j));

        while (totalProductsCount > 0) {
            int productCount = Integer.parseInt(driver.findElement(By.cssSelector("div.viewport input[name=quantity]")).getAttribute("value"));
            totalProductsCount = totalProductsCount - productCount;
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.viewport button[name='remove_cart_item']"))).click();
            if (driver.findElements(By.cssSelector("div#checkout-summary-wrapper")).size() == 1) {
                driver.findElement(By.cssSelector("div.viewport button[name='update_cart_item']")).click();
                wait.until(ExpectedConditions.attributeContains(By.cssSelector("div#checkout-cart-wrapper"), "style", "opacity: 1;"));
            }
        }
        wait.until(ExpectedConditions.textToBe(By.cssSelector("div#checkout-cart-wrapper"),
                "There are no items in your cart.\n" +
                        "<< Back"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}