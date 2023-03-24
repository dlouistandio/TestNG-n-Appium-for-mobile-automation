package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BaseTest {
    @AndroidFindBy(accessibility = "test-Username")
    private WebElement usernameField;
    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordField;
    @AndroidFindBy(accessibility = "test-LOGIN")
    private WebElement loginButton;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    private WebElement errorText;

    public LoginPage enterUserName(String userName) {
        sendKeys(usernameField, userName);
        return this;
    }

    public LoginPage enterPassword(String password) {
        sendKeys(passwordField, password);
        return this;
    }

    public String getErrorTxt(){
        return getAttribute(errorText, "text");
    }

    public ProductPage pressLoginBtn() {
        click(loginButton);
        return new ProductPage();
    }
}
