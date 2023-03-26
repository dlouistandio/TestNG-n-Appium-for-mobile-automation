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
        return getText(SLBTitle);
    }

    public String getSLBDesc(){
        return getText(SLBDesc);
    }

    public ProductPage pressBackToProductsBtn(){
        click(backToProductBtn);
        return new ProductPage();
    }
}

