package ru.stqa.selenium.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckProductStickers {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void checkProductStickersTest() {
        driver.get("http://localhost/litecart");

        List<WebElement> productsList = driver.findElements(By.cssSelector("li.product"));
        for (WebElement product : productsList) {

            List<WebElement> stickersList = product.findElements(By.cssSelector("div.sticker"));
            Assert.assertEquals(1, stickersList.size());
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}