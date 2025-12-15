package ru.KostarevaAnastasia.NauJava;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ScoreControllerTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String USERNAME = "testLogin";
    private static final String PASSWORD = "11111111";
    private static final String BASE_URL = "http://localhost:8080";

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
    void shouldDisplayScoresAfterLogin() {
        driver.get(BASE_URL + "/login");
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameField.sendKeys(USERNAME);
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(PASSWORD);
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        wait.until(ExpectedConditions.urlContains("/"));

        driver.get(BASE_URL + "/scores");
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("Page source:\n" + driver.getPageSource());
        WebElement scoresTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("scoresTable")));
        List<WebElement> rows = driver.findElements(By.cssSelector("#scoresTable tbody tr"));
        assertThat(rows).isNotEmpty();
        WebElement firstScoreCell = rows.get(0).findElement(By.tagName("td"));
        assertThat(firstScoreCell.getText()).isNotEmpty();
    }

    @Test
    void shouldRedirectToLogin_WhenAccessScoresWithoutAuth() {
        driver.get(BASE_URL + "/scores");
        wait.until(ExpectedConditions.urlContains("/login"));
        WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("form")));
        assertThat(loginForm.isDisplayed()).isTrue();
    }
}
