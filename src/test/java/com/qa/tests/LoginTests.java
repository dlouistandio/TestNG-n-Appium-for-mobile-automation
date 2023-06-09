package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws IOException {
        InputStream datais = null;

        try{
            String dataFileName = "data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if(datais != null){
                datais.close();
            }
        }
        closeApp();
        launchApp();
    }
    @AfterClass
    public void afterClass(){
    }
    @BeforeMethod
    public void beforeMethod(Method m){
        utils.log().info("login test before method");
        loginPage = new LoginPage();
        utils.log().info("\n" + "******* Start test: " + m.getName() + "*********" + "\n");
    }
    @AfterMethod
    public void afterMethod(){
        utils.log().info("login test after method");
    }
    @Test
    public void invalidLogin(){
        loginPage.enterUsername(loginUsers.getJSONObject("invalidLogin").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidLogin").getString("password"));
        loginPage.pressLoginBtn();

        String actualErrTxt = loginPage.getErrorTxt();
        String expectedErrTxt = getStrings().get("err_invalid_username_or_password");
        utils.log().info("Actual error text = " + actualErrTxt + "||" + "Expected error text = " + expectedErrTxt);

        Assert.assertEquals(actualErrTxt, expectedErrTxt);
    }

    @Test
    public void validLogin(){
        loginPage.enterUsername(loginUsers.getJSONObject("validLogin").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("validLogin").getString("password"));
        productPage = loginPage.pressLoginBtn();

        String actualProductTittle = productPage.getTitle();
        String expectedProductTittle = getStrings().get("product_title");
        utils.log().info("Actual error text = " + actualProductTittle + "\n" + "Expected error text = " + expectedProductTittle);

        Assert.assertEquals(actualProductTittle, expectedProductTittle);
    }

}
