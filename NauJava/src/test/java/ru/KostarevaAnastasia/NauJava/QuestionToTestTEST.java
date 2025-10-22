package ru.KostarevaAnastasia.NauJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;
import ru.KostarevaAnastasia.NauJava.models.QuestionType;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionToTestRepository;
import ru.KostarevaAnastasia.NauJava.repositories.TestRepository;
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

    /**
     * Тестирование поиска вопросов теста по диапазону порядка сортировки
     */
    @Test
    void testFindByTestIdAndSortingOrderBetween() {
        var test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle("Test for sorting order");
        test.setCreatorID(1L);
        test = testRepository.save(test);
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

        QuestionToTest qt1 = new QuestionToTest(testId, question1.getId(), 1, 5);
        QuestionToTest qt2 = new QuestionToTest(testId, question2.getId(), 3, 10);

        questionToTestRepository.save(qt1);
        questionToTestRepository.save(qt2);

        List<QuestionToTest> result = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 2, 4);

        assertNotNull(result);
        assertEquals(1, result.size());

        QuestionToTest found = result.get(0);
        assertEquals(testId, found.getTestId());
        assertEquals(question2.getId(), found.getQuestionId());
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
        // Создание и сохранение теста
        var test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle("Empty test");
        test.setCreatorID(1L);
        test = testRepository.save(test);
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
        var test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle("Boundary test");
        test.setCreatorID(1L);
        test = testRepository.save(test);
        Long testId = test.getId();

        Question question = new Question();
        question.setTextQuestion("Boundary question");
        question.setTheme("Boundary theme");
        question.setQuestionType(QuestionType.SINGLE);
        question = questionRepository.save(question);

        QuestionToTest qt = new QuestionToTest(testId, question.getId(), 5, 7);
        questionToTestRepository.save(qt);

        List<QuestionToTest> resultLower = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 1, 5);
        List<QuestionToTest> resultUpper = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 5, 10);
        List<QuestionToTest> resultExact = questionToTestRepository.findByTestIdAndSortingOrderBetween(testId, 5, 5);

        assertEquals(1, resultLower.size());
        assertEquals(1, resultUpper.size());
        assertEquals(1, resultExact.size());
    }
}