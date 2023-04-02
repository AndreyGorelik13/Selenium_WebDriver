package ru.stqa.selenium;

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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CheckSortCountriesAndGeo {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void exerciseEightTest(){
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        List<String> countriesList = driver.findElements(By.cssSelector("table tr.row td:nth-child(5)")).stream().map(e -> e.getText()).collect(Collectors.toList());
        checkSortList(countriesList);

        for (String country : countriesList) {
            WebElement countryLink = driver.findElement(By.xpath("//a[text()=\"" + country + "\"]"));
            String zoneCount = countryLink.findElement(By.xpath("./../../td[6]")).getText();
            if (!zoneCount.equals("0")) {
                countryLink.click();
                List<String> zonesList = driver.findElements(By.xpath("//h2[text()='Zones']/table/tbody/tr/td[3]")).stream().map(e -> e.getText()).collect(Collectors.toList());
                checkSortList(zonesList);
                driver.navigate().back();
            }
        }
    }

    @Test
    public void exerciseNineTest() {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        List<String> countriesList = driver.findElements(By.cssSelector("table tr.row td:nth-child(3)")).stream().map(e -> e.getText()).collect(Collectors.toList());

        for (String country : countriesList) {
            driver.findElement(By.xpath("//a[text()=\"" + country + "\"]")).click();
            List<String> zonesList = driver.findElements(By.xpath("//h2[text()='Zones']/following::table/tbody/tr/td[3]/select/option[@selected='selected']")).stream().map(e -> e.getText()).collect(Collectors.toList());
            checkSortList(zonesList);
            driver.navigate().back();
        }
    }

    private void checkSortList(List<String> actualList) {
        List<String> sortedList = actualList;
        Collections.sort(sortedList);
        Assert.assertEquals(actualList, sortedList);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}