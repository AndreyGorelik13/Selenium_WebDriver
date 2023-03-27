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

        List<String> menuList = driver.findElements(By.cssSelector("div#box-apps-menu-wrapper > ul.list-vertical li")).stream().map(e -> e.getText()).collect(Collectors.toList());
        for (String item : menuList) {
            driver.findElement(By.xpath("//span[text()='" + item + "']")).click();
            assertTrue (driver.findElement(By.cssSelector("h1")).isDisplayed());

            List<String> subMenuList = driver.findElements(By.cssSelector("li#app-.selected ul.docs li")).stream().map(e -> e.getText()).collect(Collectors.toList());
            for (String subItemName : subMenuList) {
                driver.findElement(By.xpath("//span[text()='" + subItemName + "']")).click();
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