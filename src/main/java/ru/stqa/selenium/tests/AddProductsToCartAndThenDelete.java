package ru.stqa.selenium.tests;

import org.junit.Test;

public class AddProductsToCartAndThenDelete extends TestBase{

    @Test
    public void test() {
        app.fillCartWithProducts(3);
        app.openCartAndDeleteAllProducts();
    }
}
