package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTestId;
import ru.KostarevaAnastasia.NauJava.repositories.custom.CustomQuestionToTestRepository;

import java.util.List;

/**
 * Репозиторий для связей между вопросами и тестами
 */
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
}