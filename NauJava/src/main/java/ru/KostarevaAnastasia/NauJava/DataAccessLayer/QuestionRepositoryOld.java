package ru.KostarevaAnastasia.NauJava.DataAccessLayer;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.KostarevaAnastasia.NauJava.models.Question;
@Component
public class QuestionRepositoryOld implements CrudRepository<Question, Long>
{
    private final List<Question> questionContainer;
    @Autowired
    public QuestionRepositoryOld(List<Question> questionContainer)
    {
        this.questionContainer = questionContainer;
    }
    @Override
    public void create(Question question)
    {
        questionContainer.add(question);
    }
    @Override
    public Question read(Long id)
    {
        return questionContainer.stream()
                .filter(x -> Objects.equals(x.getId(), id))
                .findFirst()
                .orElse(null);
    }
    @Override
    public void update(Question question)
    {
        var id = question.getId();
        for (var i = 0; i < questionContainer.size(); i++) {
            if (Objects.equals(questionContainer.get(i).getId(), id)) {
                questionContainer.set(i, question);
                break;
            }
        }
    }
    @Override
    public void delete(Long id)
    {
        for (var i = 0; i < questionContainer.size(); i++) {
            if (Objects.equals(questionContainer.get(i).getId(), id)) {
                questionContainer.remove(i);
                break;
            }
        }
    }
}
