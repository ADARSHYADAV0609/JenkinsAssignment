package com.test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.pages.BasePage;
import com.pages.LoginPage;

import DataProvider.ConfigFileReader;
import org.apache.logging.log4j.*;

public class LoginPageTest extends BasePage {

	private static Logger logger = LogManager.getLogger(LoginPageTest.class);
	LoginPage loginPage;

	public LoginPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws Exception {
		setup();
		loginPage = new LoginPage(null);
	}

	@Test(priority = 1) // Login with correct mobile number and password.
	public void LoginTest1() throws InterruptedException, IOException {

		loginPage = new LoginPage(driver);
		loginPage.clicksignin();
		logger.info("Clicked on signIn");
		loginPage.enterEmail(ConfigFileReader.getProperty("userId"));
		logger.info("Entered Valid Email Address or Phone Number");
		loginPage.clickContinue();
		logger.info("Clicked on continue");
		loginPage.enterPassword(ConfigFileReader.getProperty("password"));
		logger.info("Entered password");
		loginPage.clickOnSubmitBtn();
		logger.info("Clicked on submit");
		logger.info("Verified that the user landed on the Home Page");
		BasePage.takeScreenShot();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(priority = 2) // Login with wrong mobile number.
	public void LoginTest2() throws Exception {

		loginPage = new LoginPage(driver);
		loginPage.clicksignin();
		logger.info("Clicked on signIn");
		loginPage.enterEmail(ConfigFileReader.getProperty("invalidnum"));
		logger.info("Entered Invalid Email or Phone number");
		loginPage.clickContinue();
		logger.info("Click Continue");
		String actualError = loginPage.errormssg();
		String expectedError = "We cannot find an account with that mobile number";
		Assert.assertEquals(actualError, expectedError);
		logger.warn(expectedError);
		BasePage.takeScreenShot();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(priority = 3) // Login with correct mobile number and wrong password.
	public void LoginTest3() throws Exception {

		loginPage = new LoginPage(driver);
		loginPage.clicksignin();
		logger.info("Clicked on signIn");
		loginPage.enterEmail(ConfigFileReader.getProperty("userId"));
		logger.info("Entered valid Email or Mobile number");
		loginPage.clickContinue();
		logger.info("Clicked on continue");
		loginPage.enterPassword(ConfigFileReader.getProperty("pswrd"));
		logger.info("entered invalid Password");
		loginPage.clickOnSubmitBtn();
		String actualpswrdError = loginPage.errormssg2();
		String expectedpswrdError = "Your password is incorrect";
		Assert.assertEquals(actualpswrdError, expectedpswrdError);
		logger.info(expectedpswrdError);
		BasePage.takeScreenShot();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}