package ru.KostarevaAnastasia.NauJava.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.KostarevaAnastasia.NauJava.models.Option;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.repositories.OptionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;

@Service("questionServiceImplV2")
public class QuestionServiceImpl implements QuestionService
{
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final PlatformTransactionManager transactionManager;
    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, OptionRepository optionRepository,
                               PlatformTransactionManager transactionManager)
    {
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.transactionManager = transactionManager;
    }
    @Override
    public void createQuestionWithOptions(Question question, List<Option> options)
    {
        TransactionStatus status = transactionManager.getTransaction(new
                DefaultTransactionDefinition());
        try
        {
            Question savedQuestion = questionRepository.save(question);
            for (Option option : options) {
                option.setQuestion(savedQuestion);
                optionRepository.save(option);
            }
            transactionManager.commit(status);
        }
        catch (RuntimeException ex)
        {
            transactionManager.rollback(status);
            throw ex;
        }
    }
}

