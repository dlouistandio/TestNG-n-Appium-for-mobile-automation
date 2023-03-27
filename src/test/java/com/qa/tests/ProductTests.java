package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailPage;
import com.qa.pages.ProductPage;
import com.qa.pages.SettingPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class ProductTests extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    InputStream datais;
    JSONObject loginUsers;
    SettingPage settingPage;
    ProductDetailPage productDetailPage;

    @BeforeClass
    public void beforeClass() throws IOException {
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
        loginPage = new LoginPage();
        System.out.println("\n" + "******* Start test: " + m.getName() + "*********" + "\n");

        productPage = loginPage.login(
                loginUsers.getJSONObject("validLogin").getString("username"), loginUsers.getJSONObject("validLogin").getString("password"));
    }
    @AfterMethod
    public void afterMethod(){
        settingPage = productPage.pressSettingBtn();
        loginPage = settingPage.pressLogoutBtn();

    }

    @Test
    public void validateProductOnProductPage(){
        SoftAssert sa = new SoftAssert();

        String SLBTitle = productPage.getSLBTitle();
        sa.assertEquals(SLBTitle, strings.get("product_page_slb_title"));

        String SLBPrice = productPage.getSLBPrice();
        sa.assertEquals(SLBPrice, strings.get("product_page_slb_price"));

        sa.assertAll();
    }

    @Test
    public void validateProductOnProductDetailsPage(){
        SoftAssert sa = new SoftAssert();

        productDetailPage = productPage.pressSLBTitle();

        String SLBTitle = productDetailPage.getSLBTitle();
        sa.assertEquals(SLBTitle, strings.get("product_detail_page_slb_title"));

        String SLBPrice = productDetailPage.getSLBDesc();
        sa.assertEquals(SLBPrice, strings.get("product_detail_page_slb_desc"));

//        productPage = productDetailPage.pressBackToProductsBtn();

        sa.assertAll();
    }

}
