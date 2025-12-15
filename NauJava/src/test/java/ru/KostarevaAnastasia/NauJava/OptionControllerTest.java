package ru.KostarevaAnastasia.NauJava;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.KostarevaAnastasia.NauJava.dto.OptionCreateDto;
import ru.KostarevaAnastasia.NauJava.models.Option;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.service.OptionService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
@EnableWebSecurity
public class OptionControllerTest {
    @LocalServerPort
    int port;

    @MockBean
    private OptionService optionService;

    @BeforeEach
    void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @BeforeAll
    static void enableLogging() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void shouldCreateOptionSuccessfully() {
        Question question = new Question();
        question.setId(1L);
        Option savedOption = new Option();
        savedOption.setText("Correct answer");
        savedOption.setCorrect(true);
        savedOption.setQuestion(question);

        OptionCreateDto dto = new OptionCreateDto("Correct answer", true, 1L);

        when(optionService.createOption(eq(dto), eq("creator1")))
                .thenReturn(savedOption);
        String requestBody = """
        {
            "text": "Correct answer",
            "correct": true,
            "questionId": 1
        }
        """;

        given()
                .auth().preemptive().basic("creator1", "password")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/options")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("text", equalTo("Correct answer"))
                .body("correct", equalTo(true))
                .body("question.id", equalTo(1));
    }

    @Test
    void shouldReturn403ForRegularUser() {
        String requestBody = """
            {
                "text": "Answer",
                "correct": false,
                "questionId": 1
            }
            """;

        given()
                .auth().preemptive().basic("user1", "password")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/options")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldReturn400WhenTextIsEmpty() {
        String invalidBody = """
            {
                "text": "",
                "correct": true,
                "questionId": 1
            }
            """;

        given()
                .auth().preemptive().basic("creator1", "password")
                .contentType(ContentType.JSON)
                .body(invalidBody)
                .when()
                .post("/api/options")
                .then()
                .statusCode(400);
    }
}
