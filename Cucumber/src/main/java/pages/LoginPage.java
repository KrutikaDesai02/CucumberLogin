package pages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.CommonUtils;
import utils.DatabaseUtils;

public class LoginPage {

	WebDriver driver;
	WebDriverWait waitElement;
	
//	@FindBy(name="username")
//	private WebElement usernameField;
	
    By usernameField = By.name("username");
    By passwordField = By.name("password");
    By loginButton = By.xpath("//button[@type='submit']");
    By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    By warningMessage = By.xpath("//p[contains(@class, 'oxd-alert-content-text')]");
    By requiredWarningMessage = By.xpath("//span[contains(@class, 'oxd-input-field-error-message')]");
    
    //Constructor
	public LoginPage(WebDriver driver) {
		
		this.driver = driver;  // this means LoginPage object's driver will use same WebDriver that was use to interact with web elements
		PageFactory.initElements(driver,this);
		this.waitElement = new WebDriverWait(driver, Duration.ofSeconds(CommonUtils.EXPLICIT_WAIT_BASIC_TIME));
	}
	
	// Method to enter username
    public void enterUsername(String username) {
        WebElement usernameElement = waitElement.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameElement.sendKeys(username);
    }

    // Method to enter password
    public void enterPassword(String password) {
        WebElement passwordElement = waitElement.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordElement.sendKeys(password);
    }

    // Method to click on login button
    public void clickLoginButton() {
        WebElement loginButtonElement = waitElement.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButtonElement.click();
    }

    // Method to verify successful login (check if Dashboard is visible)
    public boolean isDashboardVisible() {
        WebElement dashboard = waitElement.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader));
        return dashboard.isDisplayed();
    }

    // Method to get the warning message text for invalid login
    public String getWarningMessage() {
        WebElement warning = waitElement.until(ExpectedConditions.visibilityOfElementLocated(warningMessage));
        return warning.getText();
    }
    
    // Method to get the Required warning message text for invalid login
    public String getRequiredWarningMessage() {
        WebElement warning = waitElement.until(ExpectedConditions.visibilityOfElementLocated(requiredWarningMessage));
        return warning.getText();
    }

    // Method to get the current URL (for assertion)
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    // Method to login using DataTable input
    public void loginWithDataTable(List<Map<String, String>> credentials) {
        for (Map<String, String> row : credentials) {
            String usernameText = row.get("username");
            String passwordText = row.get("password");

            // Enter username
            WebElement userField = waitElement.until(ExpectedConditions.elementToBeClickable(usernameField));
            userField.clear();
            userField.sendKeys(usernameText);

            // Enter password
            WebElement passField = driver.findElement(passwordField);
            passField.clear();
            passField.sendKeys(passwordText);
        }
    }
    
    // Fetch test data from the database
    public Map<String, String> getInvalidEmailValidPassword() {
        Map<String, String> credentials = new HashMap<String, String>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            stmt = conn.prepareStatement("SELECT email, password FROM users WHERE is_valid = false LIMIT 1");
            rs = stmt.executeQuery();

            if (rs.next()) {
                credentials.put("email", rs.getString("email"));
                credentials.put("password", rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // ✅ Ensure resources are closed properly
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return credentials;
    }
    
    // Perform login with given credentials
    public void login(String email, String password) {
        WebElement userField = waitElement.until(ExpectedConditions.elementToBeClickable(usernameField));
        userField.clear();
        userField.sendKeys(email);

        WebElement passField = driver.findElement(passwordField);
        passField.clear();
        passField.sendKeys(password);

        driver.findElement(loginButton).click();
    }
}
