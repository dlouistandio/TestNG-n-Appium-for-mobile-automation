package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BaseTest {
    @AndroidFindBy(accessibility = "test-Username")
    @iOSXCUITFindBy(id = "test-Username")
    private WebElement usernameField;
    @AndroidFindBy(accessibility = "test-Password")
    @iOSXCUITFindBy(id = "test-Password")
    private WebElement passwordField;
    @AndroidFindBy(accessibility = "test-LOGIN")
    @iOSXCUITFindBy(id = "test-LOGIN")
    private WebElement loginButton;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "cr[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText")
    private WebElement errorText;

    public LoginPage enterUserName(String userName) {
        clear(usernameField);
        sendKeys(usernameField, userName);
        return this;
    }

    public LoginPage enterPassword(String password) {
        clear(passwordField);
        sendKeys(passwordField, password);
        return this;
    }

    public String getErrorTxt(){
        return getText(errorText);
    }

    public ProductPage pressLoginBtn() {
        click(loginButton);
        return new ProductPage();
    }
}
