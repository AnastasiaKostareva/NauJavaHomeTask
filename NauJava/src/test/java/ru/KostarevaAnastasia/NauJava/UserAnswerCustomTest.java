package ru.KostarevaAnastasia.NauJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.QuestionType;
import ru.KostarevaAnastasia.NauJava.models.UserAnswer;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.UserAnswerRepository;
import ru.KostarevaAnastasia.NauJava.repositories.custom.CustomUserAnswerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@Transactional
class UserAnswerCustomTest {

    @Autowired
    private CustomUserAnswerRepository customUserAnswerRepository; // Теперь будет работать

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Тестирование Criteria API метода для UserAnswer - разные пользователи
     */
    @Test
    void testFindUserAnswersByUserIdAndQuestionTheme_DifferentUsers() {
        Long user1Id = 1L;
        Long user2Id = 2L;
        Long testId = 1L;

        Question mathQuestion = createQuestion("Math", "Mathematics", QuestionType.SINGLE);
        createUserAnswer(user1Id, mathQuestion.getId(), testId, 101L);
        createUserAnswer(user2Id, mathQuestion.getId(), testId, 102L);
        List<UserAnswer> user1Answers = customUserAnswerRepository
                .findUserAnswersByUserIdAndQuestionTheme(user1Id, "Mathematics");

        assertEquals(1, user1Answers.size());
        assertEquals(user1Id, user1Answers.get(0).getUserID());
        List<UserAnswer> user2Answers = customUserAnswerRepository
                .findUserAnswersByUserIdAndQuestionTheme(user2Id, "Mathematics");

        assertEquals(1, user2Answers.size());
        assertEquals(user2Id, user2Answers.get(0).getUserID());
    }

    /**
     * Тестирование Criteria API метода для UserAnswer - пустой результат
     */
    @Test
    void testFindUserAnswersByUserIdAndQuestionTheme_EmptyResult() {
        List<UserAnswer> result = customUserAnswerRepository
                .findUserAnswersByUserIdAndQuestionTheme(999L, "NonExistentTheme");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Тестирование Criteria API метода для UserAnswer - базовый сценарий
     */
    @Test
    void testFindUserAnswersByUserIdAndQuestionTheme_BasicScenario() {
        Long userId = 1L;
        Long testId = 1L;

        Question mathQuestion1 = createQuestion("Math 1", "Mathematics", QuestionType.SINGLE);
        Question mathQuestion2 = createQuestion("Math 2", "Mathematics", QuestionType.MULTIPLE);
        Question historyQuestion = createQuestion("History", "History", QuestionType.MULTIPLE);
        Question physicsQuestion = createQuestion("Physics", "Physics", QuestionType.SINGLE);

        createUserAnswer(userId, mathQuestion1.getId(), testId, 101L);
        createUserAnswer(userId, mathQuestion2.getId(), testId, 102L);
        createUserAnswer(userId, historyQuestion.getId(), testId, 201L);
        createUserAnswer(userId, physicsQuestion.getId(), testId, 301L);

        List<UserAnswer> mathAnswers = customUserAnswerRepository
                .findUserAnswersByUserIdAndQuestionTheme(userId, "Mathematics");

        assertNotNull(mathAnswers);
        assertEquals(2, mathAnswers.size());

        // Проверяем что все ответы принадлежат правильному пользователю
        assertTrue(mathAnswers.stream().allMatch(ua -> ua.getUserID().equals(userId)));
    }

    /**
     * Сравнительный тест: @Query vs Criteria API
     */
    @Test
    void testComparison_QueryVsCriteriaAPI() {
        Long userId = 1L;
        Long testId = 1L;

        Question mathQuestion1 = createQuestion("Math 1", "Mathematics", QuestionType.SINGLE);
        Question mathQuestion2 = createQuestion("Math 2", "Mathematics", QuestionType.MULTIPLE);
        Question historyQuestion = createQuestion("History", "History", QuestionType.MULTIPLE);

        createUserAnswer(userId, mathQuestion1.getId(), testId, 101L);
        createUserAnswer(userId, mathQuestion2.getId(), testId, 102L);
        createUserAnswer(userId, historyQuestion.getId(), testId, 201L);

        List<UserAnswer> queryResult = userAnswerRepository
                .findByUserIdAndQuestionTheme(userId, "Mathematics");

        List<UserAnswer> criteriaAPIResult = customUserAnswerRepository
                .findUserAnswersByUserIdAndQuestionTheme(userId, "Mathematics");

        assertNotNull(queryResult);
        assertNotNull(criteriaAPIResult);
        assertEquals(queryResult.size(), criteriaAPIResult.size());

        assertEquals(2, queryResult.size());
        assertEquals(2, criteriaAPIResult.size());

        List<Long> queryQuestionIds = queryResult.stream()
                .map(UserAnswer::getQuestionID)
                .sorted()
                .toList();
        List<Long> criteriaAPIQuestionIds = criteriaAPIResult.stream()
                .map(UserAnswer::getQuestionID)
                .sorted()
                .toList();

        assertEquals(queryQuestionIds, criteriaAPIQuestionIds);
    }


    private Question createQuestion(String text, String theme, QuestionType type) {
        Question question = new Question();
        question.setTextQuestion(text);
        question.setTheme(theme);
        question.setQuestionType(type);
        return questionRepository.save(question);
    }

    private void createUserAnswer(Long userId, Long questionId, Long testId, Long optionId) {
        UserAnswer ua = new UserAnswer();
        ua.setUserID(userId);
        ua.setQuestionID(questionId);
        ua.setTestID(testId);
        ua.setOptionID(optionId);
        entityManager.persist(ua);
        entityManager.flush();
    }
}
