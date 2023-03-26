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

    public LoginPage enterUsername(String username) {
        clear(usernameField);
        System.out.println("Login user is " + username);
        sendKeys(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        clear(passwordField);
        System.out.println("Password is " + password);
        sendKeys(passwordField, password);
        return this;
    }

    public String getErrorTxt(){
        String err = getText(errorText);
        System.out.println("Error text is " + err);
        return err;
    }

    public ProductPage pressLoginBtn() {
        System.out.println("Press Login Button");
        click(loginButton);
        return new ProductPage();
    }

    public ProductPage login(String username, String password){
        enterUsername(username);
        enterPassword(password);
        pressLoginBtn();
        return new ProductPage();
    }
}
