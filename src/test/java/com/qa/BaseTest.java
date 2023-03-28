package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTest {
    protected static AppiumDriver driver;
    protected static Properties props;
    protected static HashMap<String, String> strings = new HashMap<String, String>();
    protected static String platform;
    protected static String dateTime;
    InputStream inputStream;
    InputStream stringis;
    TestUtils utils;


    public BaseTest(){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("super before method");
        ((CanRecordScreen) driver).startRecordingScreen();
    }

    @AfterMethod
    public void afterMethod(ITestResult result){
        System.out.println("super after method");
        String media = ((CanRecordScreen) driver).stopRecordingScreen();

       Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();

       if(result.getStatus() == 2){
           String dir = "videos" + File.separator + params.get("platformName") +"_"+params.get("platformVersion")+"_"+params.get("deviceName")
                   + File.separator + dateTime + File.separator + result.getTestClass().getRealClass().getSimpleName();

           File videoDir = new File(dir);

           if(videoDir.exists()){
               videoDir.mkdirs();
           }

           try {
               FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
               stream.write(Base64.decodeBase64(media));
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    @Parameters({"platformName","platformVersion","deviceName","udid"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String deviceName, String udid) throws Exception {
        utils = new TestUtils();
        dateTime = utils.dateTime();
        platform = platformName;
        URL url;
        try{
            props = new Properties();
            String propFileName = "config.properties";
            String xmlFileName = "strings/strings.xml";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            stringis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
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

    public AppiumDriver getDriver(){
        return driver;
    }

    public String getDateTime(){
        return dateTime;
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

    public WebElement scrollToElement(){
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector().description(\"test-Price\"));"));
    }

    public void iOSScrollToElement() {
//	  RemoteWebElement element = (RemoteWebElement)getDriver().findElement(By.name("test-ADD TO CART"));
//	  String elementID = element.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
//	  scrollObject.put("element", elementID);
        scrollObject.put("direction", "down");
//	  scrollObject.put("predicateString", "label == 'ADD TO CART'");
//	  scrollObject.put("name", "test-ADD TO CART");
//	  scrollObject.put("toVisible", "sdfnjksdnfkld");
        driver.executeScript("mobile:scroll", scrollObject);
    }

    @AfterTest
    public void afterClass(){
        driver.quit();
    }
}
