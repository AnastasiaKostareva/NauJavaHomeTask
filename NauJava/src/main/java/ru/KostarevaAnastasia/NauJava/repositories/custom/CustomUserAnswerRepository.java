package ru.KostarevaAnastasia.NauJava.repositories.custom;

import ru.KostarevaAnastasia.NauJava.models.UserAnswer;

import java.util.List;

/**
 * Кастомный репозиторий для сущности UserAnswer.
 */
public interface CustomUserAnswerRepository {
    /**
     * Находит ответы пользователя по идентификатору пользователя и теме вопроса.
     * @param userId идентификатор пользователя
     * @param theme  тема вопроса
     * @return список сущностей {@link UserAnswer}, соответствующих критериям
     */
    List<UserAnswer> findUserAnswersByUserIdAndQuestionTheme(Long userId, String theme);
}
