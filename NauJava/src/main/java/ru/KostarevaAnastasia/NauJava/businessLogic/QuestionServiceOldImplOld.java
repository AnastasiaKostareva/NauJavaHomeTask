package ru.KostarevaAnastasia.NauJava.businessLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.DataAccessLayer.QuestionRepositoryOld;
import ru.KostarevaAnastasia.NauJava.models.Question;

import java.util.List;

@Service("questionServiceImplV1")
public class QuestionServiceOldImplOld implements QuestionServiceOld
{
    private final QuestionRepositoryOld questionRepositoryOld;
    @Autowired
    public QuestionServiceOldImplOld(QuestionRepositoryOld questionRepositoryOld)
    {
        this.questionRepositoryOld = questionRepositoryOld;
    }
    @Override
    public void createQuestion(Long id, String textQuestion, String theme, List<String> options)
    {
        Question newQuestion = new Question();
        newQuestion.setId(id);
        newQuestion.setTextQuestion(textQuestion);
        newQuestion.setTheme(theme);
        questionRepositoryOld.create(newQuestion);
    }
    @Override
    public Question findById(Long id)
    {
        return questionRepositoryOld.read(id);
    }
    @Override
    public void deleteById(Long id)
    {
        questionRepositoryOld.delete(id);
    }
    @Override
    public void updateText(Long id, String newText)
    {
        Question question = questionRepositoryOld.read(id);
        if (question != null) {
            question.setTextQuestion(newText);
            questionRepositoryOld.update(question);
        }
    }
    @Override
    public void updateTheme(Long id, String newTheme)
    {
        Question question = questionRepositoryOld.read(id);
        if (question != null) {
            question.setTheme(newTheme);
            questionRepositoryOld.update(question);
        }
    }
}

