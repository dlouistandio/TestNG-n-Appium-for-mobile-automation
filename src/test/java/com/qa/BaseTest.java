package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected static AppiumDriver driver;
    protected static Properties props;
    InputStream inputStream;

    public BaseTest(){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Parameters({"platformName","platformVersion","deviceName","udid"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String deviceName, String udid) throws Exception {
        try{
            props = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME,platformName);
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION,platformVersion);
            caps.setCapability(MobileCapabilityType.DEVICE_NAME,deviceName);
            caps.setCapability(MobileCapabilityType.UDID,udid);
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("androidAutomationName"));
            caps.setCapability("appPackage",props.getProperty("androidAppPackage"));
            caps.setCapability("appActivity",props.getProperty("androidAppActivity"));
            URL appUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
            caps.setCapability("app", appUrl);

            URL url = new URL(props.getProperty("appiumURL"));

            driver = new AndroidDriver(url, caps);
            String sessionId = driver.getSessionId().toString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void waitForVisibilty(WebElement e){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void click(WebElement e){
        waitForVisibilty(e);
        e.click();
    }

    public void sendKeys(WebElement e, String text){
        waitForVisibilty(e);
        e.sendKeys(text);
    }

    public String getAttribute(WebElement e, String attribute){
        waitForVisibilty(e);
        return e.getAttribute(attribute);
    }

    @AfterTest
    public void afterClass(){
        driver.quit();
    }
}
