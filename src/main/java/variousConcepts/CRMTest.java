package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CRMTest {
	WebDriver driver;
	String browser;
	String url;

	// Storing Webelement using By Class
	By USERNAME_FIELD = By.xpath("//*[@id=\"username\"]");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FIELD = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD_HEADER_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By CUSTOMER_MENU_FIELD = By.xpath("//span[contains(text(), 'Customers')]");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//a[contains(text(), 'Add Customer')]");
	By ADD_CUSTOMER_HEADER_FIELD = By.xpath("//*[@id=\"rform\"]/div[1]/div[1]/div[1]/label");

	By FULL_NAME_FIELD = By.xpath("//*[@id=\"account\"]");
	By COMPANY_NAME_FIELD = By.xpath("//select[@id=\"cid\"]");
	By EMAIL_FIELD = By.xpath("//*[@id=\"email\"]");
	By COUNTRY_NAME_FIELD = By.xpath("//*[@id=\"country\"]");

	// Login Data
	String userName = "demo@techfios.com";
	String Password = "abc123";

	// Test or Mock Data
	String fullName = "September Batch";
	String company = "Techfios";
	String email = "abc432@techfios.com";
	String country = "Albania";

	@BeforeTest
	public void readConfig() {
		// Scanner //BufferedReader //InputStream //FileReader

		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url = prop.getProperty("url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// @Test
	public void loginTest() {
		driver.findElement(USERNAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(Password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		String dashBoardHeader = driver.findElement(DASHBOARD_HEADER_FIELD).getText();
		Assert.assertEquals(dashBoardHeader, "Dashboard", "Dashboard page not found!!");

	}

	@Test
	public void addContact() {
		driver.findElement(USERNAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(Password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		String dashBoardHeader = driver.findElement(DASHBOARD_HEADER_FIELD).getText();
		Assert.assertEquals(dashBoardHeader, "Dashboard", "Dashboard page not found!!");

		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
		String addCustomerHeader = driver.findElement(ADD_CUSTOMER_HEADER_FIELD).getText();
		System.out.println("================" + addCustomerHeader);
//		Assert.assertEquals(addCustomerHeader, "Full Name*", "Add Contact page not found!!");
		
		Random rnd = new Random(); 
		int generatedNum = rnd.nextInt(9999);
		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + generatedNum);

	}

//	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

} 

 
