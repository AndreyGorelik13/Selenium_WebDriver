package ru.stqa.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class AddNewProduct {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void addNewProductTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Add New Product')]")).click();

        driver.findElement(By.xpath("//label[contains(text(),'Enabled')]")).click();
        driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys("Rubber duck");
        driver.findElement(By.cssSelector("input[name=code")).sendKeys("RD13");
        driver.findElement(By.cssSelector("input[name=quantity")).sendKeys("13");
        driver.findElement(By.cssSelector("input[name='new_images[]']")).sendKeys(new File("src/main/resources/Duck.jpg").getAbsolutePath());
        driver.findElement(By.cssSelector("input[name=date_valid_from")).sendKeys("03-03-2023");
        driver.findElement(By.cssSelector("input[name=date_valid_to")).sendKeys("03-03-2024");

        driver.findElement(By.xpath("//a[contains(text(),'Information')]")).click();
        new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]"))).selectByValue("1");
        driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys("The rubber duck is a rubber bath toy");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("A rubber duck is a rubber bath toy shaped like a small duck with a flat bottom, usually yellow. It is designed for swimming, therefore it is made of lightweight plastic and filled with air, which ensures the buoyancy of the toy.");

        driver.findElement(By.xpath("//a[contains(text(),'Prices')]")).click();
        driver.findElement(By.cssSelector("input[name=purchase_price]")).sendKeys("13");
        new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]"))).selectByValue("USD");
        driver.findElement(By.cssSelector("input[name='prices[USD]']")).sendKeys("1.000");
        driver.findElement(By.cssSelector("button[name=save]")).click();

        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        Assert.assertTrue("The product is not in the catalog", driver.findElement(By.xpath("//form[@name='catalog_form']/descendant::table[@class='dataTable']/tbody/descendant::tr/td[3]/a[text()='Rubber duck']")).isDisplayed());
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}