package com.qa.pages;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class SettingPage extends BaseTest {
    @AndroidFindBy(accessibility = "test-LOGOUT")
    @iOSXCUITFindBy(id = "test-LOGOUT")
    private WebElement logoutBtn;

    public LoginPage pressLogoutBtn(){
        click(logoutBtn);
        utils.log().info("Press logout Button");
        return new LoginPage();
    }
}
