package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
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
import java.security.PublicKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

public class BaseTest {
    protected static AppiumDriver driver;
    protected static Properties props;
    protected static HashMap<String, String> strings = new HashMap<String, String>();
    protected static String platform;
    InputStream inputStream;
    InputStream stringis;
    TestUtils utils;


    public BaseTest(){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Parameters({"platformName","platformVersion","deviceName","udid"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String deviceName, String udid) throws Exception {
        platform = platformName;
        URL url;
        try{
            props = new Properties();
            String propFileName = "config.properties";
            String xmlFileName = "strings/strings.xml";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            stringis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            utils = new TestUtils();
            strings = utils.parseStringXML(stringis);

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME,platformName);
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION,platformVersion);
            caps.setCapability(MobileCapabilityType.DEVICE_NAME,deviceName);

            switch (platformName){
                case "Android":
                    caps.setCapability(MobileCapabilityType.UDID,udid);
                    caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("androidAutomationName"));
                    caps.setCapability("appPackage",props.getProperty("androidAppPackage"));
                    caps.setCapability("appActivity",props.getProperty("androidAppActivity"));
//                    String androidAppUrl = getClass().getResource(props.getProperty("androidAppLocation")).getFile();
//                    System.out.println("app url is " + androidAppUrl);
//                    caps.setCapability("app", androidAppUrl);

                    url = new URL(props.getProperty("appiumURL"));

                    driver = new AndroidDriver(url,caps);
                    break;
                case "iOS":
                    caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("iOSAutomationName"));
                    String iosAppUrl = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
                    System.out.println("app url is " + iosAppUrl);
                    caps.setCapability("bundleId", props.getProperty("iOSBundleId"));
//                    caps.setCapability("app", iosAppUrl);

                    url = new URL(props.getProperty("appiumURL"));

                    driver = new IOSDriver(url, caps);
                    break;
                default:
                    throw new Exception("Invalid Platform! - " + platformName);
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                inputStream.close();
            }
            if (stringis != null){
                stringis.close();
            }
        }
    }

    public void waitForVisibilty(WebElement e){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void clear(WebElement e){
        waitForVisibilty(e);
        e.clear();
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

    public String getText(WebElement e){
        switch (platform){
            case "Android":
               return getAttribute(e, "text");
            case "iOS":
                return getAttribute(e, "label");
        }
        return null;
    }

    public void closeApp() {
        switch(platform){
            case "Android":
                ((InteractsWithApps)driver).terminateApp(props.getProperty("androidAppPackage"));
                break;
            case "iOS":
                ((InteractsWithApps) driver).terminateApp(props.getProperty("iOSBundleId"));
        }
    }

    public void launchApp() {
        switch(platform){
            case "Android":
                ((InteractsWithApps) driver).activateApp(props.getProperty("androidAppPackage"));
                break;
            case "iOS":
                ((InteractsWithApps) driver).activateApp(props.getProperty("iOSBundleId"));
        }
    }

    @AfterTest
    public void afterClass(){
        driver.quit();
    }
}
