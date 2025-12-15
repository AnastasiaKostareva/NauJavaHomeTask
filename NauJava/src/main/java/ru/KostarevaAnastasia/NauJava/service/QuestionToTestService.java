package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;

public interface QuestionToTestService {
    /**
     * Сохранение сопоставления вопроса и теста
     * @param questionToTest сопоставление вопроса и теста
     * @return сущность QuestionToTest
     */
    QuestionToTest save(QuestionToTest questionToTest);

    /**
     * Получает количество тестов до теста с заданным идентификатором
     * @param testId идентификатор теста
     * @return количество
     */
    long countByTestId(Long testId);


    /**
     * Удаляем вопрос из теста
     * @param testId идентификатор теста
     * @param questionId идентификатор вопроса
     */
    void deleteByTestIdAndQuestionId(Long testId, Long questionId);
}