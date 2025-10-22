package ru.KostarevaAnastasia.NauJava;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.Option;
import ru.KostarevaAnastasia.NauJava.models.QuestionType;
import ru.KostarevaAnastasia.NauJava.repositories.OptionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;
import ru.KostarevaAnastasia.NauJava.service.QuestionService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@Transactional
public class CreateQuestionTest {
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public CreateQuestionTest(QuestionService questionService,
                              QuestionRepository questionRepository,
                              OptionRepository optionRepository) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    /**
     * Тестирование создания вопроса с вариантами ответов в транзакции
     */
    @Test
    void testCreateQuestionWithOptions() {
        Question question = new Question();
        question.setTextQuestion("What is 2+2?");
        question.setTheme("Mathematics");
        question.setQuestionType(QuestionType.SINGLE);

        Option option1 = new Option();
        option1.setText("3");
        option1.setCorrect(false);

        Option option2 = new Option();
        option2.setText("4");
        option2.setCorrect(true);

        Option option3 = new Option();
        option3.setText("5");
        option3.setCorrect(false);

        List<Option> options = List.of(option1, option2, option3);

        questionService.createQuestionWithOptions(question, options);

        Optional<Question> savedQuestion = questionRepository.findById(question.getId());
        assertTrue(savedQuestion.isPresent());
        assertEquals("What is 2+2?", savedQuestion.get().getTextQuestion());
        assertEquals("Mathematics", savedQuestion.get().getTheme());
        assertEquals(QuestionType.SINGLE, savedQuestion.get().getQuestionType());

        List<Option> savedOptions = optionRepository.findByQuestionID(question.getId());
        assertEquals(3, savedOptions.size());

        assertTrue(savedOptions.stream().allMatch(option -> option.getQuestionID().equals(question.getId())));

        Option savedOption1 = savedOptions.get(0);
        Option savedOption2 = savedOptions.get(1);
        Option savedOption3 = savedOptions.get(2);

        assertEquals("3", savedOption1.getText());
        assertFalse(savedOption1.getCorrect());

        assertEquals("4", savedOption2.getText());
        assertTrue(savedOption2.getCorrect());

        assertEquals("5", savedOption3.getText());
        assertFalse(savedOption3.getCorrect());
    }
}
