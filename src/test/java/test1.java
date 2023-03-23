import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class test1 {
    AppiumDriver driver;
    @Test
    public void invalidLogin(){
        WebElement usernameField = driver.findElement(AppiumBy.accessibilityId("test-Username"));
        WebElement passwordField = driver.findElement(AppiumBy.accessibilityId("test-Password"));
        WebElement loginButton = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));

        usernameField.sendKeys("invalid user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        WebElement errorText = driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView"));

        String actualErrTxt = errorText.getAttribute("text");
        String expectedErrTxt = "Username and password do not match any user in this service.";

        Assert.assertEquals(actualErrTxt, expectedErrTxt);
    }

    @Test
    public void validLogin(){
        WebElement usernameField = driver.findElement(AppiumBy.accessibilityId("test-Username"));
        WebElement passwordField = driver.findElement(AppiumBy.accessibilityId("test-Password"));
        WebElement loginButton = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        WebElement productText = driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView"));

        String actualProductTxt = productText.getAttribute("text");
        String expectedProductTxt = "PRODUCTS";

        Assert.assertEquals(actualProductTxt, expectedProductTxt);
    }


}
