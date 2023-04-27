package com.qa.pages;

import com.qa.BaseTest;
import com.qa.MenuPage;
import com.qa.utils.TestUtils;
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
    @iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Item title\"])[1]")
    private WebElement SLBTitle;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
    @iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Price\"])[1]")
    private WebElement SLBPrice;

    public String getTitle(){
        String title = getText(productText);
        utils.log().info("Page title is " + title);
        return title;
    }

    public String getSLBTitle(){
        String product = getText(SLBTitle);
        utils.log().info("Product title is " + product);
        return product;
    }

    public String getSLBPrice(){
        String price = getText(SLBPrice);
        utils.log().info("Product price is " + price);
        return price;
    }

    public ProductDetailPage pressSLBTitle(){
        click(SLBTitle);
        utils.log().info("Press the product");
        return new ProductDetailPage();
    }
}
