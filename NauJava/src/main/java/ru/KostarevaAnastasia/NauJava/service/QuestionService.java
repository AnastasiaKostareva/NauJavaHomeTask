package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.Option;

import java.util.List;

public interface QuestionService {

    /**
     * Создаёт вопрос вместе со списком вариантов ответов транзакционно.
     * @param question вопрос
     * @param options список вариантов
     */
    void createQuestionWithOptions(Question question, List<Option> options);
}