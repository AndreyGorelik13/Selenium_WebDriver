package ru.stqa.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class UserRegistration {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void userRegistrationTest() {
        String email = "StevenBernson" + new Random().nextInt(500) + "@email.com";
        String password = "0987654321";

        driver.get("http://localhost/litecart");

        driver.findElement(By.xpath("//a[text()='New customers click here']")).click();

        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys("Steven");
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys("Bernson");
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys("1556 Broadway, suite 416");
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys("10120");
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys("New York");
        driver.findElement(By.cssSelector("span[role=presentation]")).click();
        driver.findElement(By.cssSelector("span[class$=dropdown] input[role=textbox]")).sendKeys("United States" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+1 502 456 1313");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[name=create_account]")).click();

        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[name=login]")).click();

        driver.findElement(By.xpath("//a[text()='Logout']")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}