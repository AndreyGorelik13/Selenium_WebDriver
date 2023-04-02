package ru.stqa.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckingCorrectPageIsOpened {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void correctPageIsOpenedTest() {
        driver.get("http://localhost/litecart");

        WebElement productOnMainPage = driver.findElement(By.xpath("//h3[contains(text(), 'Campaigns')]/../descendant::li"));
        String productNameOnMainPage = productOnMainPage.findElement(By.cssSelector("div.name")).getText();
        WebElement ordinaryPriceOnMainPage = productOnMainPage.findElement(By.cssSelector("div.price-wrapper s.regular-price"));
        String ordinaryPriceValueOnMainPage = ordinaryPriceOnMainPage.getText();
        WebElement discountPriceOnMainPage = productOnMainPage.findElement(By.cssSelector("div.price-wrapper strong.campaign-price"));
        String discountPriceValueOnMainPage = discountPriceOnMainPage.getText();

        // Проверки в, г, д для главной страницы
        checkOrdinaryPriceStyles(ordinaryPriceOnMainPage);
        checkDiscountPriceStyles(discountPriceOnMainPage);
        compareOrdinaryAndDiscountSizes(ordinaryPriceOnMainPage, discountPriceOnMainPage);

        productOnMainPage.click();
        String productName = driver.findElement(By.cssSelector("h1.title")).getText();
        WebElement ordinaryPrice = driver.findElement(By.cssSelector("div#box-product s.regular-price"));
        WebElement discountPrice = driver.findElement(By.cssSelector("div#box-product strong.campaign-price"));

        // Проверка а, б для главной страницы и страницы товара
        Assert.assertEquals(productNameOnMainPage, productName);
        Assert.assertEquals(ordinaryPriceValueOnMainPage, ordinaryPrice.getText());
        Assert.assertEquals(discountPriceValueOnMainPage, discountPrice.getText());

        // Проверка в,г,д - для страницы товара
        checkOrdinaryPriceStyles(ordinaryPrice);
        checkDiscountPriceStyles(discountPrice);
        compareOrdinaryAndDiscountSizes(ordinaryPrice, discountPrice);
    }

    private void checkOrdinaryPriceStyles(WebElement el) {
        Assert.assertEquals("Ordinary price is not strikethroughed.", "line-through", el.getCssValue("text-decoration-line"));
        Color color = Color.fromString(el.getCssValue("color"));
        Assert.assertEquals("Ordinary price is not grey.", color.getColor().getRed(), color.getColor().getGreen());
        Assert.assertEquals("Ordinary price is not grey.", color.getColor().getGreen(), color.getColor().getBlue());
    }

    private void checkDiscountPriceStyles(WebElement el) {
        Assert.assertEquals("Discount price is not bold.", "700", el.getCssValue("font-weight"));
        Color color = Color.fromString(el.getCssValue("color"));
        Assert.assertNotEquals("Discount price is not red.", color.getColor().getRed(), color.getColor().getGreen());
        Assert.assertEquals("Discount price is not red.", color.getColor().getGreen(), color.getColor().getBlue());
    }

    private void compareOrdinaryAndDiscountSizes(WebElement ordinaryPrice, WebElement discountPrice) {
        String ordinarySize = ordinaryPrice.getCssValue("font-size");
        String discountSize = discountPrice.getCssValue("font-size");
        if (Float.parseFloat(ordinarySize.substring(0, ordinarySize.length() - 2)) < Float.parseFloat(discountSize.substring(0, discountSize.length() - 2)))
            System.out.print("Discount price font size is bigger than Ordinary price font size.");
        else Assert.fail("Discount price font size is smaller than Ordinary price font size.");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}