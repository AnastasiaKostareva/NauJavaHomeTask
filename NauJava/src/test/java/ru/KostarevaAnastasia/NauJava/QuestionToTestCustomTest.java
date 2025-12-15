package ru.KostarevaAnastasia.NauJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.KostarevaAnastasia.NauJava.models.*;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionToTestRepository;
import ru.KostarevaAnastasia.NauJava.repositories.TestRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.KostarevaAnastasia.NauJava.repositories.UserRepository;
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

    @Autowired
    private QuestionToTestRepository questionToTestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Тестирование Criteria API метода для QuestionToTest - базовый сценарий
     */
    @Test
    void testFindQuestionToTestByTestIDAndSortingOrderBetween_BasicScenario() {
        User author = new User();
        author.setUsername("testuser");
        author.setPassword(passwordEncoder.encode("password"));
        author.setRole(Role.CREATOR);
        author = userRepository.save(author);

        var test = createTest("Basic Test");
        Question question1 = createQuestion("Question 1", "Math", QuestionType.SINGLE, author);
        Question question2 = createQuestion("Question 2", "Physics", QuestionType.MULTIPLE, author);
        Question question3 = createQuestion("Question 3", "History", QuestionType.MULTIPLE, author);
        Question question4 = createQuestion("Question 4", "Biology", QuestionType.SINGLE, author);

        createQuestionToTest(test, question1, 1, 5);
        createQuestionToTest(test, question2, 2, 10);
        createQuestionToTest(test, question3, 4, 8);
        createQuestionToTest(test, question4, 6, 7);

        List<QuestionToTest> result = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(test.getId(), 2, 5);

        assertNotNull(result);
        assertEquals(2, result.size());

        List<Integer> foundOrders = result.stream()
                .map(QuestionToTest::getSortingOrder)
                .sorted()
                .toList();
        assertEquals(List.of(2, 4), foundOrders);

        assertTrue(result.stream().allMatch(qt -> qt.getTest().getId().equals(test.getId())));
    }

    /**
     * Тестирование Criteria API метода для QuestionToTest - граничные значения
     */
    @Test
    void testFindQuestionToTestByTestIDAndSortingOrderBetween_BoundaryValues() {
        User author = new User();
        author.setUsername("testuser");
        author.setPassword(passwordEncoder.encode("password"));
        author.setRole(Role.CREATOR);
        author = userRepository.save(author);
        var test = createTest("Boundary Test");
        Question question1 = createQuestion("Question 1", "Math", QuestionType.SINGLE, author);
        Question question2 = createQuestion("Question 2", "Physics", QuestionType.MULTIPLE, author);

        createQuestionToTest(test, question1, 5, 5);
        createQuestionToTest(test, question2, 10, 8);

        List<QuestionToTest> exactMatch = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(test.getId(), 5, 5);
        assertEquals(1, exactMatch.size());
        assertEquals(5, exactMatch.get(0).getSortingOrder());

        List<QuestionToTest> lowerBound = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(test.getId(), 1, 5);
        assertEquals(1, lowerBound.size());
        assertEquals(5, lowerBound.get(0).getSortingOrder());

        List<QuestionToTest> upperBound = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(test.getId(), 5, 15);
        assertEquals(2, upperBound.size());

        List<Integer> foundOrders = upperBound.stream()
                .map(QuestionToTest::getSortingOrder)
                .sorted()
                .toList();
        assertEquals(List.of(5, 10), foundOrders);

        List<QuestionToTest> noResults = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(test.getId(), 6, 9);
        assertEquals(0, noResults.size());
    }

    /**
     * Тестирование Criteria API метода для QuestionToTest - пустой результат
     */
    @Test
    void testFindQuestionToTestByTestIDAndSortingOrderBetween_EmptyResult() {
        User author = new User();
        author.setUsername("testuser");
        author.setPassword(passwordEncoder.encode("password"));
        author.setRole(Role.CREATOR);
        author = userRepository.save(author);
        var test = createTest("Empty Result Test");
        Question question = createQuestion("Question 1", "Math", QuestionType.SINGLE, author);
        createQuestionToTest(test, question, 10, 5);

        List<QuestionToTest> result = customQuestionToTestRepository
                .findQuestionToTestByTestIDAndSortingOrderBetween(test.getId(), 1, 5);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Результат должен быть пустым для диапазона 1-5");
    }

    private ru.KostarevaAnastasia.NauJava.models.Test createTest(String title) {
        ru.KostarevaAnastasia.NauJava.models.Test test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle(title);
        User creator = new User();
        creator.setUsername("tester");
        creator.setRole(Role.USER);
        entityManager.persist(creator);
        test.setCreator(creator);
        return testRepository.save(test);
    }

    private Question createQuestion(String text, String theme, QuestionType type, User author) {
        Question question = new Question();
        question.setTextQuestion(text);
        question.setTheme(theme);
        question.setQuestionType(type);
        question.setAuthor(author);
        return questionRepository.save(question);
    }

    private void createQuestionToTest(ru.KostarevaAnastasia.NauJava.models.Test test, Question question, Integer sortingOrder, Integer points) {
        QuestionToTest qt = new QuestionToTest();
        qt.setId(new QuestionToTestId(
                question.getId(),
                test.getId()
        ));
        qt.setTest(test);
        qt.setQuestion(question);
        qt.setSortingOrder(sortingOrder);
        qt.setNumberPoints(points);
        questionToTestRepository.save(qt);
    }
}