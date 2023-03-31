package com.qa.pages;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class ProductDetailPage extends BaseTest {
    TestUtils utils = new TestUtils();
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[1]")
    private WebElement SLBTitle;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
    @iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[2]")
    private WebElement SLBDesc;

    @AndroidFindBy(accessibility = "test-Price")
    private WebElement SLBPrice;

    @AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
    @iOSXCUITFindBy (id = "test-BACK TO PRODUCTS")
    private WebElement backToProductBtn;

    @iOSXCUITFindBy (id = "test-ADD TO CART")
    private WebElement addToCartBtn;

    public String getSLBTitle(){
        String title = getText(SLBTitle,"Product title in details page is ");
        return title;
    }

    public String getSLBDesc(){
        String desc = getText(SLBDesc,"Product desc in details page is ");
        return desc;
    }

    public String getSLBPrice(){
        String price = getText(SLBPrice,"Product price in details page is " );
        return price;
    }

    public String scrollToSLBPriceAndGetTxt(){
        return getText(scrollToElement(), "");
    }

    public void scrollPage(){
        iOSScrollToElement();
    }

    public boolean isAddToCartBtnDisp(){
        return addToCartBtn.isDisplayed();
    }

    public ProductPage pressBackToProductsBtn(){
        click(backToProductBtn,"Press back to product page");
        return new ProductPage();
    }
}

