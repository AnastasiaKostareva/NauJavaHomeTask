package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.models.Score;

import java.util.List;

public interface ScoreService {
    /**
     * Сохраняет оценку пользователя по прохождению теста
     * @param testId идентификатор теста
     * @param username имя пользователя
     * @param scoreValue набранная оценка
     */
    void saveScore(Long testId, String username, Integer scoreValue);

    /**
     * Ищет для пользователя список баллов
     * @param username имя пользователя
     * @return список набранных баллов
     */
    List<Score> findByUsername(String username);
}
