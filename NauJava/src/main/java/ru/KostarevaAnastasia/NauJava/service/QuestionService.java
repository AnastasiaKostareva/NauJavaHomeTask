package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.Option;

import java.util.List;

/**
 * Сервис для управления вопросами и связанными с ними вариантами ответов.
 */
public interface QuestionService {

    /**
     * Создаёт вопрос вместе со списком вариантов ответов транзакционно.
     * @param question вопрос
     * @param options список вариантов
     */
    void createQuestionWithOptions(Question question, List<Option> options);

    /**
     * Находит вопрос по id
     * @param id идентификатор
     * @return вопрос Question
     */
    Question findById(Long id);

    /**
     * Получает список всех вопросов
     * @return список вопросов
     */
    List<Question> findAll();

    /**
     * Получает список всех вопросов и вариантами ответа на них
     * @return список вопросов с вариантами
     */
    List<Question> findAllWithOptions();
}