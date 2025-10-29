package ru.KostarevaAnastasia.NauJava.repositories.custom;

import ru.KostarevaAnastasia.NauJava.models.UserAnswer;

import java.util.List;

public interface CustomUserAnswerRepository {
    List<UserAnswer> findUserAnswersByUserIdAndQuestionTheme(Long userId, String theme);
}
