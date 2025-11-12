package ru.KostarevaAnastasia.NauJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.KostarevaAnastasia.NauJava.models.*;
import ru.KostarevaAnastasia.NauJava.repositories.*;

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

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    private User createUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

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

        Option optionMath1 = optionRepository.save(createOption(questionMath1));
        Option optionMath2 = optionRepository.save(createOption(questionMath2));
        Option optionHistory = optionRepository.save(createOption(questionHistory));

        User user = new User();
        user.setName("Test User");
        user.setLogin("testuser");
        user.setRole(Role.USER);
        user = userRepository.save(user);
        Long userId = user.getId();
        var test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle("new Test");
        test = testRepository.save(test);

        UserAnswer userAnswerMath1 = new UserAnswer();
        userAnswerMath1.setId(new UserAnswerId(
                user.getId(),
                optionMath1.getId(),
                test.getId(),
                questionMath1.getId()
        ));
        userAnswerMath1.setUser(user);
        userAnswerMath1.setQuestion(questionMath1);
        userAnswerMath1.setTest(test);
        userAnswerMath1.setOption(optionMath1);
        userAnswerRepository.save(userAnswerMath1);

        UserAnswer userAnswerMath2 = new UserAnswer();
        userAnswerMath2.setId(new UserAnswerId(
                user.getId(),
                optionMath2.getId(),
                test.getId(),
                questionMath2.getId()
        ));
        userAnswerMath2.setUser(user);
        userAnswerMath2.setQuestion(questionMath2);
        userAnswerMath2.setTest(test);
        userAnswerMath2.setOption(optionMath2);
        userAnswerRepository.save(userAnswerMath2);

        UserAnswer userAnswerHistory = new UserAnswer();
        userAnswerHistory.setId(new UserAnswerId(
                user.getId(),
                optionHistory.getId(),
                test.getId(),
                questionHistory.getId()
        ));
        userAnswerHistory.setUser(user);
        userAnswerHistory.setQuestion(questionHistory);
        userAnswerHistory.setTest(test);
        userAnswerHistory.setOption(optionHistory);
        userAnswerRepository.save(userAnswerHistory);

        List<UserAnswer> mathAnswers = userAnswerRepository.findByUserIdAndQuestionTheme(userId, "Mathematics");
        assertNotNull(mathAnswers);
        assertEquals(2, mathAnswers.size());

        for (UserAnswer answer : mathAnswers) {
            assertEquals(userId, answer.getUser().getId());
            Question question = questionRepository.findById(answer.getQuestion().getId()).orElse(null);
            assertNotNull(question);
            assertEquals("Mathematics", question.getTheme());
        }

        List<UserAnswer> historyAnswers = userAnswerRepository.findByUserIdAndQuestionTheme(userId, "History");
        assertNotNull(historyAnswers);
        assertEquals(1, historyAnswers.size());

        UserAnswer historyAnswer = historyAnswers.get(0);
        assertEquals(userId, historyAnswer.getUser().getId());
        assertEquals(questionHistory.getId(), historyAnswer.getQuestion().getId());

        Question historyQuestion = questionRepository.findById(historyAnswer.getQuestion().getId()).orElse(null);
        assertNotNull(historyQuestion);
        assertEquals("History", historyQuestion.getTheme());
    }

    /**
     * Тестирование поиска когда у пользователя нет ответов по указанной теме
     */
    @Test
    void testFindByUserIdAndQuestionTheme_NoAnswersForTheme() {
        User user = new User();
        user.setName("Test User");
        user.setLogin("testuser");
        user.setRole(Role.USER);
        user = userRepository.save(user);
        Long userId = user.getId();
        var test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle("new Test2");
        test = testRepository.save(test);

        Question questionPhysics = new Question();
        questionPhysics.setTextQuestion("What is gravity?");
        questionPhysics.setTheme("Physics");
        questionPhysics.setQuestionType(QuestionType.SINGLE);
        questionPhysics = questionRepository.save(questionPhysics);

        Option option = optionRepository.save(createOption(questionPhysics));

        UserAnswer userAnswerPhysics = new UserAnswer();
        userAnswerPhysics.setId(new UserAnswerId(
                user.getId(),
                option.getId(),
                test.getId(),
                questionPhysics.getId()
        ));
        userAnswerPhysics.setUser(user);
        userAnswerPhysics.setQuestion(questionPhysics);
        userAnswerPhysics.setTest(test);
        userAnswerPhysics.setOption(option);
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
        User user = new User();
        user.setName("Test User");
        user.setLogin("testuser");
        user.setRole(Role.USER);
        user = userRepository.save(user);
        Long userId = user.getId();
        var test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle("new Test3");
        test = testRepository.save(test);

        Question question = new Question();
        question.setTextQuestion("Sample question");
        question.setTheme("ExistingTheme");
        question.setQuestionType(QuestionType.SINGLE);
        question = questionRepository.save(question);

        Option option = optionRepository.save(createOption(question));

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setId(new UserAnswerId(
                user.getId(),
                option.getId(),
                test.getId(),
                question.getId()
        ));
        userAnswer.setUser(user);
        userAnswer.setQuestion(question);
        userAnswer.setTest(test);
        userAnswer.setOption(option);
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
        User user1 = new User();
        user1.setName("Test User");
        user1.setLogin("testuser");
        user1.setRole(Role.USER);
        user1 = userRepository.save(user1);
        Long user1Id = user1.getId();
        User user2 = new User();
        user2.setName("Test User");
        user2.setLogin("testuser");
        user2.setRole(Role.USER);
        user2 = userRepository.save(user2);
        Long user2Id = user2.getId();
        var test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle("new Test4");
        test = testRepository.save(test);

        Question questionMath = new Question();
        questionMath.setTextQuestion("Algebra question");
        questionMath.setTheme("Mathematics");
        questionMath.setQuestionType(QuestionType.SINGLE);
        questionMath = questionRepository.save(questionMath);

        Option option = optionRepository.save(createOption(questionMath));

        UserAnswer user1Answer = new UserAnswer();
        user1Answer.setId(new UserAnswerId(
                user1.getId(),
                option.getId(),
                test.getId(),
                questionMath.getId()
        ));
        user1Answer.setUser(createUser(user1Id));
        user1Answer.setQuestion(questionMath);
        user1Answer.setTest(test);
        user1Answer.setOption(createOption(questionMath));
        userAnswerRepository.save(user1Answer);

        UserAnswer user2Answer = new UserAnswer();
        user2Answer.setId(new UserAnswerId(
                user2.getId(),
                option.getId(),
                test.getId(),
                questionMath.getId()
        ));
        user2Answer.setUser(createUser(user2Id));
        user2Answer.setQuestion(questionMath);
        user2Answer.setTest(test);
        user2Answer.setOption(createOption(questionMath));
        userAnswerRepository.save(user2Answer);

        List<UserAnswer> user1Answers = userAnswerRepository.findByUserIdAndQuestionTheme(user1Id, "Mathematics");

        assertNotNull(user1Answers);
        assertEquals(1, user1Answers.size());
        assertEquals(user1Id, user1Answers.get(0).getUser().getId());

        List<UserAnswer> user2Answers = userAnswerRepository.findByUserIdAndQuestionTheme(user2Id, "Mathematics");

        assertNotNull(user2Answers);
        assertEquals(1, user2Answers.size());
        assertEquals(user2Id, user2Answers.get(0).getUser().getId());
    }

    private ru.KostarevaAnastasia.NauJava.models.Option createOption(Question question) {
        ru.KostarevaAnastasia.NauJava.models.Option option = new ru.KostarevaAnastasia.NauJava.models.Option();
        option.setQuestion(question);
        return option;
    }
}