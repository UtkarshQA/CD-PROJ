package com.MyAccount.Utilities;

import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.MyAccount.Common.Request;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class SeleniumUtil {

	public static Properties properties;
	public static PropertiesConfiguration propertiesConfiguration;
	public static WebDriver driver;
	public static String imagesName = "Image";
	public static Date date = new Date();
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	public static String dt = formatter.format(date);
	public static FileReader fileReader;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static String reportFolderLocation = System.getProperty("user.dir") + "\\reports\\";
	public static String screenshotFolderLocation = System.getProperty("user.dir") + "\\screenshot\\";
	public static String reportDestination = reportFolderLocation + "report_" + dt + ".html";
	public static String g_client;
	public static String g_userName;
	public static String g_screenName;
	public static String g_firstName;
	public static String g_lastName;
	public static String g_email;
	public static String g_securityQuestion;
	public static String g_securityAnswer;
	public static String g_planNumber;
	public static String g_userPassword;
	public static String g_accountNumber;
	public static String g_url;
	public static boolean ProfileResponse;
	public static boolean InsproserviceResponse;
	public static boolean PaymentMethodsResponse;
	public static boolean ProductSummaryResponse;
	public static boolean HomepageResponse;
	public static boolean PaymentResponse;
	public static boolean PayNow_homePageResponse;
	public static boolean ProductsDetailsResponse;
	public static boolean AutoPaymentPreferenceResponse;
	public static boolean DeletePaymentResponse;
	public static String ProfileResponse_message_content;
	public static String InsproserviceResponse_message_content;
	public static String PaymentMethodsResponse_message_content;
	public static String ProductSummaryResponse_message_content;
	public static String HomepageResponse_message_content;
	public static String PaymentResponse_message_content;
	public static String PayNow_homePageResponse_message_content;
	public static String ProductsDetailsResponse_message_content;
	public static String AutoPaymentPreferenceResponse_message_content;
	public static String DeletePaymentResponse_message_content;
	
	public static DataBaseUtil db = new DataBaseUtil();

	@Parameters({ "environment", "client", "planNumber", "userPassword" })
	@BeforeSuite(alwaysRun = true)
	public void setUp(String environment, String client, String planNumber, String userPassword) throws Exception {
		DeleteFile(reportFolderLocation);
		DeleteFile(screenshotFolderLocation);
		propertiesLoad(environment, client);
		db.DataBase_Open();
		userDetails(planNumber, userPassword);
		extentReport();
		Request request = new Request();
		request.GetInsproserviceResponse();
		System.out.println(InsproserviceResponse);
	}

	public void userDetails(String planNumber, String userPassword)
			throws ConfigurationException, ClassNotFoundException, SQLException {

		// DataBaseUtil db = new DataBaseUtil();

		db.executeQuery("SELECT * from AGIA_MARegistration where certificateOrPlanNumber =" + planNumber);

		String accountNumber = db.selectData("accountNumber");

		String query = "SELECT TOP 1 u.userId, registerUser.client, u.screenName, u.firstName, u.lastName, userEmail.data_ AS email, u.reminderQueryQuestion AS securityQuestion, u.reminderQueryAnswer AS securityAnswer, registerUser.certificateOrPlanNumber AS certificateNumber, registerUser.accountNumber from ( select userId from agia_maregistration where accountNumber = '"
				+ accountNumber + "' and certificateOrPlanNumber = '" + planNumber
				+ "') AS registerUserId INNER JOIN  user_ u ON u.userId = registerUserId.userId INNER JOIN (select data_, classPk from expandovalue v INNER JOIN  expandocolumn c ON c.tableId = ( SELECT top 1 tableId from expandocolumn where name = 'emailAddress' ) ) AS userEmail on userEmail.classPk = u.userId INNER JOIN  agia_maregistration registerUser ON u.userId = registerUser.userId";

		db.executeQuery(query);

		g_client = db.selectData("client");
		g_screenName = db.selectData("screenName");
		g_userName = db.selectData("screenName").replace("_" + g_client.toLowerCase(), "").trim();
		g_firstName = db.selectData("firstName");
		g_lastName = db.selectData("lastName");
		g_email = db.selectData("email");
		g_securityQuestion = db.selectData("securityQuestion");
		g_securityAnswer = db.selectData("securityAnswer");
		g_planNumber = planNumber;
		g_userPassword = userPassword;
		g_accountNumber = accountNumber;

	}

	public static void DeleteFile(String folderPathe) {
		File file = new File(folderPathe);
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile() && f.exists()) {
				f.delete();
				// System.out.println("successfully deleted");
			} else {
				// System.out.println("cant delete a file due to open or error");
			}
		}
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws NoSuchElementException, ConfigurationException {
		extent.flush();
	}

	String passScreen = null;
	String failScreen = null;

	public void verify(Object actual, Object expected) {
		try {
			if (actual.equals(expected) == true) {
				passScreen = getScreenshot(driver, imagesName);
				logger.pass("Actual value '" + actual + "' matches with expected value '" + expected + "'.",
						MediaEntityBuilder.createScreenCaptureFromPath(passScreen).build());
			} else {
				failScreen = getScreenshot(driver, imagesName);
				logger.fail("Actual value '" + actual + "' did not match with expected value '" + expected + "'.",
						MediaEntityBuilder.createScreenCaptureFromPath(failScreen).build());

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void notEqual(Object actual, Object expected) throws Exception {

		if (actual.equals(expected) == true) {
			failScreen = getScreenshot(driver, imagesName);
			logger.fail("Actual value '" + actual + "' did not match with expected value '" + expected + "'.",
					MediaEntityBuilder.createScreenCaptureFromPath(failScreen).build());
		} else {
			passScreen = getScreenshot(driver, imagesName);
			logger.pass("Actual value '" + actual + "' matches with expected value '" + expected + "'.",
					MediaEntityBuilder.createScreenCaptureFromPath(passScreen).build());

		}
	}

	public void extentReport() {

		// initialize the HtmlReporter
		htmlReporter = new ExtentHtmlReporter(reportDestination);

		// initialize ExtentReports and attach the HtmlReporter
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// To add system or environment info by using the setSystemInfo method.
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Browser Name", properties.getProperty("BrowserName"));
		extent.setSystemInfo("Environment", properties.getProperty("Environment"));
		extent.setSystemInfo("URL", g_url);
		extent.setSystemInfo("Client Name", g_client);
		extent.setSystemInfo("Account Number", g_accountNumber);
		extent.setSystemInfo("Plan Number", g_planNumber);
		extent.setSystemInfo("User Name", g_userName);
		extent.setSystemInfo("First Name", g_firstName);
		extent.setSystemInfo("Last Name", g_lastName);

		// configuration items to change the look and feel
		// add content, manage tests etc
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("MyAccount Automation Report");
		htmlReporter.config().setReportName("MyAccount Automation Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

	}

	// driver and screenshotName
	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		// below line is just to append the date format with the screenshot name to
		// avoid duplicate names
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/screenshot/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		// Returns the captured file path
		return destination;
	}

	public void propertiesLoad(String propertyFile, String client) throws Exception {
		if (propertyFile.equalsIgnoreCase("QA")) {
			fileReader = new FileReader("config/QA.properties");
			propertiesConfiguration = new PropertiesConfiguration("config/QA.properties");

			if (client.equalsIgnoreCase("nra")) {
				g_url = "https://myaccount-nraapprovedservices.agiatest.dev";
			} else if (client.equalsIgnoreCase("up")) {
				g_url = "https://myaccount-unionplusinsurance.agiatest.dev";
			} else if (client.equalsIgnoreCase("lit")) {
				g_url = "https://myaccount-thelit.agiatest.dev";
			} else if (client.equalsIgnoreCase("aft")) {
				g_url = "https://myaccount-aftbenefits.agiatest.dev";
			} else if (client.equalsIgnoreCase("nasw")) {
				g_url = "https://myaccount-naswmemberinsuranceprograms.agiatest.dev";
			} else if (client.equalsIgnoreCase("moose")) {
				g_url = "https://myaccount-moosevip.agiatest.dev";
			} else if (client.equalsIgnoreCase("csea")) {
				g_url = "https://myaccount-cseabenefitsprogram.agiatest.dev";
			} else if (client.equalsIgnoreCase("gsc")) {
				g_url = "https://myaccount-gscinsurance.agiatest.dev";
			} else if (client.equalsIgnoreCase("ausa")) {
				g_url = "https://myaccount-ausainsurance.agiatest.dev";
			} else if (client.equalsIgnoreCase("aoa")) {
				g_url = "https://myaccount-aoainsurance.agiatest.dev";
			} else if (client.equalsIgnoreCase("amta")) {
				g_url = "https://myaccount-amtabenefits.agiatest.dev";
			} else if (client.equalsIgnoreCase("vfw")) {
				g_url = "https://myaccount-vfwmemberplans.agiatest.dev";
			} else if (client.equalsIgnoreCase("coa")) {
				g_url = "https://myaccount-coainsurance.agiatest.dev";
			} else if (client.equalsIgnoreCase("kiwanis")) {
				g_url = "https://myaccount-kiwanisinsuranceandtravelprotection.agiatest.dev";
			} else if (client.equalsIgnoreCase("aca")) {
				g_url = "https://myaccount-acatodayinsurance.agiatest.dev";
			} else if (client.equalsIgnoreCase("test")) {
				g_url = "https://agiatest.dev";
			} else {
				System.out.println("Client not found..");
			}

		} else if (propertyFile.equalsIgnoreCase("DEV"))

		{
			fileReader = new FileReader("config/DEV.properties");
			propertiesConfiguration = new PropertiesConfiguration("config/DEV.properties");

			if (client.equalsIgnoreCase("nra")) {
				g_url = "https://myaccount-nraapprovedservices.agiadev.dev";
			} else if (client.equalsIgnoreCase("up")) {
				g_url = "https://myaccount-unionplusinsurance.agiadev.dev";
			} else if (client.equalsIgnoreCase("lit")) {
				g_url = "https://myaccount-thelit.agiadev.dev";
			} else if (client.equalsIgnoreCase("aft")) {
				g_url = "https://myaccount-aftbenefits.agiadev.dev";
			} else if (client.equalsIgnoreCase("nasw")) {
				g_url = "https://myaccount-naswmemberinsuranceprograms.agiadev.dev";
			} else if (client.equalsIgnoreCase("moose")) {
				g_url = "https://myaccount-moosevip.agiadev.dev";
			} else if (client.equalsIgnoreCase("csea")) {
				g_url = "https://myaccount-cseabenefitsprogram.agiadev.dev";
			} else if (client.equalsIgnoreCase("gsc")) {
				g_url = "https://myaccount-gscinsurance.agiadev.dev";
			} else if (client.equalsIgnoreCase("ausa")) {
				g_url = "https://myaccount-ausainsurance.agiadev.dev";
			} else if (client.equalsIgnoreCase("aoa")) {
				g_url = "https://myaccount-aoainsurance.agiadev.dev";
			} else if (client.equalsIgnoreCase("amta")) {
				g_url = "https://myaccount-amtabenefits.agiadev.dev";
			} else if (client.equalsIgnoreCase("vfw")) {
				g_url = "https://myaccount-vfwmemberplans.agiadev.dev";
			} else if (client.equalsIgnoreCase("coa")) {
				g_url = "https://myaccount-coainsurance.agiadev.dev";
			} else if (client.equalsIgnoreCase("kiwanis")) {
				g_url = "https://myaccount-kiwanisinsuranceandtravelprotection.agiadev.dev";
			} else if (client.equalsIgnoreCase("aca")) {
				g_url = "https://myaccount-acatodayinsurance.agiadev.dev";
			} else if (client.equalsIgnoreCase("dev")) {
				g_url = "https://agiadev.dev";
			} else {
				System.out.println("Client not found..");
			}

		} else if (propertyFile.equalsIgnoreCase("UAT")) {

			fileReader = new FileReader("config/UAT.properties");
			propertiesConfiguration = new PropertiesConfiguration("config/UAT.properties");

			if (client.equalsIgnoreCase("nra")) {
				g_url = "https://myaccount-nraapprovedservices.agiauat.dev";
			} else if (client.equalsIgnoreCase("up")) {
				g_url = "https://myaccount-unionplusinsurance.agiauat.dev";
			} else if (client.equalsIgnoreCase("lit")) {
				g_url = "https://myaccount-thelit.agiauat.dev";
			} else if (client.equalsIgnoreCase("aft")) {
				g_url = "https://myaccount-aftbenefits.agiauat.dev";
			} else if (client.equalsIgnoreCase("nasw")) {
				g_url = "https://myaccount-naswmemberinsuranceprograms.agiauat.dev";
			} else if (client.equalsIgnoreCase("moose")) {
				g_url = "https://myaccount-moosevip.agiauat.dev";
			} else if (client.equalsIgnoreCase("csea")) {
				g_url = "https://myaccount-cseabenefitsprogram.agiauat.dev";
			} else if (client.equalsIgnoreCase("gsc")) {
				g_url = "https://myaccount-gscinsurance.agiauat.dev";
			} else if (client.equalsIgnoreCase("ausa")) {
				g_url = "https://myaccount-ausainsurance.agiauat.dev";
			} else if (client.equalsIgnoreCase("aoa")) {
				g_url = "https://myaccount-aoainsurance.agiauat.dev";
			} else if (client.equalsIgnoreCase("amta")) {
				g_url = "https://myaccount-amtabenefits.agiauat.dev";
			} else if (client.equalsIgnoreCase("vfw")) {
				g_url = "https://myaccount-vfwmemberplans.agiauat.dev";
			} else if (client.equalsIgnoreCase("coa")) {
				g_url = "https://myaccount-coainsurance.agiauat.dev";
			} else if (client.equalsIgnoreCase("kiwanis")) {
				g_url = "https://myaccount-kiwanisinsuranceandtravelprotection.agiauat.dev";
			} else if (client.equalsIgnoreCase("aca")) {
				g_url = "https://myaccount-acatodayinsurance.agiauat.dev";
			} else if (client.equalsIgnoreCase("uat")) {
				g_url = "https://agiauat.dev";
			} else {
				System.out.println("Client not found..");
			}

		}
		try {
			properties = new Properties();
			properties.load(fileReader);
		} catch (Exception ex) {
			logger.info("***************************");
			logger.info("Property file you are looking for does not exist.");
			logger.info("***************************");
		}
	}

	public void updatePropertyFile(String propertyName, String propertyValue) throws ConfigurationException {
		propertiesConfiguration.setProperty(propertyName, propertyValue);
		propertiesConfiguration.save();
		System.out.println("Config Property Successfully Updated..");
	}

	public void openBrowser() throws Exception {
		logger = extent.createTest("Open Browser").assignCategory(getClass().getSimpleName());
		logger.info("Open browser: " + properties.getProperty("BrowserName"));
		if (properties.getProperty("BrowserName").equalsIgnoreCase("ff")) {
			System.setProperty("webdriver.edge.driver", "drivers/geckodriver.exe");
			// driver = new EdgeDriver();
		}

		if (properties.getProperty("BrowserName").equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			driver = new ChromeDriver();

		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		extent.removeTest(logger);
	}

	public void setBrowserDimension(int width, int hight) {
		logger.info("Set browser dimension: " + width + "x" + hight);
		Dimension d = new Dimension(width, hight);
		driver.manage().window().setSize(d);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	public void openURL(String url) throws InterruptedException {
		logger.info("Navigate to url: " + url);
		driver.get(url);
		implicitLoadTime();
		Thread.sleep(2000);
		driver.findElement(By.tagName("body")).click();
	}

	public void closeBrowser() {
		logger.info("Close browser/Tab.");
		try {
			driver.close();
		} catch (NoSuchElementException e) {
			// TODO: handle NoSuchElementException
		}
	}

	public void quitBrowser() {
		logger.info("Quit browser.");
		try {
			driver.quit();
		} catch (NoSuchElementException e) {
			// TODO: handle NoSuchElementException
		}
	}

	public void refresh() {
		logger.info("Refresh page.");
		driver.navigate().refresh();
		implicitLoadTime();
	}

	public void implicitLoadTime() {
		logger.info("Implicitly wait.");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void pageLoadTime() {
		logger.info("Page load timeout.");
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	public List findWebElementsList(By locator) {
		return driver.findElements(locator);
	}

	public void waitForElement(int milliSeconds) throws Exception {
		Thread.sleep(milliSeconds);
	}

	public void mouseHover(By locator) {
		logger.info("Mouse hover: " + locator);
		try {
			WebElement mouseHover = driver.findElement(locator);
			Actions action = new Actions(driver);
			action.moveToElement(mouseHover).build().perform();
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}

	}

	public void mouseHoverAndClick(By locator) {
		logger.info("Mouse hover and click: " + locator);

		try {
			WebElement mouseHover = driver.findElement(locator);
			Actions action = new Actions(driver);
			action.moveToElement(mouseHover).click().build().perform();
			highlightElement(mouseHover);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}

	}

	public void waitForElementPresent(By locator) {
		logger.info("Wait for element present: " + locator);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
	}

	public void waitForElementInVisible(By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public void waitForElementVisible(By locator) {
		logger.info("Wait for element visible: " + locator);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
	}

	public void waitForElementClickable(By locator) {
		logger.info("Wait for element clickable: " + locator);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}

	}

	public void waitForElemenToBeClickableAndClick(By locator) {
		logger.info("Wait for element clickable and click: " + locator);
		try {
			waitForElementClickable(locator);
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			webElement.click();
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}

	}

	public void selectByText(By locator, String visibleText) {
		logger.info("Select by text: " + locator);
		logger.info("Visible text: " + visibleText);
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			Select sel = new Select(webElement);
			sel.selectByVisibleText(visibleText);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}

	}

	public void selectByValue(By locator, String value) {
		logger.info("Select by value: " + locator);
		logger.info("Value: " + value);
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			Select sel = new Select(webElement);
			sel.selectByValue(value);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}

	}

	public void selectByIndex(By locator, int index) {
		logger.info("Select by index: " + locator);
		logger.info("Index: " + index);
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			Select sel = new Select(webElement);
			sel.selectByIndex(index);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}

	}

	public String getSystemProperties(String propName) {
		return System.getProperty(propName);
	}

	public void tabKey() {
		logger.info("Press tab key");
		Actions act = new Actions(driver);
		act.sendKeys(Keys.TAB).perform();
	}

	public void switchToIFrame(String frameID) {
		logger.info("Switch to IFrame: " + frameID);
		try {
			driver.switchTo().frame(frameID);
		} catch (NoSuchElementException e) {
			logger.error("IFrame not found: " + frameID);
		}
	}

	public void switchToDefaultFrame() {
		logger.info("Switch to default IFrame");
		driver.switchTo().defaultContent();
	}

	public void scrollDownJavaScript() {
		logger.info("Scroll down by JavaScript");
		JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
		javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void highlightElement(WebElement webElement) {
		JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
		javascriptExecutor.executeScript("arguments[0].style.border='2px solid yellow'", webElement);
	}

	public void scrollUPJavaScript() {
		logger.info("Scroll UP by JavaScript");
		JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
		javascriptExecutor.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	public void scrollByXYPosition(String Xposition, String Yposition) {
		logger.info("scroll by XPosition: " + Xposition + " Yposition: " + Yposition);
		JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
		javascriptExecutor.executeScript("window.scrollTo(" + Xposition + "," + Yposition + ")");
	}

	public void scrollToElement(By locator) {
		logger.info("Scroll to element: " + locator);
		try {
			WebElement webElement = driver.findElement(locator);
			JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
			javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", webElement);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}

	}

	public void resetZoom() {
		JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
		javascriptExecutor.executeScript("document.body.style.zoom = '100%'");
	}

	public void click(By locator) {
		logger.info("Click on element: " + locator);
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			webElement.click();
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
	}

	public boolean isChecked(By locator) {
		logger.info("Element is checked: " + locator);
		WebElement element = null;
		boolean isChecked = false;
		try {
			element = driver.findElement(locator);
			highlightElement(element);
			isChecked = element.isSelected();
		} catch (NoSuchElementException e) {
		}

		if (isChecked == true) {
			highlightElement(element);
			isChecked = true;
		} else {
			isChecked = false;
			logger.info("Element is unchecked: " + locator);
		}

		return isChecked;
	}

	public void clickByJS(By locator) {
		logger.info("Click by JavascriptExecutor: " + locator);
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", webElement);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
	}

	public void clickByJS(WebElement webElement) {
		logger.info("Click by JavascriptExecutor: " + webElement);
		try {
			highlightElement(webElement);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", webElement);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + webElement);
		}
	}

	public void clearText(By locator) {
		logger.info("Clear textbox: " + locator);
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			webElement.clear();
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
	}

	public final void setText(By locator, String value) {
		logger.info("Set text: " + locator);
		logger.info("Text value: " + value);
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			webElement.clear();
			webElement.sendKeys(value);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
	}

	public void acceptAlert() {
		logger.info("Accept alert.");
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoSuchElementException e) {
			logger.error("Alert not found.");
		}

	}

	public void acceptDismiss() {
		logger.info("Dismiss alert.");
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (NoSuchElementException e) {
			logger.error("Alert not found.");
		}
	}

	public void dragAndDrop(By from_locator, By to_locator) {
		Actions actions = null;
		WebElement From = null;
		WebElement To = null;
		logger.info("Drag from locator: " + from_locator);
		try {
			From = driver.findElement(from_locator);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + from_locator);
		}
		logger.info("Drop from locator: " + from_locator);
		try {
			To = driver.findElement(to_locator);
			actions = new Actions(driver);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + to_locator);
		}
		actions.dragAndDrop(From, To).build().perform();

	}

	public String date() {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
		Date date = new Date();
		String dt = formatter.format(date);
		return dt;
	}

	public boolean isElementDisplayed(By locator) {
		logger.info("Element is displayed: " + locator);
		boolean isDisplayed = false;
		try {
			isDisplayed = driver.findElement(locator).isDisplayed();
		} catch (Exception e) {
			logger.info("Element not found: " + locator);
		}
		return isDisplayed;
	}

	// Get methods
	public String getAlertText() {
		String strAlert = null;
		try {
			Alert alert = driver.switchTo().alert();
			strAlert = alert.getText();
			logger.info("Get alert text: " + strAlert);
		} catch (NoSuchElementException e) {
			logger.error("Alert not found.");
		}
		return strAlert;
	}

	public String getAttributeValue(By locator, String Attribute) {
		String strAttributeValue = null;
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			strAttributeValue = webElement.getAttribute(Attribute);
			logger.info("Get attribute value: " + strAttributeValue);

		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
		return strAttributeValue;
	}

	public String getElementCSSValue(By locator, String CssProperty) {
		String strCssValue = null;
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			strCssValue = webElement.getCssValue(CssProperty);
			logger.info("Get CSS value: " + strCssValue);

		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
		return strCssValue;
	}

	public String getCurrentPageURL() {
		String strCurrentUrl = driver.getCurrentUrl();
		logger.info("Get current page URL: " + strCurrentUrl);
		return strCurrentUrl;
	}

	public String getDropdownSelectedValue(By locator) {
		String strDropdownValue = null;
		try {
			Select webElement = new Select(driver.findElement(locator));
			strDropdownValue = webElement.getFirstSelectedOption().getText().trim();
			logger.info("Get dropdown selected value: " + strDropdownValue);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
		return strDropdownValue;
	}

	public String getText(By locator) {
		String strText = null;
		try {
			WebElement webElement = driver.findElement(locator);
			highlightElement(webElement);
			strText = webElement.getText().trim();
			logger.info("Get element text: " + strText);
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + locator);
		}
		return strText;
	}

	public String getPageTitle() {
		String strPageTitle = driver.getTitle();
		logger.info("Get current page title: " + strPageTitle);
		return strPageTitle;
	}

	public String winHandleBefore;

	public void switchToWindow() {

		logger.info("Switch to current window.");

		winHandleBefore = driver.getWindowHandle();

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public void swithToDefaultWindow() {
		logger.info("Switch to default window.");
		driver.switchTo().window(winHandleBefore);
	}

	public String getBrowserDetails() {

		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		String os = cap.getPlatform().toString();
		String v = cap.getVersion().toString();
		String getBrowserDetails = "Browser: " + browserName + " " + v + ", Operating System: " + os;
		return getBrowserDetails;

	}

	public void deleteAllCookies() {
		logger.info("Delete all cookies.");
		driver.manage().deleteAllCookies();
	}

	public int generatePin() throws Exception {
		// 6 digit random number
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());

		int num = generator.nextInt(99999) + 99999;
		if (num < 100000 || num > 999999) {
			num = generator.nextInt(99999) + 99999;
			if (num < 100000 || num > 999999) {
				throw new Exception("Unable to generate PIN at this time..");
			}
		}
		return num;
	}

	public int generateMobile() throws Exception {
		// 10 digit random number
		int m = (int) Math.pow(10, 10 - 1);
		return m + new Random().nextInt(9 * m);
	}

	public Integer getElementCounts(By locator) throws Exception {
		List<WebElement> element = null;
		try {
			element = driver.findElements(locator);
			logger.info("Get element counts: " + locator);
		} catch (Exception e) {
			logger.error("Element not found: " + locator);
		}
		return element.size();

	}

	public String getOnlyDigits(Object object) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher((CharSequence) object);
		String number = matcher.replaceAll("");
		return number;
	}

	public String getOnlyStrings(String s) {
		Pattern pattern = Pattern.compile("[^a-z A-Z]");
		Matcher matcher = pattern.matcher(s);
		String string = matcher.replaceAll("");
		return string;
	}

	public String generateEmail() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr + "@yopmail.com";

	}

	public String generateString(int lan) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < lan) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

}
