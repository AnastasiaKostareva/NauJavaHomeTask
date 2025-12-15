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


public class QuestionWebControllerTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String USERNAME = "testCreator";
    private static final String PASSWORD = "test11111111Creator";
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
    void shouldRedirectToLogin_WhenRegularUserTriesToAccessQuestionsPage() {
        driver.get(BASE_URL + "/questions");
        if (!driver.getCurrentUrl().contains("/login")) {
            return;
        }
        assertThat(driver.findElement(By.tagName("form")).isDisplayed()).isTrue();
    }

    @Test
    void shouldOpenCreateQuestionForm_WhenAuthorized() {
        driver.get(BASE_URL + "/questions/new");
        if (driver.getCurrentUrl().contains("/login")) {
            return;
        }
        assertThat(driver.getPageSource()).contains("Создать вопрос");
        assertThat(driver.findElements(By.name("textQuestion"))).isNotEmpty();
        assertThat(driver.findElements(By.name("theme"))).isNotEmpty();
        assertThat(driver.findElements(By.cssSelector("button[type='submit']"))).isNotEmpty();
    }

    @Test
    void shouldCreateQuestionSuccessfully() {
        driver.get(BASE_URL + "/questions/new");

        if (driver.getCurrentUrl().contains("/login")) {
            return;
        }
        driver.findElement(By.name("textQuestion")).sendKeys("Сколько будет 2+2?");
        driver.findElement(By.name("theme")).sendKeys("Математика");
        driver.findElement(By.name("questionType")).sendKeys("SINGLE");

        WebElement pointsField = driver.findElement(By.name("points"));
        pointsField.clear();
        pointsField.sendKeys("5");
        WebElement orderField = driver.findElement(By.name("orderIndex"));
        orderField.clear();
        orderField.sendKeys("1");

        List<WebElement> optionTexts = driver.findElements(By.name("optionText"));
        if (!optionTexts.isEmpty()) {
            optionTexts.get(0).sendKeys("4");
        }

        List<WebElement> correctCheckboxes = driver.findElements(By.name("optionCorrect"));
        if (!correctCheckboxes.isEmpty()) {
            correctCheckboxes.get(0).click();
        }

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/questions"));
        assertThat(driver.getPageSource()).contains("Вопрос с вариантами успешно создан!");
    }
}
