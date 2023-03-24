package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductPage;
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
    InputStream datais;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws IOException {
        try{
            String dataFileName = "data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(datais != null){
                datais.close();
            }
        }
    }
    @AfterClass
    public void afterClass(){
    }
    @BeforeMethod
    public void beforeMethod(Method m){
        loginPage = new LoginPage();
        System.out.println("\n" + "******* Start test: " + m.getName() + "*********" + "\n");
    }
    @AfterMethod
    public void afterMethod(){
    }
    @Test
    public void invalidLogin(){
        loginPage.enterUserName(loginUsers.getJSONObject("invalidLogin").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidLogin").getString("password"));
        loginPage.pressLoginBtn();

        String actualErrTxt = loginPage.getErrorTxt();
        String expectedErrTxt = strings.get("err_invalid_username_or_password");
        System.out.println("Actual error text = " + actualErrTxt + "\n" + "Expected error text = " + expectedErrTxt);

        Assert.assertEquals(actualErrTxt, expectedErrTxt);
    }

    @Test
    public void validLogin(){
        loginPage.enterUserName(loginUsers.getJSONObject("validLogin").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("validLogin").getString("password"));
        productPage = loginPage.pressLoginBtn();

        String actualProductTittle = productPage.getTitle();
        String expectedProductTittle = strings.get("product_title");
        System.out.println("Actual error text = " + actualProductTittle + "\n" + "Expected error text = " + expectedProductTittle);

        Assert.assertEquals(actualProductTittle, expectedProductTittle);
    }

}
