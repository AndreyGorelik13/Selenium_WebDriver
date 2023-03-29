package ru.stqa.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AllSectionsClick {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void allSectionsClickTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> menuList = driver.findElements(By.cssSelector("#app-"));
        for (int i = 0; i < menuList.size(); i++) {
            menuList = driver.findElements(By.cssSelector("#app-"));
            menuList.get(i).click();
            assertTrue (driver.findElement(By.cssSelector("h1")).isDisplayed());

            List<WebElement> subMenuList = driver.findElements(By.cssSelector("#app- ul li a"));
            for (int j = 0; j < subMenuList.size(); j++) {
                subMenuList = driver.findElements(By.cssSelector("#app- ul li a"));
                subMenuList.get(j).click();
                assertTrue (driver.findElement(By.cssSelector("h1")).isDisplayed());
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}