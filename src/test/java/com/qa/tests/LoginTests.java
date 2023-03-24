package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductPage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    @BeforeClass
    public void beforeClass(){
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
        loginPage.enterUserName("invalid user");
        loginPage.enterPassword("secret_sauce");
        loginPage.pressLoginBtn();

        String actualErrTxt = loginPage.getErrorTxt();
        String expectedErrTxt = "Username and password do not match any user in this service.";
        System.out.println("Actual error text = " + actualErrTxt + "\n" + "Expected error text = " + expectedErrTxt);

        Assert.assertEquals(actualErrTxt, expectedErrTxt);
    }

    @Test
    public void validLogin(){
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        productPage = loginPage.pressLoginBtn();

        String actualProductTittle = productPage.getTitle();
        String expectedProductTittle = "PRODUCTS";
        System.out.println("Actual error text = " + actualProductTittle + "\n" + "Expected error text = " + expectedProductTittle);

        Assert.assertEquals(actualProductTittle, expectedProductTittle);
    }

}
