package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.dto.TestSessionDto;

public interface TestSessionService {
    /**
     * Создание сессии прохождения теста
     * @param testId идентификатор теста
     * @return сессию прохождения теста
     */
    TestSessionDto startTest(Long testId);
}