package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends Page {

    private static final By FIRST_PRODUCT_LOCATOR = By.cssSelector("div#box-checkout-cart ul.shortcuts  li.shortcut:nth-child(1)");
    private static final By PRODUCT_INFO_LOCATOR = By.cssSelector("div#checkout-cart-wrapper");
    private static final By PRODUCT_COUNTS_LOCATOR = By.cssSelector("div#box-checkout-cart ul li.item ");
    private static final By REMOVE_BUTTON_LOCATOR = By.cssSelector("div.viewport button[name='remove_cart_item']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage selectFirstProduct() {
        driver.findElement(FIRST_PRODUCT_LOCATOR).click();
        return this;
    }

    public CartPage deleteAllProducts () {
        while (driver.findElements(PRODUCT_COUNTS_LOCATOR).size() != 0) {
            wait.until(ExpectedConditions.elementToBeClickable(REMOVE_BUTTON_LOCATOR)).click();
            wait.until(ExpectedConditions.attributeContains(PRODUCT_INFO_LOCATOR, "style", "opacity: 1;"));
        }
        wait.until(ExpectedConditions.textToBe(PRODUCT_INFO_LOCATOR,
                "There are no items in your cart.\n" +
                        "<< Back"));
        return this;
    }
}