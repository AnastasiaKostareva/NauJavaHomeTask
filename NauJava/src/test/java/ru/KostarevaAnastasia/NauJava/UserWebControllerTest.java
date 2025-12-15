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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserWebControllerTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String USERNAME = "admin";
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
        login();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void login() {
        driver.get(BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")))
                .sendKeys(USERNAME);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/"));
    }

    @Test
    void shouldUpdateUserRole_WhenAdminEditsUser() {
        driver.get(BASE_URL + "/users");
        if (driver.getCurrentUrl().contains("/login")) {
            return;
        }

        driver.get(BASE_URL + "/users/edit/2");
        WebElement roleSelect = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("role"))
        );
        roleSelect.sendKeys("CREATOR");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/users"));
        assertThat(driver.getPageSource()).contains("CREATOR");
    }
}
