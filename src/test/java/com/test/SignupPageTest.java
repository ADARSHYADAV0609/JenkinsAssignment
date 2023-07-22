package com.test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.pages.SignUpPage;

import DataProvider.ConfigFileReader;

public class SignupPageTest extends BasePage {

	private static Logger logger = LogManager.getLogger(SignupPageTest.class);
	SignUpPage signup;
	LoginPage loginpage;

	public SignupPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws Exception {
		setup();
		signup = new SignUpPage(null);
	}

	@Test(priority = 1)
	public void signUpTest1() throws Exception {

		signup = new SignUpPage(driver);
		signup.LogInSignUpPage();
		logger.info("Clicked on login/signup button");
		signup.clickOnSignUp();
		logger.info("Clicked on signUp");
		signup.enterName(ConfigFileReader.getProperty("username"));
		logger.info("Entered username");
		signup.enterMobNum(ConfigFileReader.getProperty("mob"));
		logger.info("Entered email or mobile number");
		signup.enterPassword(ConfigFileReader.getProperty("pswrd"));
		logger.info("Entered password");
		signup.clickSubmit();
		logger.info("Clicked on Submit");

		logger.info("Verified that user registered succesfully");
		BasePage.takeScreenShot();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(priority = 2)
	public void signUpTest2() throws Exception {

		signup = new SignUpPage(driver);
		signup.LogInSignUpPage();
		logger.info("Clicked on login/signUp button");
		signup.clickOnSignUp();
		logger.info("Clicked on signUp");
		signup.enterName(ConfigFileReader.getProperty("username"));
		logger.info("entered username");
		signup.enterMobNum(ConfigFileReader.getProperty("mob2"));
		logger.info("entered mobile number");
		signup.enterPassword(ConfigFileReader.getProperty("pswrd"));
		logger.info("Entered password");
		signup.clickSubmit();
		logger.info("Clicked on Submit button");
		logger.warn("Invalid password type");
		String actualsignupError = signup.SignUpError();
		String expectedsignupError = "The mobile number you entered does not seem to be valid";
		Assert.assertEquals(actualsignupError, expectedsignupError);
		BasePage.takeScreenShot();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
