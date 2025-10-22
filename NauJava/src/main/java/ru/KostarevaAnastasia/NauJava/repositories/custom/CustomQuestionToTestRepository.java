package ru.KostarevaAnastasia.NauJava.repositories.custom;

import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;

import java.util.List;

public interface CustomQuestionToTestRepository {
    List<QuestionToTest> findQuestionToTestByTestIDAndSortingOrderBetween(Long testId, Integer minOrder, Integer maxOrder);
}
