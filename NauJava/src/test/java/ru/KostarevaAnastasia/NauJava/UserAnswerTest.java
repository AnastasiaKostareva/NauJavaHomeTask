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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@Transactional
public class UserAnswerTest {
    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Тестирование поиска ответов пользователя по ID пользователя и теме вопроса
     */
    @Test
    void testFindByUserIdAndQuestionTheme() {
        Question questionMath1 = new Question();
        questionMath1.setTextQuestion("What is 2+2?");
        questionMath1.setTheme("Mathematics");
        questionMath1.setQuestionType(QuestionType.SINGLE);
        questionMath1 = questionRepository.save(questionMath1);

        Question questionMath2 = new Question();
        questionMath2.setTextQuestion("What is 3*3?");
        questionMath2.setTheme("Mathematics");
        questionMath2.setQuestionType(QuestionType.SINGLE);
        questionMath2 = questionRepository.save(questionMath2);

        Question questionHistory = new Question();
        questionHistory.setTextQuestion("When was WWII?");
        questionHistory.setTheme("History");
        questionHistory.setQuestionType(QuestionType.SINGLE);
        questionHistory = questionRepository.save(questionHistory);

        Long userId = 1L;
        Long testId = 1L;

        UserAnswer userAnswerMath1 = new UserAnswer();
        userAnswerMath1.setUserID(userId);
        userAnswerMath1.setQuestionID(questionMath1.getId());
        userAnswerMath1.setTestID(testId);
        userAnswerMath1.setOptionID(101L);
        userAnswerRepository.save(userAnswerMath1);

        UserAnswer userAnswerMath2 = new UserAnswer();
        userAnswerMath2.setUserID(userId);
        userAnswerMath2.setQuestionID(questionMath2.getId());
        userAnswerMath2.setTestID(testId);
        userAnswerMath2.setOptionID(102L);
        userAnswerRepository.save(userAnswerMath2);

        UserAnswer userAnswerHistory = new UserAnswer();
        userAnswerHistory.setUserID(userId);
        userAnswerHistory.setQuestionID(questionHistory.getId());
        userAnswerHistory.setTestID(testId);
        userAnswerHistory.setOptionID(201L);
        userAnswerRepository.save(userAnswerHistory);

        List<UserAnswer> mathAnswers = userAnswerRepository.findByUserIdAndQuestionTheme(userId, "Mathematics");
        assertNotNull(mathAnswers);
        assertEquals(2, mathAnswers.size());

        for (UserAnswer answer : mathAnswers) {
            assertEquals(userId, answer.getUserID());
            Question question = questionRepository.findById(answer.getQuestionID()).orElse(null);
            assertNotNull(question);
            assertEquals("Mathematics", question.getTheme());
        }

        List<UserAnswer> historyAnswers = userAnswerRepository.findByUserIdAndQuestionTheme(userId, "History");
        assertNotNull(historyAnswers);
        assertEquals(1, historyAnswers.size());

        UserAnswer historyAnswer = historyAnswers.get(0);
        assertEquals(userId, historyAnswer.getUserID());
        assertEquals(questionHistory.getId(), historyAnswer.getQuestionID());

        Question historyQuestion = questionRepository.findById(historyAnswer.getQuestionID()).orElse(null);
        assertNotNull(historyQuestion);
        assertEquals("History", historyQuestion.getTheme());
    }

    /**
     * Тестирование поиска когда у пользователя нет ответов по указанной теме
     */
    @Test
    void testFindByUserIdAndQuestionTheme_NoAnswersForTheme() {
        Long userId = 2L;
        Long testId = 2L;

        Question questionPhysics = new Question();
        questionPhysics.setTextQuestion("What is gravity?");
        questionPhysics.setTheme("Physics");
        questionPhysics.setQuestionType(QuestionType.SINGLE);
        questionPhysics = questionRepository.save(questionPhysics);

        UserAnswer userAnswerPhysics = new UserAnswer();
        userAnswerPhysics.setUserID(userId);
        userAnswerPhysics.setQuestionID(questionPhysics.getId());
        userAnswerPhysics.setTestID(testId);
        userAnswerPhysics.setOptionID(301L);
        userAnswerRepository.save(userAnswerPhysics);

        List<UserAnswer> biologyAnswers = userAnswerRepository.findByUserIdAndQuestionTheme(userId, "Biology");

        assertNotNull(biologyAnswers);
        assertTrue(biologyAnswers.isEmpty());
    }

    /**
     * Тестирование поиска когда пользователь не существует
     */
    @Test
    void testFindByUserIdAndQuestionTheme_NonExistentUser() {
        Long nonExistentUserId = 999L;
        String theme = "Mathematics";

        List<UserAnswer> results = userAnswerRepository.findByUserIdAndQuestionTheme(nonExistentUserId, theme);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    /**
     * Тестирование поиска когда тема не существует
     */
    @Test
    void testFindByUserIdAndQuestionTheme_NonExistentTheme() {
        Long userId = 3L;
        Long testId = 3L;

        Question question = new Question();
        question.setTextQuestion("Sample question");
        question.setTheme("ExistingTheme");
        question.setQuestionType(QuestionType.SINGLE);
        question = questionRepository.save(question);

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setUserID(userId);
        userAnswer.setQuestionID(question.getId());
        userAnswer.setTestID(testId);
        userAnswer.setOptionID(401L);
        userAnswerRepository.save(userAnswer);

        List<UserAnswer> results = userAnswerRepository.findByUserIdAndQuestionTheme(userId, "NonExistentTheme");

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    /**
     * Тестирование поиска для разных пользователей с одинаковыми темами
     */
    @Test
    void testFindByUserIdAndQuestionTheme_MultipleUsers() {
        Long user1Id = 10L;
        Long user2Id = 20L;
        Long testId = 10L;

        Question questionMath = new Question();
        questionMath.setTextQuestion("Algebra question");
        questionMath.setTheme("Mathematics");
        questionMath.setQuestionType(QuestionType.SINGLE);
        questionMath = questionRepository.save(questionMath);

        UserAnswer user1Answer = new UserAnswer();
        user1Answer.setUserID(user1Id);
        user1Answer.setQuestionID(questionMath.getId());
        user1Answer.setTestID(testId);
        user1Answer.setOptionID(501L);
        userAnswerRepository.save(user1Answer);

        UserAnswer user2Answer = new UserAnswer();
        user2Answer.setUserID(user2Id);
        user2Answer.setQuestionID(questionMath.getId());
        user2Answer.setTestID(testId);
        user2Answer.setOptionID(502L);
        userAnswerRepository.save(user2Answer);

        List<UserAnswer> user1Answers = userAnswerRepository.findByUserIdAndQuestionTheme(user1Id, "Mathematics");

        assertNotNull(user1Answers);
        assertEquals(1, user1Answers.size());
        assertEquals(user1Id, user1Answers.get(0).getUserID());

        List<UserAnswer> user2Answers = userAnswerRepository.findByUserIdAndQuestionTheme(user2Id, "Mathematics");

        assertNotNull(user2Answers);
        assertEquals(1, user2Answers.size());
        assertEquals(user2Id, user2Answers.get(0).getUserID());
    }
}