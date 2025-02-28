package stepDefinations;

import org.openqa.selenium.WebDriver;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utils.ExcelUtils;
import factory.DriverFactory;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Login {

	WebDriver driver;
	LoginPage loginPage;
	Map<String, String> testData;
	
	@Given("User navigate to login page")
	public void user_navigate_to_login_page() {
		
		driver = DriverFactory.getDriver();
		loginPage = new LoginPage(driver);  // Initialize LoginPage
	}

	@When("User enters valid email address {string} in email field")
	public void user_enters_valid_email_address_in_email_field(String usernameText) {
//		waitElement = new WebDriverWait(driver, Duration.ofSeconds(10));
//		WebElement usernameField = waitElement.until(ExpectedConditions.elementToBeClickable(By.name("username")));
//		usernameField.sendKeys(usernameText);
		loginPage.enterUsername(usernameText);
	}

	@When("User enters valid password {string} in password field")
	public void user_enters_valid_password_in_password_field(String passwordText) {
//		driver.findElement(By.name("password")).sendKeys(passwordText);
		loginPage.enterPassword(passwordText);
	}

	@When("User clicks on Login button")
	public void user_clicks_on_login_button() {
//		driver.findElement(By.xpath("//button[@type='submit']")).click();
		loginPage.clickLoginButton();
	}

	@Then("User should get successfully login")
	public void user_should_get_successfully_login() {
		
//		waitElement = new WebDriverWait(driver, Duration.ofSeconds(10));
//        // ✅ Assertion for URL
//        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
//        waitElement.until(ExpectedConditions.urlToBe(expectedUrl));
//        assertEquals("URL did not match, login failed!", expectedUrl, driver.getCurrentUrl());
//        // ✅ Assertion for Dashboard Page visibility
//        WebElement dashboardHeader = waitElement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
//        assertTrue("Dashboard is not visible, login may have failed!", dashboardHeader.isDisplayed());
//        System.out.println("Login successful, User is on Dashboard");
		
		// Verify URL and Dashboard visibility
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
        assertEquals("URL did not match, login failed!", expectedUrl, loginPage.getCurrentUrl());

        assertTrue("Dashboard is not visible, login may have failed!", loginPage.isDashboardVisible());
        System.out.println("Login successful, User is on Dashboard");
	}

	//Scenario Outline, Runs the scenario once per row
	@When("^User enters invalid email address (.+) in email field")
	public void user_enters_invalid_email_address_in_email_field(String usernameText) {
		
		loginPage.enterUsername(usernameText);
	}

	@When("^User enters invalid password (.+) in password field")
	public void user_enters_invalid_password_in_password_field(String passwordText) {
		
		loginPage.enterPassword(passwordText);
	}
	
	@Then("User should get a proper warning message about credentials mismatch")
	public void user_should_get_a_proper_warning_message_about_credentials_mismatch() {

//		waitElement = new WebDriverWait(driver, Duration.ofSeconds(10));
//	    // ✅ Wait for the warning message to appear
//	    WebElement warningMessage = waitElement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'oxd-alert-content-text')]")));
//	    // ✅ Extract actual text from the warning message
//	    String actualMessage = warningMessage.getText();
//	    String expectedMessage = "Invalid credentials";
//	    // ✅ Assertion to check if the warning message is correct
//	    assertEquals("Warning message does not match!", expectedMessage, actualMessage);
//	    System.out.println("Warning message displayed correctly: " + actualMessage);
		
		String expectedMessage = "Invalid credentials";
        String actualMessage = loginPage.getWarningMessage();
        assertEquals("Warning message does not match!", expectedMessage, actualMessage);
        System.out.println("Warning message displayed correctly: " + actualMessage);
	}
	
	//DataTable (Structured Input, Runs once but handles all rows in one execution
	@When("User enters the following credentials:")
    public void user_enters_the_following_credentials(DataTable credentialsTable) {
        List<Map<String, String>> credentials = credentialsTable.asMaps(String.class, String.class);
        loginPage.loginWithDataTable(credentials);
    }
	
	//External Test Data (from a database), getting data from MySQL database
    @When("User enters invalid email and valid password from database")
    public void user_enters_invalid_email_and_valid_password_from_database() {
        testData = loginPage.getInvalidEmailValidPassword(); // Fetch from DB
        loginPage.login(testData.get("email"), testData.get("password"));
    }

    @Then("User should see a proper warning message for incorrect email")
    public void user_should_see_a_proper_warning_message_for_incorrect_email() {
        String actualMessage = loginPage.getWarningMessage();
        String expectedMessage = "Invalid credentials";
        assertEquals("Warning message does not match!", expectedMessage, actualMessage);
        System.out.println("Warning message displayed correctly: " + actualMessage);
    }
    
    //External Test Data (Excel)
    @When("User tries to login without entering any credentials")
    public void user_tries_to_login_without_entering_any_credentials() {
        loginPage.enterUsername(ExcelUtils.getData(1, 0)); // Getting Email from Excel
        loginPage.enterPassword(ExcelUtils.getData(1, 1)); // Getting Password from Excel
        loginPage.clickLoginButton();
    }

    @Then("User should see a warning message for missing credentials")
    public void user_should_see_a_warning_message_for_missing_credentials() {
    	String actualMessage = loginPage.getRequiredWarningMessage();
        String expectedMessage = "Invalid credentials";
        assertEquals("Warning message does not match!", expectedMessage, actualMessage);
        System.out.println("Warning message displayed correctly: " + actualMessage);;
    }
}
