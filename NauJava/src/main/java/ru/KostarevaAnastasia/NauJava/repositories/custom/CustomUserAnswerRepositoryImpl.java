package ru.KostarevaAnastasia.NauJava.repositories.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.UserAnswer;

import java.util.List;

@Repository
public class CustomUserAnswerRepositoryImpl implements CustomUserAnswerRepository{
    private final EntityManager entityManager;

    @Autowired
    public CustomUserAnswerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<UserAnswer> findUserAnswersByUserIdAndQuestionTheme(Long userId, String theme) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserAnswer> query = criteriaBuilder.createQuery(UserAnswer.class);
        Root<UserAnswer> uaRoot = query.from(UserAnswer.class);
        Root<Question> questionRoot = query.from(Question.class);

        Predicate joinCondition = criteriaBuilder.equal(uaRoot.get("questionID"), questionRoot.get("id"));
        Predicate userIdPred = criteriaBuilder.equal(uaRoot.get("userID"), userId);
        Predicate themePred = criteriaBuilder.equal(questionRoot.get("theme"), theme);

        query.select(uaRoot)
                .where(criteriaBuilder.and(joinCondition, userIdPred, themePred));

        return entityManager.createQuery(query).getResultList();
    }
}
