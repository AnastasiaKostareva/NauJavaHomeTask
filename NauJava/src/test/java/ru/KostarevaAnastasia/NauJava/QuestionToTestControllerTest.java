package ru.KostarevaAnastasia.NauJava;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;
import ru.KostarevaAnastasia.NauJava.repositories.custom.CustomQuestionToTestRepository;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
class QuestionToTestControllerTest {

    @LocalServerPort
    int port;

    @MockBean
    private CustomQuestionToTestRepository questionToTestRepository;

    @BeforeEach
    void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldReturn200AndQuestionList_whenValidParametersProvided() {
        Long testId = 1L;
        Integer minOrder = 1;
        Integer maxOrder = 5;
        QuestionToTest questionToTest = new QuestionToTest();

        when(questionToTestRepository.findQuestionToTestByTestIDAndSortingOrderBetween(eq(testId), eq(minOrder), eq(maxOrder)))
                .thenReturn(List.of(questionToTest));

        given()
                .param("testId", testId)
                .param("minOrder", minOrder)
                .param("maxOrder", maxOrder)
                .when()
                .get("/custom/question-to-test/by-test-and-order-range")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1));
    }

    @Test
    void shouldReturn404_whenNoQuestionsFound() {
        Long testId = 999L;
        Integer minOrder = 10;
        Integer maxOrder = 20;
        when(questionToTestRepository.findQuestionToTestByTestIDAndSortingOrderBetween(eq(testId), eq(minOrder), eq(maxOrder)))
                .thenReturn(List.of());

        given()
                .param("testId", testId)
                .param("minOrder", minOrder)
                .param("maxOrder", maxOrder)
                .when()
                .get("/custom/question-to-test/by-test-and-order-range")
                .then()
                .statusCode(404)
                .body("message", equalTo(
                        "No answers found for testId " + testId + " and orders '" + minOrder + " " + maxOrder + "'"));
    }

    @Test
    void shouldReturn400_whenRequiredParameterMissing() {
        given()
                .param("minOrder", 1)
                .param("maxOrder", 5)
                .when()
                .get("/custom/question-to-test/by-test-and-order-range")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturn404_whenMinOrderGreaterThanMaxOrder() {
        when(questionToTestRepository.findQuestionToTestByTestIDAndSortingOrderBetween(eq(1L), eq(10), eq(5)))
                .thenReturn(List.of());

        given()
                .param("testId", 1)
                .param("minOrder", 10)
                .param("maxOrder", 5)
                .when()
                .get("/custom/question-to-test/by-test-and-order-range")
                .then()
                .statusCode(404);
    }
}
