package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductDetailPage extends BaseTest {
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
    private WebElement SLBTitle;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
    private WebElement SLBDesc;

    @AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
    private WebElement backToProductBtn;

    public String getSLBTitle(){
        String title = getText(SLBTitle);
        System.out.println("Product title in details page is " + title);
        return title;
    }

    public String getSLBDesc(){
        String desc = getText(SLBDesc);
        System.out.println("Product desc in details page is " + desc);
        return desc;
    }

    public ProductPage pressBackToProductsBtn(){
        System.out.println("Press back to product page");
        click(backToProductBtn);
        return new ProductPage();
    }
}

