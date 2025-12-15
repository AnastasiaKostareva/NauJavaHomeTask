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

public class TestControllerTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String USERNAME = "creator";
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
    void shouldDisplayTestList() {
        driver.get(BASE_URL + "/tests");
        WebElement header = driver.findElement(By.tagName("h1"));
        assertThat(header.getText()).isEqualTo("Доступные тесты");
        List<WebElement> testItems = driver.findElements(By.cssSelector("ul > li"));
        assertThat(testItems).isNotEmpty();
        List<WebElement> startLinks = driver.findElements(By.partialLinkText("Начать тест"));
        assertThat(startLinks).isNotEmpty();
    }

    @Test
    void shouldCreateNewTest() {
        driver.get(BASE_URL + "/tests/new");
        if (driver.getCurrentUrl().contains("/login")) {
            return;
        }
        driver.findElement(By.id("title")).sendKeys("Selenium Test");
        driver.findElement(By.cssSelector("form[method='post'] button[type='submit']")).click();

        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/tests"));

        assertThat(driver.getPageSource()).contains("Selenium Test");
    }

    @Test
    void shouldStartTest() {
        driver.get(BASE_URL + "/tests/52");
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        assertThat(header.getText()).isEqualTo("Selenium Test");
        assertThat(driver.getPageSource()).contains("Завершить тест");
    }
}
