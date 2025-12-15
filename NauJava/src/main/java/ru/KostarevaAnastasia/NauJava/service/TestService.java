package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.models.Test;

import java.util.List;

public interface TestService {
    /**
     * Получает список всех тестов
     * @return список тестов
     */
    List<Test> findAll();

    /**
     * Ищет тест по идентификатору
     * @param id идентификатор запрашиваемого теста
     * @return тест
     */
    Test findById(Long id);

    /**
     * Сохраняет тест
     * @param test сам тест
     * @return сохраненный тест
     */
    Test save(Test test);

    /**
     * Ищет тест по идентификатору и вопросы для него
     * @param testId идентификатор запрашиваемого теста
     * @return тест с вопросами для него
     */
    Test findWithQuestions(Long testId);

    /**
     * Считает количество вопросов в тесте
     * @param testId идентификатор запрашиваемого теста
     * @return количество вопросов
     */
    long countQuestionsInTest(Long testId);
}
