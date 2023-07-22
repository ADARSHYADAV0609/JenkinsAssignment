package com.pages;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



import DataProvider.ConfigFileReader;
import Utility.ExtentManager;

public class BasePage {

	public static ExtentReports extent;
	public static ExtentTest test;
	public static WebDriver driver;
	
	public void setup() throws IOException {
        if (ConfigFileReader.getProperty("mode").equalsIgnoreCase("non-headless")) {
            if (ConfigFileReader.getProperty("browser").equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver",
                        "C:/Users/adarshyadav/Downloads/chromedriver_win32/chromedriver.exe");
                driver = new ChromeDriver();
            } else if (ConfigFileReader.getProperty("browser").equalsIgnoreCase("edge")) {
                System.setProperty("webdriver.edge.driver",
                        "C:/Users/adarshyadav/Downloads/chromedriver_win32/chromedriver.exe");
                driver = new EdgeDriver();
            } else if (ConfigFileReader.getProperty("browser").equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver",
                        "C:/Users/adarshyadav/Downloads/chromedriver_win32/chromedriver.exe");
                driver = new FirefoxDriver();
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(ConfigFileReader.getProperty("implicitime")),
                    TimeUnit.SECONDS);
            driver.get(ConfigFileReader.getProperty("url"));
        }
        if (ConfigFileReader.getProperty("mode").equalsIgnoreCase("headless")) {
            if (ConfigFileReader.getProperty("browser").equalsIgnoreCase("chrome")) {



               ChromeOptions chromeOptions = new ChromeOptions();
                System.setProperty("webdriver.chrome.driver",
                        "C:/Users/adarshyadav/Downloads/chromedriver_win32/chromedriver.exe");
                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
            } else if (ConfigFileReader.getProperty("browser").equalsIgnoreCase("edge")) {



               EdgeOptions edgeOptions = new EdgeOptions();
                System.setProperty("webdriver.edge.driver",
                        "C:/Users/adarshyadav/Downloads/chromedriver_win32/chromedriver.exe");
                edgeOptions.addArguments("--headless");
                driver = new EdgeDriver(edgeOptions);
            } else if (ConfigFileReader.getProperty("browser").equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver",
                        "C:/Users/adarshyadav/Downloads/chromedriver_win32/chromedriver.exe");
                driver = new FirefoxDriver();
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(ConfigFileReader.getProperty("implicitime")),
                    TimeUnit.SECONDS);
            driver.get(ConfigFileReader.getProperty("url"));
        }
    }



	public static void takeScreenShot() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);



       DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));



       String scrFilepath = scrFile.getAbsolutePath();
        System.out.println("scrFilepath: " + scrFilepath);



       File currentDirFile = new File("Screenshots");
        String path = currentDirFile.getAbsolutePath();



       FileUtils.copyFile(scrFile, new File(path + "\\screenshot" + dateFormat.format(cal.getTime()) + ".png"));
    }
	
	@BeforeSuite
	public void init() throws Exception {
		extent = ExtentManager.getInstance("reports/ExtentReports.html");
	}

	@BeforeMethod
	public void startTest(Method method) throws Exception {
		test = extent.startTest(method.getName());
	}

	@AfterMethod
	public void testResult(ITestResult result) {
		System.out.println(result.getMethod().getMethodName());
		if (result.getStatus() == ITestResult.FAILURE)
			test.log(LogStatus.FAIL, result.getThrowable());
		else if (result.getStatus() == ITestResult.SKIP)
			test.log(LogStatus.SKIP, result.getThrowable());
		else
			test.log(LogStatus.PASS, "Test Passed");
	}

	@AfterSuite
	public void reportFlush() {
		extent.flush();
	}
}
