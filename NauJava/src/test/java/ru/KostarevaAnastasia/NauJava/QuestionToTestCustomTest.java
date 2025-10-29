package ru.KostarevaAnastasia.NauJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.KostarevaAnastasia.NauJava.models.*;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.TestRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.KostarevaAnastasia.NauJava.repositories.custom.CustomQuestionToTestRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@Transactional
class QuestionToTestCustomTest {

    @Autowired
    private CustomQuestionToTestRepository customQuestionToTestRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Тестирование Criteria API метода для QuestionToTest - базовый сценарий
     */
    @Test
    void testFindQuestionToTestByTestIDAndSortingOrderBetween_BasicScenario() {
        var test = createTest("Basic Test", 1L);
        Long testId = test.getId();

        Question question1 = createQuestion("Question 1", "Math", QuestionType.SINGLE);
        Question question2 = createQuestion("Question 2", "Physics", QuestionType.MULTIPLE);
        Question question3 = createQuestion("Question 3", "History", QuestionType.MULTIPLE);
        Question question4 = createQuestion("Question 4", "Biology", QuestionType.SINGLE);

        createQuestionToTest(testId, question1.getId(), 1, 5);
        createQuestionToTest(testId, question2.getId(), 2, 10);
        createQuestionToTest(testId, question3.getId(), 4, 8);
        createQuestionToTest(testId, question4.getId(), 6, 7);

        List<QuestionToTest> result = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(testId, 2, 5);

        assertNotNull(result);
        assertEquals(2, result.size());

        List<Integer> foundOrders = result.stream()
                .map(QuestionToTest::getSortingOrder)
                .sorted()
                .toList();
        assertEquals(List.of(2, 4), foundOrders);

        assertTrue(result.stream().allMatch(qt -> qt.getTestId().equals(testId)));
    }

    /**
     * Тестирование Criteria API метода для QuestionToTest - граничные значения
     */
    @Test
    void testFindQuestionToTestByTestIDAndSortingOrderBetween_BoundaryValues() {
        var test = createTest("Boundary Test", 1L);
        Long testId = test.getId();

        Question question1 = createQuestion("Question 1", "Math", QuestionType.SINGLE);
        Question question2 = createQuestion("Question 2", "Physics", QuestionType.MULTIPLE);

        createQuestionToTest(testId, question1.getId(), 5, 5);
        createQuestionToTest(testId, question2.getId(), 10, 8);

        List<QuestionToTest> exactMatch = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(testId, 5, 5);

        assertEquals(1, exactMatch.size());
        assertEquals(5, exactMatch.get(0).getSortingOrder());

        List<QuestionToTest> lowerBound = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(testId, 1, 5);

        assertEquals(1, lowerBound.size());
        assertEquals(5, lowerBound.get(0).getSortingOrder());

        List<QuestionToTest> upperBound = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(testId, 5, 15);

        assertEquals(2, upperBound.size());

        List<Integer> foundOrders = upperBound.stream()
                .map(QuestionToTest::getSortingOrder)
                .sorted()
                .toList();
        assertEquals(List.of(5, 10), foundOrders);

        List<QuestionToTest> noResults = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(testId, 6, 9);

        assertEquals(0, noResults.size());
    }

    /**
     * Тестирование Criteria API метода для QuestionToTest - пустой результат
     */
    @Test
    void testFindQuestionToTestByTestIDAndSortingOrderBetween_EmptyResult() {
        var test = createTest("Empty Result Test", 1L);
        Long testId = test.getId();

        Question question = createQuestion("Question 1", "Math", QuestionType.SINGLE);
        createQuestionToTest(testId, question.getId(), 10, 5);

        List<QuestionToTest> result = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(testId, 1, 5);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Результат должен быть пустым для диапазона 1-5");
    }



    private ru.KostarevaAnastasia.NauJava.models.Test createTest(String title, Long creatorId) {
        var test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle(title);
        test.setCreatorID(creatorId);
        return testRepository.save(test);
    }

    private Question createQuestion(String text, String theme, QuestionType type) {
        Question question = new Question();
        question.setTextQuestion(text);
        question.setTheme(theme);
        question.setQuestionType(type);
        return questionRepository.save(question);
    }

    private void createQuestionToTest(Long testId, Long questionId, Integer sortingOrder, Integer points) {
        QuestionToTest qt = new QuestionToTest(testId, questionId, sortingOrder, points);
        entityManager.persist(qt);
        entityManager.flush();
    }
}