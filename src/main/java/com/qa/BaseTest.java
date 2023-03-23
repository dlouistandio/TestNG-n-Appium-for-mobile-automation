package com.qa;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected AppiumDriver driver;
    protected Properties props;
    InputStream inputStream;

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        try{
            props = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION,"13");
            caps.setCapability(MobileCapabilityType.DEVICE_NAME,"Samsung A52");
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("androidAutomationName"));
            caps.setCapability(MobileCapabilityType.UDID,"RR8R602TX1M");
            caps.setCapability("unlockType","pin");
            caps.setCapability("unlockKey","8225");
            caps.setCapability("appPackage",props.getProperty("androidAppPackage"));
            caps.setCapability("appActivity",props.getProperty("androidAppActivity"));

            URL url = new URL(props.getProperty("appiumURL"));

            driver = new AndroidDriver(url, caps);
            String sessionId = driver.getSessionId().toString();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @AfterTest
    public void afterClass(){
        driver.quit();
    }
}
