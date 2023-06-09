package ru.stqa.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class OpenPageInNewWindow {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void openPageInNewWindowTest() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        driver.findElement(By.cssSelector("table tr.row td:nth-child(7) a")).click();
        List<WebElement> arrowIconsList = driver.findElements(By.cssSelector("i.fa-external-link"));
        for (WebElement arrowIcon : arrowIconsList) {
            String originalWindow = driver.getWindowHandle();
            arrowIcon.click();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            Set<String> existingWindows = driver.getWindowHandles();
            for (String checkingWindow : existingWindows) {
                if (!checkingWindow.equals(originalWindow)) {
                    driver.switchTo().window(checkingWindow);
                    driver.close();
                }
                driver.switchTo().window(originalWindow);
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}