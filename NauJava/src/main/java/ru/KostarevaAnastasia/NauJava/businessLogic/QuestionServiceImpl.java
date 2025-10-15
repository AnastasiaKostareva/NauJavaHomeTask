package ru.KostarevaAnastasia.NauJava.businessLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.dataAccessLayer.QuestionRepository;
import ru.KostarevaAnastasia.NauJava.models.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService
{
    private final QuestionRepository questionRepository;
    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository)
    {
        this.questionRepository = questionRepository;
    }
    @Override
    public void createQuestion(Long id, String textQuestion, String theme, List<String> options)
    {
        Question newQuestion = new Question();
        newQuestion.setId(id);
        newQuestion.setTextQuestion(textQuestion);
        newQuestion.setTheme(theme);
        newQuestion.setOptions(options);
        questionRepository.create(newQuestion);
    }
    @Override
    public Question findById(Long id)
    {
        return questionRepository.read(id);
    }
    @Override
    public void deleteById(Long id)
    {
        questionRepository.delete(id);
    }
    @Override
    public void updateText(Long id, String newText)
    {
        Question question = questionRepository.read(id);
        if (question != null) {
            question.setTextQuestion(newText);
            questionRepository.update(question);
        }
    }
    @Override
    public void updateTheme(Long id, String newTheme)
    {
        Question question = questionRepository.read(id);
        if (question != null) {
            question.setTheme(newTheme);
            questionRepository.update(question);
        }
    }
    @Override
    public void addOption(Long id, String newOption)
    {
        Question question = questionRepository.read(id);
        if (question != null) {
            question.getOptions().add(newOption);
            questionRepository.update(question);
        }
    }
    @Override
    public void deleteOption(Long id, String option)
    {
        Question question = questionRepository.read(id);
        if (question != null && question.getOptions() != null) {
            question.getOptions().remove(option);
            questionRepository.update(question);
        }
    }
}

