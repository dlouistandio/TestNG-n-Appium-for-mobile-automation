package com.qa.pages;

import com.qa.BaseTest;
import com.qa.MenuPage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AndroidFindBySet;
import org.openqa.selenium.WebElement;

public class ProductPage extends MenuPage {
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Toggle\"]/parent::*[1]/preceding-sibling::*[1]")
    private WebElement productText;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
    private WebElement SLBTitle;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
    private WebElement SLBPrice;

    public String getTitle(){
        return getText(productText);
    }

    public String getSLBTitle(){
        return getText(SLBTitle);
    }

    public String getSLBPrice(){
        return getText(SLBPrice);
    }

    public ProductDetailPage pressSLBTitle(){
        click(SLBTitle);
        return new ProductDetailPage();
    }
}
