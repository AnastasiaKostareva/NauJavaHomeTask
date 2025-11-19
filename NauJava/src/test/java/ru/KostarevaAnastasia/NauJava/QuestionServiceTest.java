package ru.KostarevaAnastasia.NauJava;

import org.hibernate.TransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import ru.KostarevaAnastasia.NauJava.models.Option;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.QuestionType;
import ru.KostarevaAnastasia.NauJava.repositories.OptionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;
import ru.KostarevaAnastasia.NauJava.service.QuestionServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {
    private QuestionRepository mockQuestionRepo;
    private OptionRepository mockOptionRepo;
    private PlatformTransactionManager mockTransactionManager;
    private QuestionServiceImpl questionService;

    @BeforeEach
    public void setUp() {
        mockQuestionRepo = mock(QuestionRepository.class);
        mockOptionRepo = mock(OptionRepository.class);
        mockTransactionManager = mock(PlatformTransactionManager.class);

        questionService = new QuestionServiceImpl(mockQuestionRepo, mockOptionRepo, mockTransactionManager);
    }

    @Test
    void shouldCreateQuestionWithOptions()
    {
        Question question = new Question(null, "test question", "test", QuestionType.SINGLE);
        List<Option> options = Arrays.asList(new Option(), new Option());

        Question afterSaveQuestion = new Question(1L, "test question", "test", QuestionType.SINGLE);
        when(mockQuestionRepo.save(question)).thenReturn(afterSaveQuestion);

        TransactionStatus status = mock(TransactionStatus.class);
        when(mockTransactionManager.getTransaction(any())).thenReturn(status);

        questionService.createQuestionWithOptions(question, options);
        verify(mockQuestionRepo).save(question);
        verify(mockOptionRepo, times(2)).save(argThat(opt ->
                opt.getQuestion() != null && opt.getQuestion().getId().equals(1L)
        ));

        verify(mockTransactionManager).commit(status);
    }

    @Test
    void shouldCreateQuestionWithEmptyOptions()
    {
        Question question = new Question(null, "test question", "test", QuestionType.SINGLE);
        List<Option> options = new ArrayList<>();

        Question afterSaveQuestion = new Question(1L, "test question", "test", QuestionType.SINGLE);
        when(mockQuestionRepo.save(question)).thenReturn(afterSaveQuestion);

        TransactionStatus status = mock(TransactionStatus.class);
        when(mockTransactionManager.getTransaction(any())).thenReturn(status);

        questionService.createQuestionWithOptions(question, options);
        verify(mockQuestionRepo).save(question);
        verify(mockOptionRepo, never()).save(any());
        verify(mockTransactionManager).commit(status);
    }

    @Test
    void shouldRollbackTransactionWhenQuestionSaveFails()
    {
        Question question = new Question(null, "test question", "test", QuestionType.SINGLE);
        List<Option> options = Arrays.asList(new Option(), new Option());

        TransactionStatus status = mock(TransactionStatus.class);
        when(mockTransactionManager.getTransaction(any())).thenReturn(status);

        when(mockQuestionRepo.save(question)).thenThrow(new DataAccessException("error in question repo") {});

        DataAccessException exception = Assertions.assertThrows(DataAccessException.class, () -> {
            questionService.createQuestionWithOptions(question, options);
        });

        Assertions.assertEquals("error in question repo", exception.getMessage());
        verify(mockTransactionManager).rollback(status);
        verify(mockTransactionManager, never()).commit(status);
        verify(mockOptionRepo, never()).save(any(Option.class));
    }

    @Test
    void shouldRollbackTransactionWhenOptionSaveFails()
    {
        Question question = new Question(null, "test question", "test", QuestionType.SINGLE);
        List<Option> options = Arrays.asList(new Option(), new Option());

        Question afterSaveQuestion = new Question(1L, "test question", "test", QuestionType.SINGLE);
        when(mockQuestionRepo.save(question)).thenReturn(afterSaveQuestion);

        TransactionStatus status = mock(TransactionStatus.class);
        when(mockTransactionManager.getTransaction(any())).thenReturn(status);

        when(mockOptionRepo.save(options.get(0))).thenReturn(options.get(0));
        when(mockOptionRepo.save(options.get(1))).thenThrow(new DataAccessException("error: option save failed") {});

        DataAccessException exception = Assertions.assertThrows(DataAccessException.class, () -> {
            questionService.createQuestionWithOptions(question, options);
        });

        Assertions.assertEquals("error: option save failed", exception.getMessage());
        verify(mockTransactionManager).rollback(status);
        verify(mockTransactionManager, never()).commit(status);
        verify(mockOptionRepo, times(2)).save(any(Option.class));
    }

    @Test
    void shouldRollbackTransactionWhenTransactionFails()
    {
        Question question = new Question(null, "test question", "test", QuestionType.SINGLE);
        List<Option> options = Arrays.asList(new Option(), new Option());

        Question afterSaveQuestion = new Question(1L, "test question", "test", QuestionType.SINGLE);
        when(mockQuestionRepo.save(question)).thenReturn(afterSaveQuestion);

        TransactionStatus status = mock(TransactionStatus.class);
        when(mockTransactionManager.getTransaction(any())).thenReturn(status);
        doThrow(new TransactionException("error on commit")).when(mockTransactionManager).commit(status);

        when(mockOptionRepo.save(options.get(0))).thenReturn(options.get(0));
        when(mockOptionRepo.save(options.get(1))).thenReturn(options.get(1));

        TransactionException exception = Assertions.assertThrows(TransactionException.class, () -> {
            questionService.createQuestionWithOptions(question, options);
        });

        Assertions.assertEquals("error on commit", exception.getMessage());
        verify(mockTransactionManager).rollback(status);
        verify(mockOptionRepo, times(2)).save(any(Option.class));
    }
}
