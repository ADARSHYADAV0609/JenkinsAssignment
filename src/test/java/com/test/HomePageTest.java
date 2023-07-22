package com.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.pages.BasePage;
import com.pages.HomePage;

import DataProvider.ConfigFileReader;

public class HomePageTest extends BasePage {
	private static Logger logger = LogManager.getLogger(HomePageTest.class);
	HomePage homepage;
	SoftAssert softassert = new SoftAssert();

	public HomePageTest() {
		super();
	}

	@BeforeMethod
	public void setUp()throws Exception{
		setup();
		homepage=new HomePage(null);
	}
	
	

	@Test(priority=1)
	public void VerifyTitleTest1() throws IOException {
		
		homepage = new HomePage(driver);
		homepage.verifyTitle();
		logger.info("Verified that expected and actual title are same");
		BasePage.takeScreenShot();
		
	}
	@Test(priority=2)
	public void VerifyTitleTest2() throws IOException {
		
		homepage = new HomePage(driver);
		homepage.verifyTitle();
		logger.error("Actual and Expected title are not same");
		BasePage.takeScreenShot();
		
		
	}
	
	@Test(priority=3)
	public void SearchItem() throws IOException {
		
		homepage=new HomePage(driver);
		homepage.searchItem(ConfigFileReader.getProperty("Item"));
		logger.info("Item entered on search box");
		homepage.clickSearch();
		logger.info("Clicked search button");
		BasePage.takeScreenShot();


		logger.info("Verified that item searched succesfully");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	@Test(priority=4)
	public void IsLogoDisplay() throws IOException {
		
		homepage=new HomePage(driver);
		boolean logo= driver.findElement(By.xpath("//*[@id=\"nav-logo-sprites\"]")).isDisplayed();
		logger.info("Logo displayed");
		
		BasePage.takeScreenShot();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	@Test(priority=5)
	public void IsMenuAvailable() throws IOException {
		
		WebElement Menu = driver.findElement(By.xpath("//*[@id=\"nav-hamburger-menu\"]"));
		logger.info("Menu found and clicked");
		softassert.assertEquals (true, Menu.isDisplayed());
		softassert.assertAll();
		
		logger.info("Verified that menu displayed on page succesfully");
		BasePage.takeScreenShot();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
}
