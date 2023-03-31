package com.qa;

import com.qa.pages.SettingPage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class MenuPage extends BaseTest{

    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-TMenu\"]/XCUIElementTypeOther")
    private WebElement settingBtn;

    public SettingPage pressSettingBtn(){
        click(settingBtn,"Press settings button");
        return new SettingPage();
    }
}
