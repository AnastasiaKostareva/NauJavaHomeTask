package ru.KostarevaAnastasia.NauJava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.KostarevaAnastasia.NauJava.models.*;
import ru.KostarevaAnastasia.NauJava.repositories.*;
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
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private CustomUserAnswerRepository customUserAnswerRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;


    private User createUser(String name) {
        User user = new User();
        user.setUsername(name);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    private ru.KostarevaAnastasia.NauJava.models.Test createTest(String title) {
        ru.KostarevaAnastasia.NauJava.models.Test test = new ru.KostarevaAnastasia.NauJava.models.Test();
        test.setTitle(title);
        return testRepository.save(test);
    }

    private Option createOption(String text, boolean correct, Question question) {
        Option option = new Option();
        option.setText(text);
        option.setCorrect(correct);
        option.setQuestion(question);
        return optionRepository.save(option);
    }

    private Question createQuestion(String text, String theme, QuestionType type, User author) {
        Question question = new Question();
        question.setTextQuestion(text);
        question.setTheme(theme);
        question.setQuestionType(type);
        question.setAuthor(author);
        entityManager.persist(question);
        entityManager.flush();
        return question;
    }

    private UserAnswer createUserAnswer(User user, Question question, ru.KostarevaAnastasia.NauJava.models.Test test, Option option) {
        UserAnswer ua = new UserAnswer();
        ua.setId(new UserAnswerId(
                user.getId(),
                option.getId(),
                test.getId(),
                question.getId()
        ));
        ua.setUser(user);
        ua.setQuestion(question);
        ua.setTest(test);
        ua.setOption(option);
        return userAnswerRepository.save(ua);
    }

    /**
     * Тестирование Criteria API метода для UserAnswer - разные пользователи
     */
    @Test
    void testFindUserAnswersByUserIdAndQuestionTheme_DifferentUsers() {
        User user1 = createUser("user1");
        User user2 = createUser("user2");
        ru.KostarevaAnastasia.NauJava.models.Test test = createTest("Math Test");

        User author = new User();
        author.setUsername("testuser");
        author.setPassword(passwordEncoder.encode("password"));
        author.setRole(Role.CREATOR);
        author = userRepository.save(author);

        Question mathQuestion = createQuestion("2+2?", "Mathematics", QuestionType.SINGLE, author);
        Option opt1 = createOption("3", false, mathQuestion);
        Option opt2 = createOption("4", true, mathQuestion);

        createUserAnswer(user1, mathQuestion, test, opt1);
        createUserAnswer(user2, mathQuestion, test, opt2);

        List<UserAnswer> user1Answers = customUserAnswerRepository
                .findUserAnswersByUserIdAndQuestionTheme(user1.getId(), "Mathematics");
        assertEquals(1, user1Answers.size());
        assertEquals(user1.getId(), user1Answers.get(0).getUser().getId());

        List<UserAnswer> user2Answers = customUserAnswerRepository
                .findUserAnswersByUserIdAndQuestionTheme(user2.getId(), "Mathematics");
        assertEquals(1, user2Answers.size());
        assertEquals(user2.getId(), user2Answers.get(0).getUser().getId());
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
        User user = createUser("user");
        ru.KostarevaAnastasia.NauJava.models.Test test = createTest("General Knowledge");

        User author = new User();
        author.setUsername("testuser");
        author.setPassword(passwordEncoder.encode("password"));
        author.setRole(Role.CREATOR);
        author = userRepository.save(author);

        Question math1 = createQuestion("2+2", "Mathematics", QuestionType.SINGLE, author);
        Question math2 = createQuestion("3*3", "Mathematics", QuestionType.MULTIPLE, author);
        Question history = createQuestion("WW2", "History", QuestionType.MULTIPLE, author);
        Question physics = createQuestion("F=ma", "Physics", QuestionType.SINGLE, author);

        Option o1 = createOption("4", true, math1);
        Option o2 = createOption("9", true, math2);
        Option o3 = createOption("1939", true, history);
        Option o4 = createOption("Newton", true, physics);

        createUserAnswer(user, math1, test, o1);
        createUserAnswer(user, math2, test, o2);
        createUserAnswer(user, history, test, o3);
        createUserAnswer(user, physics, test, o4);

        List<UserAnswer> mathAnswers = customUserAnswerRepository
                .findUserAnswersByUserIdAndQuestionTheme(user.getId(), "Mathematics");

        assertEquals(2, mathAnswers.size());
        assertTrue(mathAnswers.stream().allMatch(ua -> ua.getUser().getId().equals(user.getId())));
    }

    /**
     * Сравнительный тест: @Query vs Criteria API
     */
    @Test
    void testComparison_QueryVsCriteriaAPI() {
        User user = createUser("user");
        ru.KostarevaAnastasia.NauJava.models.Test test = createTest("Comparison Test");

        User author = new User();
        author.setUsername("testuser");
        author.setPassword(passwordEncoder.encode("password"));
        author.setRole(Role.CREATOR);
        author = userRepository.save(author);

        Question math1 = createQuestion("Math Q1", "Mathematics", QuestionType.SINGLE, author);
        Question math2 = createQuestion("Math Q2", "Mathematics", QuestionType.MULTIPLE,author);
        Question history = createQuestion("Hist Q1", "History", QuestionType.MULTIPLE, author);

        Option o1 = createOption("A", false, math1);
        Option o2 = createOption("B", true, math2);
        Option o3 = createOption("C", true, history);

        createUserAnswer(user, math1, test, o1);
        createUserAnswer(user, math2, test, o2);
        createUserAnswer(user, history, test, o3);

        List<UserAnswer> queryResult = userAnswerRepository
                .findByUserIdAndQuestionTheme(user.getId(), "Mathematics");

        List<UserAnswer> criteriaAPIResult = customUserAnswerRepository
                .findUserAnswersByUserIdAndQuestionTheme(user.getId(), "Mathematics");

        assertNotNull(queryResult);
        assertNotNull(criteriaAPIResult);
        assertEquals(2, queryResult.size());
        assertEquals(2, criteriaAPIResult.size());

        var queryIds = queryResult.stream()
                .map(ua -> ua.getQuestion().getId())
                .sorted()
                .toList();
        var criteriaIds = criteriaAPIResult.stream()
                .map(ua -> ua.getQuestion().getId())
                .sorted()
                .toList();

        assertEquals(queryIds, criteriaIds);
    }
}