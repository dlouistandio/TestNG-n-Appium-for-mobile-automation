package com.qa.pages;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BaseTest {
    TestUtils utils = new TestUtils();

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
        sendKeys(usernameField, username,"Login user is " + username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        clear(passwordField);
        sendKeys(passwordField, password,"Password is " + password);
        return this;
    }

    public String getErrorTxt(){
        String err = getText(errorText, "Error text is - " );
        return err;
    }

    public ProductPage pressLoginBtn() {
        click(loginButton, "Press Login Button");
        return new ProductPage();
    }

    public ProductPage login(String username, String password){
        enterUsername(username);
        enterPassword(password);
        pressLoginBtn();
        return new ProductPage();
    }
}
