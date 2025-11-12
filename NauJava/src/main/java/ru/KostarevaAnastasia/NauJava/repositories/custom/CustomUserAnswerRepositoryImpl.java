package ru.KostarevaAnastasia.NauJava.repositories.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.User;
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

        Join<UserAnswer, Question> questionJoin = uaRoot.join("question");
        Join<UserAnswer, User> userJoin = uaRoot.join("user");

        Predicate userIdPred = criteriaBuilder.equal(userJoin.get("id"), userId);
        Predicate themePred = criteriaBuilder.equal(questionJoin.get("theme"), theme);

        query.select(uaRoot).where(criteriaBuilder.and(userIdPred, themePred));

        return entityManager.createQuery(query).getResultList();
    }
}
