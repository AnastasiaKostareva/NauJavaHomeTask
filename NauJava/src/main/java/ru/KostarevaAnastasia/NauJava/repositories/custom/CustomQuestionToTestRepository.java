package ru.KostarevaAnastasia.NauJava.repositories.custom;

import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;

import java.util.List;

/**
 * Кастомный репозиторий для сущности QuestionToTest.
 */
public interface CustomQuestionToTestRepository {
    /**
     * Находит связи вопросов с тестом по идентификатору теста и диапазону порядковых номеров.
     * @param testId    идентификатор теста
     * @param minOrder  минимальное значение порядкового номера (включительно)
     * @param maxOrder  максимальное значение порядкового номера (включительно)
     * @return список сущностей QuestionToTest, соответствующих критериям
     */
    List<QuestionToTest> findQuestionToTestByTestIDAndSortingOrderBetween(Long testId, Integer minOrder, Integer maxOrder);
}
