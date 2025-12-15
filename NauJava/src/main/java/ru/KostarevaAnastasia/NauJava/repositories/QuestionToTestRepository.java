package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTestId;

import java.util.List;

/**
 * Репозиторий для связей между вопросами и тестами
 */
@RepositoryRestResource(path = "questions-to-tests")
public interface QuestionToTestRepository
        extends CrudRepository<QuestionToTest, QuestionToTestId> {
    /**
     * Находит связи вопросов с тестом по идентификатору теста и диапазону порядковых номеров
     * @param testId идентификатор теста
     * @param minOrder минимальное значение порядкового номера
     * @param maxOrder максимальное значение порядкового номера
     * @return список связей
     */
    List<QuestionToTest> findByTestIdAndSortingOrderBetween(Long testId, Integer minOrder, Integer maxOrder);

    /**
     * Поиск вопросов для теста с заданным идентификатором
     * @param testId идентификатор
     * @return список сопоставлений вопроса с тестами
     */
    List<QuestionToTest> findByTestIdOrderBySortingOrderAsc(Long testId);

    /**
     * Количество для теста
     * @param testId идентификатор
     * @return количество
     */
    long countByTestId(Long testId);

    /**
     * Удаление вопроса из теста
     * @param testId идентификатор теста
     * @param questionId идентификатор вопроса
     */
    void deleteByTestIdAndQuestionId(Long testId, Long questionId);
}