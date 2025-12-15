package ru.KostarevaAnastasia.NauJava;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginLogoutTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String USERNAME = "testLogin";
    private static final String PASSWORD = "11111111";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:8080/login");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldLoginAndLogoutSuccessfully() {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameField.sendKeys(USERNAME);
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(PASSWORD);
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/"));
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("welcomeMessage")));
        assertTrue(welcomeMessage.getText().contains("Добро пожаловать"), "No greeting found");

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        assertTrue(logoutButton.isDisplayed(), "The logout button was not found");
        logoutButton.click();

        wait.until(ExpectedConditions.urlContains("/login?logout"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/login?logout"), "Not forwarded to the login");
    }
}