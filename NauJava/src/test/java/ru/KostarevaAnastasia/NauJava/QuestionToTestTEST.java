package ru.KostarevaAnastasia.NauJava;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.KostarevaAnastasia.NauJava.models.*;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionToTestRepository;
import ru.KostarevaAnastasia.NauJava.repositories.TestRepository;
import ru.KostarevaAnastasia.NauJava.repositories.UserRepository;
import ru.KostarevaAnastasia.NauJava.repositories.custom.CustomQuestionToTestRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@Transactional
class QuestionToTestTEST {

    @Autowired
    private QuestionToTestRepository questionToTestRepository;

    @Autowired
    private CustomQuestionToTestRepository customQuestionToTestRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Тестирование поиска вопросов теста по диапазону порядка сортировки
     */
    @Test
    void testFindByTestIdAndSortingOrderBetween() {
        ru.KostarevaAnastasia.NauJava.models.Test test = createTest("Test for sorting order");
        Long testId = test.getId();

        Question question1 = new Question();
        question1.setTextQuestion("Question 1");
        question1.setTheme("Theme 1");
        question1.setQuestionType(QuestionType.SINGLE);
        question1 = questionRepository.save(question1);

        Question question2 = new Question();
        question2.setTextQuestion("Question 2");
        question2.setTheme("Theme 2");
        question2.setQuestionType(QuestionType.MULTIPLE);
        question2 = questionRepository.save(question2);

        createQuestionToTest(test, question1, 1, 5);
        createQuestionToTest(test, question2, 3, 10);

        List<QuestionToTest> result = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 2, 4);

        assertNotNull(result);
        assertEquals(1, result.size());

        QuestionToTest found = result.get(0);
        assertEquals(testId, found.getTest().getId());
        assertEquals(question2.getId(), found.getQuestion().getId());
        assertEquals(3, found.getSortingOrder());
        assertEquals(10, found.getNumberPoints());

        List<QuestionToTest> allResults = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 1, 5);

        assertNotNull(allResults);
        assertEquals(2, allResults.size());
    }

    /**
     * Тестирование поиска когда нет вопросов в указанном диапазоне
     */
    @Test
    void testFindByTestIdAndSortingOrderBetween_NoResults() {
        ru.KostarevaAnastasia.NauJava.models.Test test = createTest("Empty test");
        Long testId = test.getId();

        List<QuestionToTest> result = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 10, 20);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Тестирование поиска с граничными значениями
     */
    @Test
    void testFindByTestIdAndSortingOrderBetween_BoundaryValues() {
        ru.KostarevaAnastasia.NauJava.models.Test test = createTest("Boundary test");
        Long testId = test.getId();

        Question question = new Question();
        question.setTextQuestion("Boundary question");
        question.setTheme("Boundary theme");
        question.setQuestionType(QuestionType.SINGLE);
        question = questionRepository.save(question);
        createQuestionToTest(test, question, 5, 7);

        List<QuestionToTest> resultLower = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 1, 5);
        List<QuestionToTest> resultUpper = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 5, 10);
        List<QuestionToTest> resultExact = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 5, 5);

        assertEquals(1, resultLower.size());
        assertEquals(1, resultUpper.size());
        assertEquals(1, resultExact.size());
    }

    private ru.KostarevaAnastasia.NauJava.models.Test createTest(String title) {
        ru.KostarevaAnastasia.NauJava.models.Test test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle(title);
        User user = new User();
        user.setName("creator1");
        user.setRole(Role.USER);
        user = userRepository.save(user);
        test.setCreator(user);
        return testRepository.save(test);
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
