package ru.stqa.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class NoMessagesInBrowserLog {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void test() {
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        List<WebElement> productCount = driver.findElements(By.xpath("//*[@id='content']//td[3]/a[contains(@href, 'product_id')]"));

        for (int i = 0; i < productCount.size(); i++){
            List<WebElement> products = driver.findElements(By.xpath("//*[@id='content']//td[3]/a[contains(@href, 'product_id')]"));
            products.get(i).click();
            for (LogEntry l: driver.manage().logs().get("browser").getAll()
                 ) {
                System.out.print(l + " Номер ссылки = " + i );
            }
            driver.navigate().back();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}