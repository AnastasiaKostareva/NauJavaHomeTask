package ru.KostarevaAnastasia.NauJava.repositories.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;

import java.util.List;

@Repository
public class CustomQuestionToTestRepositoryImpl implements CustomQuestionToTestRepository {
    private final EntityManager entityManager;

    @Autowired
    public CustomQuestionToTestRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<QuestionToTest> findQuestionToTestByTestIDAndSortingOrderBetween(Long testId, Integer minOrder, Integer maxOrder) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionToTest> query = criteriaBuilder.createQuery(QuestionToTest.class);
        Root<QuestionToTest> root = query.from(QuestionToTest.class);

        Predicate predicateTestId = criteriaBuilder.equal(root.get("test").get("id"), testId);
        Predicate predicateOrder = criteriaBuilder.between(root.get("sortingOrder"), minOrder, maxOrder);

        query.select(root).where(criteriaBuilder.and(predicateTestId, predicateOrder));

        return entityManager.createQuery(query).getResultList();
    }
}
